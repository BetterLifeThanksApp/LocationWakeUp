package betterlifethanksapp.gmail.com.locationWakeUp.ui.currentRoute


import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.util.AndroidException
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import betterlifethanksapp.gmail.com.locationWakeUp.data.db.LocationDao
import betterlifethanksapp.gmail.com.locationWakeUp.data.db.LocationRoomDatabase
import betterlifethanksapp.gmail.com.locationWakeUp.data.location.*
import betterlifethanksapp.gmail.com.locationWakeUp.data.unit.Unit
import betterlifethanksapp.gmail.com.locationWakeUp.data.unit.UnitSettings
import kotlinx.coroutines.launch
import java.lang.Exception
import java.math.RoundingMode
import java.net.UnknownHostException
import java.text.DecimalFormat

class CurrentRouteViewModel(application: Application)
    : AndroidViewModel(application),
    LocationEventsListener.OnFinishedLocationSingleOperations.OnFinishedSingleLocationVm{

    private val _dialogInterfaceText = MutableLiveData<String>()

    val dialogInterfaceText:LiveData<String>
        get() = _dialogInterfaceText

    private val _toastMessage = MutableLiveData<String>()

    val toastMessage:LiveData<String>
        get() = _toastMessage

    private val _buttonEnabled = MutableLiveData<Boolean>()

    val buttonEnabled:LiveData<Boolean>
        get() = _buttonEnabled

    private val _permissionGranted = MutableLiveData<Boolean>()

    val permissionGranted:LiveData<Boolean>
        get() = _permissionGranted

    private val _isProgressBarVisible = MutableLiveData<Boolean>()

    val isProgressBarVisible:LiveData<Boolean>
            get() = _isProgressBarVisible

    private val _buttonClick = MutableLiveData<Boolean>()

    val buttonClick:LiveData<Boolean>
        get() = _buttonClick

    private val _textView = MutableLiveData<String>()

    val textView:LiveData<String>
        get() = _textView

    private val _wakeUpMode = MutableLiveData<Boolean>()

    val wakeUpMode:LiveData<Boolean>
            get() = _wakeUpMode
    //TODO You should create button to cancel wakeUpMode

    private var unitsName:String="km"



    private val REQUEST_CODE = 984



    private val repository:CurrentRouteRepository

    init {
        _buttonEnabled.value = true
        _permissionGranted.value = true
        val locationDao = LocationRoomDatabase.getDatabase(application).locationDao()
        repository = CurrentRouteRepository(application,this,locationDao)
        //val ll = CurrentSingleLocationListener(this)
        //1.Create location references
        //2.Create Repository references with location ref in constructor
    }


    fun onButtonClicked(text:String?){

        //1.Check location permission and check is turn on/off
        //2.Notify fragment if turn off or if you don't let permisson yet.
        //3.Check internet permission and check is turn on/off
        //4.Notify fragment
        //repo.checkLocation
        //repo.checkInternet
        //repo.checkDistance
        //TODO this works only if i use this fragment(becauce I use thisFragmentViewModelScope
            //TODO I can click in button 2 times(if i click fast) and I see 2x dialogBox :(
            viewModelScope.launch {
                _buttonEnabled.value=false
                _isProgressBarVisible.value = true
                _buttonClick.value = false
                try {
                    //TODO add progress circle(for waiting) and disable in myLocationSuccess() and myLocationFaliure() implemented methods :)
                    repository.getDistenceInfo(text!!)
                }
                catch (e: UnknownHostException){
                    _toastMessage.value =  e.message
                    enableButtonDisableProgressBar()
                }
                catch (e: AndroidException)
                {
                    _permissionGranted.value = false
                    enableButtonDisableProgressBar()
                }
                catch(e:Exception)
                {
                            _toastMessage.value = e.message
                            enableButtonDisableProgressBar()

                }
            }


        //_dialogInterfaceText.value = "Odległość od miejsca to $distance km\nUstawić budzik?"
    }


    fun setAlarmClockWithLocation(locationName:String)
    {
        _wakeUpMode.value=true
        _toastMessage.value = "YEA"

        //TODO maybe first save location somewhere.Maybe return location in successLocation and save into val.If you click yes in dialogbox then I save into database(e.g 'Room') if I click no save variable to null
        viewModelScope.launch {
            observeDistance()
            repository.insertIfNotExist(locationName)
            repository.setAlarmClockWithLocation()

        }
        //TODO Temporary I use viewModelScope.In future maybe I want to ues another scope if I want to use location in background https://developer.android.com/training/location/request-updates#continue-user-initiated-action
    }

    private fun observeDistance() {
        repository.distance.observeForever { distance->
            val df = DecimalFormat("#.#")
            df.roundingMode = RoundingMode.CEILING
            _textView.value = "You're from destination ${df.format(distance)} $unitsName"
        }
    }

    fun makeRequest(activity: Fragment) {
        Log.i("Permission","makeRequest()")


            activity.requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),
                REQUEST_CODE)
            Log.i("Permission","let in makeRequest")
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray)
    {
        when(requestCode){
            REQUEST_CODE->{
                if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED)
                {
                    _toastMessage.value = "Proszę ponownie kliknąć na przycisk\n ...i kliknąć zezwól :)"
                }
                else
                {
                    _buttonClick.value = true
                }
            }
        }
    }



    override fun successSingleLocation(distance:Float){
        val tempInt:Int = distance.toInt()
        _dialogInterfaceText.value = "Distance in straight line is around $tempInt $unitsName\nSet up alarm clock?"
        _isProgressBarVisible.value = false
    }

    override fun failedSingleLocation() {
        _toastMessage.value = "Turn on location please :)\nAnd again click button ;)"
        enableButtonDisableProgressBar()
    }

    fun enableButtonDisableProgressBar(){
        _buttonEnabled.value = true
        _isProgressBarVisible.value = false
    }

    fun onDestroyView(){
        resetViewValues()
    }

    private fun resetViewValues() {
        _toastMessage.value=null
        _dialogInterfaceText.value=null
    }

    fun onUnitChanged(unit:Unit) {
        val unitSettings = UnitSettings(unit)
        unitsName = unitSettings.unitName
        repository.onUnitChanged(unitSettings.multiplierUnit)
    }

}

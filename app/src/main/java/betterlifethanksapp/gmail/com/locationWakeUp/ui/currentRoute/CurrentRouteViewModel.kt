package betterlifethanksapp.gmail.com.locationWakeUp.ui.currentRoute


import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.util.AndroidException
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.preference.PreferenceManager
import betterlifethanksapp.gmail.com.locationWakeUp.R
import betterlifethanksapp.gmail.com.locationWakeUp.data.db.LocationDao
import betterlifethanksapp.gmail.com.locationWakeUp.data.db.LocationRoomDatabase
import betterlifethanksapp.gmail.com.locationWakeUp.data.location.*
import betterlifethanksapp.gmail.com.locationWakeUp.data.unit.PreferencesOperations
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

    private var unitsName:String=""



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

            getUnitsFromPreference()

            viewModelScope.launch {
                _buttonEnabled.value=false
                _isProgressBarVisible.value = true
                _buttonClick.value = false
                try {
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

    }

    private fun getUnitsFromPreference() {

        val name = repository.getUnitPreference()
        val prefOperations = PreferencesOperations()
        name?.let {
            val unitEnumType = prefOperations.getUnitEnumType(it)
            onUnitChanged(unitEnumType)
        }
    }




    fun setAlarmClockWithLocation(locationName:String)
    {
        _wakeUpMode.value=true
        _toastMessage.value = "YEA"

        viewModelScope.launch {
            observeDistance()
            repository.insertIfNotExist(locationName)
            repository.setAlarmClockWithLocation()

        }
    }

    private fun observeDistance() {
        repository.distance.observeForever { distance->
            val df = DecimalFormat("#.#")
            df.roundingMode = RoundingMode.CEILING
            _textView.value = getAppContext().getString(R.string.distance_info,df.format(distance),unitsName)
        }
    }

    fun makeRequest(activity: Fragment) {
            activity.requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),
                REQUEST_CODE)
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
                    _toastMessage.value = getAppContext().getString(R.string.turn_on_location)
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
        _dialogInterfaceText.value = getAppContext().getString(R.string.dialog_text,tempInt,unitsName)
        _isProgressBarVisible.value = false
    }

    override fun failedSingleLocation() {
        _toastMessage.value = getAppContext().getString(R.string.turn_on_location)
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

    private fun getAppContext(): Context {
        val context = getApplication() as Context
        return context.applicationContext
    }

}

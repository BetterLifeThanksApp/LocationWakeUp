package betterlifethanksapp.gmail.com.locationWakeUp.ui.currentRoute


import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.util.AndroidException
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import betterlifethanksapp.gmail.com.locationWakeUp.data.internet.InternetConnect
import betterlifethanksapp.gmail.com.locationWakeUp.data.location.*
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.net.UnknownHostException

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

    private val REQUEST_CODE = 984



    private val repository:CurrentRouteRepository

    init {
        _buttonEnabled.value = true
        _permissionGranted.value = true
        repository = CurrentRouteRepository(application,this)
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
                    when(e){
                        is IllegalArgumentException,is IOException->{
                            _toastMessage.value = "I couldn't find destination address:(\nPlease try enter another location"
                            enableButtonDisableProgressBar()
                        }

                    }

                }
            }


        //_dialogInterfaceText.value = "Odległość od miejsca to $distance km\nUstawić budzik?"
    }


    fun setAlarmClockWithLocation()
    {
        _toastMessage.value = "YEA"
        //TODO get location and send to repository and set the alarm when you are e.g. 2km from this location
        //TODO maybe first save location somewhere.Maybe return location in successLocation and save into val.If you click yes in dialogbox then I save into database(e.g 'Room') if I click no save variable to null
        viewModelScope.launch {repository.setAlarmClockWithLocation()}
        //TODO Temporary I use viewModelScope.In future maybe I want to ues another scope if I want to use location in background https://developer.android.com/training/location/request-updates#continue-user-initiated-action
    }

    fun makeRequest(activity: Fragment) {
        Log.i("Permission","makeRequest()")


            activity.requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),
                REQUEST_CODE)
        //TODO maybe add later ACCESS_COARSE_LOCATION permission
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
                    Log.i("Permission","granted")
                }
            }
        }
    }



    override fun successSingleLocation(distance:Float){
        val tempInt:Int = distance.toInt()
        _dialogInterfaceText.value = "Distance in straight line is around $tempInt km\nSet up alarm clock?"
        enableButtonDisableProgressBar()//TODO don't use this method in this place
    }

    override fun failedSingleLocation() {
        _toastMessage.value = "Turn on location please :)\nAnd again click button ;)"
        enableButtonDisableProgressBar()
    }

    private fun enableButtonDisableProgressBar(){
        _buttonEnabled.value = true
        _isProgressBarVisible.value = false
    }

}

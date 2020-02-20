package betterlifethanksapp.gmail.com.locationWakeUp.ui.currentRoute


import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import android.util.AndroidException
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import betterlifethanksapp.gmail.com.locationWakeUp.data.internet.InternetConnect
import betterlifethanksapp.gmail.com.locationWakeUp.data.location.*
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class CurrentRouteViewModel(application: Application) : AndroidViewModel(application),DistanceSuccess,LocationEventsListener{

    private val _dialogInterfaceText = MutableLiveData<String>()

    val dialogInterfaceText:LiveData<String>
        get() = _dialogInterfaceText


    private val _distanceState = MutableLiveData<Boolean>()

    val distancState:LiveData<Boolean>
        get() = _distanceState

    private val _toastMessage = MutableLiveData<String>()

    val toastMessage:LiveData<String>
        get() = _toastMessage

    private val _buttonEnabled = MutableLiveData<Boolean>()

    val buttonEnabled:LiveData<Boolean>
        get() = _buttonEnabled

    private val _permissionGranted = MutableLiveData<Boolean>()

    val permissionGranted:LiveData<Boolean>
        get() = _permissionGranted

    private val REQUEST_CODE = 984



    private val repository:CurrentRouteRepository

    init {
        _buttonEnabled.value = true
        _permissionGranted.value = true
        val ldo = LocationDataOperations(application)
        val ll = CurrentSingleLocationListener(this)
        val ldh = LocationDataHelper(ldo,ll)
        val internetConnect = InternetConnect()
        repository=CurrentRouteRepository(ldh,internetConnect)
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
            viewModelScope.launch {
                _buttonEnabled.value=false
                try {
                    repository.getDistenceInfo(text!!)
                }
                catch (e: UnknownHostException){
                    _toastMessage.value =  e.message
                }
                catch (e: AndroidException)
                {
                    _permissionGranted.value = false
                }
                finally {
                    _buttonEnabled.value = true
                }
            }


        //_dialogInterfaceText.value = "Odległość od miejsca to $distance km\nUstawić budzik?"
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



    override fun displayToast(text: Float) {
        _distanceState.value=true
    }

    override fun locationFaliure() {
        _distanceState.value=false
    }

    //TODO application works correctly if you manually on Location...
    override fun myLocationSuccess(location: Location) {
        Log.i("LOCATION","$location")
    }

    override fun myLocaionFaliure() {
        Log.i("LOCATION","faliure")
    }


}

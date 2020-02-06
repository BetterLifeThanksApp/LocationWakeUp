package betterlifethanksapp.gmail.com.locationWakeUp.ui.currentRoute


import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import betterlifethanksapp.gmail.com.locationWakeUp.data.location.DistanceSuccess
import betterlifethanksapp.gmail.com.locationWakeUp.data.location.LocationDataHelper
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class CurrentRouteViewModel(application: Application) : AndroidViewModel(application),DistanceSuccess{

    private val _dialogInterfaceText = MutableLiveData<String>()

    val dialogInterfaceText:LiveData<String>
        get() = _dialogInterfaceText


    private val _distanceState = MutableLiveData<Boolean>()

    val distancState:LiveData<Boolean>
        get() = _distanceState

    private val _toastMessage = MutableLiveData<String>()

    val toastMessage:LiveData<String>
        get() = _toastMessage


    private val repository:CurrentRouteRepository

    init {
        //TODO
        val ldh = LocationDataHelper(application)
        repository=CurrentRouteRepository(ldh)
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
                try {
                    repository.getDistenceInfo(text!!)
                }
                catch (e: UnknownHostException){
                    _toastMessage.value =  e.message
                }
            }


        //_dialogInterfaceText.value = "Odległość od miejsca to $distance km\nUstawić budzik?"
    }

    override fun displayToast(text: Float) {
        _distanceState.value=true
    }

    override fun locationFaliure() {
        _distanceState.value=false
    }


}

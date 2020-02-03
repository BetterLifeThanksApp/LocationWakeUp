package betterlifethanksapp.gmail.com.locationWakeUp.ui.currentRoute


import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import betterlifethanksapp.gmail.com.locationWakeUp.data.location.DistanceSuccess
import betterlifethanksapp.gmail.com.locationWakeUp.data.location.LocationDataHelper

class CurrentRouteViewModel(application: Application) : AndroidViewModel(application),DistanceSuccess{

    private val _dialogInterfaceText = MutableLiveData<String>()

    val dialogInterfaceText:LiveData<String>
        get() = _dialogInterfaceText


    private val _distanceState = MutableLiveData<Boolean>()

    val distancState:LiveData<Boolean>
        get() = _distanceState


    private val repository:CurrentRouteRepository

    init {
        //TODO
        val ldh = LocationDataHelper(application,"Zlota 44,Warsaw",distanceSuccess = this)
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
        repository.getDistenceInfo()

        _dialogInterfaceText.value = "Odległość od miejsca to X km\nUstawić budzik?"
    }

    override fun displayToast(text: Float) {
        _distanceState.value=true
    }

    override fun locationFaliure() {
        _distanceState.value=false
    }


}

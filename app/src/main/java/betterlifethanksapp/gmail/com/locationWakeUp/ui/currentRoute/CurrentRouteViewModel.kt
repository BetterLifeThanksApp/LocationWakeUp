package betterlifethanksapp.gmail.com.locationWakeUp.ui.currentRoute


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData

class CurrentRouteViewModel(application: Application) : AndroidViewModel(application){

    private val _dialogInterfaceText = MutableLiveData<String>()

    val dialogInterfaceText:LiveData<String>
        get() = _dialogInterfaceText


    init {
        //TODO
        //1.Create location references
        //2.Create Repository references with location ref in constructor
    }


    fun onButtonClicked(text:String?){

        //1.Check location permission and check is turn on/off
        //2.Notify fragment if turn off or if you don't let permisson yet.
        //3.Check internet permission and check is turn on/off
        //4.Notify fragment

        _dialogInterfaceText.value = "Odległość od miejsca to X km\nUstawić budzik?"
    }


}

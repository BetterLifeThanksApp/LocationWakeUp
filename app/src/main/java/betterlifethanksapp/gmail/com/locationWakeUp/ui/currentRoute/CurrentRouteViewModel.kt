package betterlifethanksapp.gmail.com.locationWakeUp.ui.currentRoute


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData

class CurrentRouteViewModel() : ViewModel(){

    private val _dialogInterfaceText = MutableLiveData<String>()

    val dialogInterfaceText:LiveData<String>
        get() = _dialogInterfaceText

    fun onButtonClicked(){
        _dialogInterfaceText.value = "Odległość od miejsca to X km\nUstawić budzik?"
    }


}

package betterlifethanksapp.gmail.com.locationWakeUp.ui.currentRoute

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object CurrentRouteRepo {


    private val _currentRouteInfo = MutableLiveData<String>()

    val currentRouteInfo:LiveData<String>
    get() = _currentRouteInfo

    init{
        _currentRouteInfo.value = "Brak info"
    }


    fun getRouteInfo(){
        _currentRouteInfo.value="Inne info"
    }

}
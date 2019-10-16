package betterlifethanksapp.gmail.com.locationWakeUp.ui.currentRoute

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class CurrentRouteViewModel() : ViewModel() {


    val currentRouteInfo:LiveData<String>
    get() = CurrentRouteRepo.currentRouteInfo

    fun onSetARouteClick() = CurrentRouteRepo.getRouteInfo()


}

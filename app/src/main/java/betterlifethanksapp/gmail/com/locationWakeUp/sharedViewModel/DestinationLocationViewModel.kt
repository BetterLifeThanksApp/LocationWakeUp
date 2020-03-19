package betterlifethanksapp.gmail.com.locationWakeUp.sharedViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DestinationLocationViewModel: ViewModel() {
    val destinationLocation = MutableLiveData<String>()

    fun setDestination(destination:String){
        destinationLocation.value = destination
    }
}
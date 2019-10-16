package betterlifethanksapp.gmail.com.locationWakeUp.ui.currentRoute

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CurrentRouteViewModel() : ViewModel(), Observable {

    private val callbacks:PropertyChangeRegistry by lazy {PropertyChangeRegistry()}

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }


    val currentRouteInfo:LiveData<String>
    get() = CurrentRouteRepo.currentRouteInfo


   @Bindable
   val editTextLocation = MutableLiveData<String>()



    fun onSetARouteClick() = CurrentRouteRepo.getRouteInfo(editTextLocation.value)


}

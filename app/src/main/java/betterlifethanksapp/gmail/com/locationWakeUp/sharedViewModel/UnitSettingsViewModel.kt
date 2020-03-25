package betterlifethanksapp.gmail.com.locationWakeUp.sharedViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import betterlifethanksapp.gmail.com.locationWakeUp.data.unit.Unit

class UnitSettingsViewModel:ViewModel() {

    val currentUnit = MutableLiveData<Unit>()

    fun setcurrentUnit(currentUnit:Unit){
        this.currentUnit.value = currentUnit
    }

}
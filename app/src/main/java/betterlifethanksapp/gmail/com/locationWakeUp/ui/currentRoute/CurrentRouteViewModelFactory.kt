package betterlifethanksapp.gmail.com.locationWakeUp.ui.currentRoute

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CurrentRouteViewModelFactory(val activity:Activity): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Activity::class.java)
            .newInstance(activity)
    }
}
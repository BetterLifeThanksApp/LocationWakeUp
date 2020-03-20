package betterlifethanksapp.gmail.com.locationWakeUp.ui.routes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import betterlifethanksapp.gmail.com.locationWakeUp.data.db.LocationRoomDatabase

class RoutesViewModel(application: Application)
    : AndroidViewModel(application){

    private val repository:RoutesRepository


    init{
        val locationDao = LocationRoomDatabase
            .getDatabase(application)
            .locationDao()
        repository = RoutesRepository(locationDao)
    }



}

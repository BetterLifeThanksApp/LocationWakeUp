package betterlifethanksapp.gmail.com.locationWakeUp.ui.routes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import betterlifethanksapp.gmail.com.locationWakeUp.data.db.Location
import betterlifethanksapp.gmail.com.locationWakeUp.data.db.LocationRoomDatabase

class RoutesViewModel(application: Application)
    : AndroidViewModel(application){

    private val repository:RoutesRepository

    val allLocation: LiveData<List<Location>>


    init{
        val locationDao = LocationRoomDatabase
            .getDatabase(application)
            .locationDao()
        repository = RoutesRepository(locationDao)
        allLocation = repository.allLocation
    }



}

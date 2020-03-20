package betterlifethanksapp.gmail.com.locationWakeUp.ui.routes

import androidx.lifecycle.LiveData
import betterlifethanksapp.gmail.com.locationWakeUp.data.db.Location
import betterlifethanksapp.gmail.com.locationWakeUp.data.db.LocationDao

class RoutesRepository(private val locationDao: LocationDao) {


    val allLocation: LiveData<List<Location>> = locationDao.getAllLocations()


}
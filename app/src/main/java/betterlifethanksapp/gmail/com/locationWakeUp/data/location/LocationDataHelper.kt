package betterlifethanksapp.gmail.com.locationWakeUp.data.location

import android.location.Location
import betterlifethanksapp.gmail.com.locationWakeUp.data.location.LocationPermission.Companion.PERMISSION_STRING
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocationDataHelper(private val locationDataOperations:LocationDataOperations,
                         private val locationResult:LocationEventsListener.OnFinishedLocationSingleOperations):LocationEventsListener

                        //later user context.applicationContext()
{

    lateinit var destination:Location

    companion object{
        private const val PERMISSION_STRING1:String = android.Manifest.permission.ACCESS_COARSE_LOCATION
        private const val PERMISSION_STRING2:String = android.Manifest.permission.ACCESS_FINE_LOCATION
    }

    suspend fun checkPermissionCorrect(){
        locationDataOperations.checkPermissionCorrect(PERMISSION_STRING1, PERMISSION_STRING2)
    }

    suspend fun launchLocationRequests() = withContext(Dispatchers.IO){
        locationDataOperations.createLocationRequest(this@LocationDataHelper)
        locationDataOperations.createSettingsBuilder()
        locationDataOperations.startLocationUpdated()
    }

    fun checkLocationOn():Boolean{
        val isLocationOn = locationDataOperations.isLocationOn()
        if(!isLocationOn)
        {
            locationResult.failedSingleLocation()
        }
        return isLocationOn
    }


    suspend fun estimateDistance(text: String){
        destination = locationDataOperations.getDestinationLocation(text)
        val locationListener = CurrentSingleLocationListener(this)
        locationDataOperations.createSingleLocationRequest(this@LocationDataHelper)
        locationDataOperations.createSettingsBuilder()
        locationDataOperations.oneLocationUpdate()
    }


    fun getDistanceInfo(text:String){

    }

    override fun mySingleLocationSuccess(location: Location) {
        val distance = locationDataOperations.getDistance(location,destination)
        locationResult.successSingleLocation(distance)
    }

    override fun mySingleLocationFailure() {
        locationResult.failedSingleLocation()
    }

    override fun multipleLocationSuccess(currentLocation: Location) {
        val distance = locationDataOperations.getDistance(currentLocation,destination)
        locationResult.successMultipleLocation(distance)
    }

    override fun multipleLocationFailure() {
        //TODO //HANDLE THIS LATER...
    }


}
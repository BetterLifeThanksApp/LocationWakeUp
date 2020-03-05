package betterlifethanksapp.gmail.com.locationWakeUp.data.location

import android.location.Location
import betterlifethanksapp.gmail.com.locationWakeUp.data.location.LocationPermission.Companion.PERMISSION_STRING
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocationDataHelper(private val locationDataOperations:LocationDataOperations,
                         private val locationResult:LocationEventsListener.OnFinishedLocationSingleOperations):LocationEventsListener

                        //later user context.applicationContext()
{
    /*
    override fun myLocaionFaliure() {
        distanceSuccess.locationFaliure()
    }
    */
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


    suspend fun estimateDistance(text: String){
        destination = locationDataOperations.getDestinationLocation(text)
        val locationListener = CurrentSingleLocationListener(this)
        //locationDataOperations.getMySingleLocation(locationListener)
        locationDataOperations.createSingleLocationRequest(this@LocationDataHelper)
        locationDataOperations.createSettingsBuilder()
        locationDataOperations.oneLocationUpdate()
    }


    fun getDistanceInfo(text:String){




        //1.Check location permission and check is turn on/off
        //2.Notify fragment if turn off or if you don't let permisson yet.
        //3.Check internet permission and check is turn on/off
        //4.Notify fragment
        //repo.checkLocation
        //repo.checkInternet
        //repo.checkDistance



        //val locationListener: LocationListener = CurrentSingleLocationListener(activity,this)

      //  locationDataOperations.getMyLocation(locationListener) //get your current location


        //val distance = locationDataOperations.getDistance(myLocation,destination)
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
/*
    //TODO create interface and run this method if location is correct
    override fun myLocationSuccess(myLocation:Location)
    {
        val destination = locationDataOperations.getDestinationLocation(address)
        val distance = locationDataOperations.getDistance(myLocation,destination)
        distanceSuccess.displayToast(distance)

    }
    */





}
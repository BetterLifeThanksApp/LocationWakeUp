package betterlifethanksapp.gmail.com.locationWakeUp.data.location

import android.location.Location

class LocationDataHelper(private val locationDataOperations:LocationDataOperations,
                         private val locationResult:LocationEventsListener.OnFinishedAllOperations):LocationEventsListener

                        //later user context.applicationContext()
{
    /*
    override fun myLocaionFaliure() {
        distanceSuccess.locationFaliure()
    }
    */
    lateinit var destination:Location

    companion object{
        private const val PERMISSION_STRING:String = android.Manifest.permission.ACCESS_FINE_LOCATION
    }

    suspend fun checkPermissionCorrect(){
        locationDataOperations.checkPermissionCorrect(PERMISSION_STRING)
    }



    suspend fun estimateDistance(text: String){
        destination = locationDataOperations.getDestinationLocation(text)
        val locationListener = CurrentSingleLocationListener(this)
        locationDataOperations.getMySingleLocation(locationListener)
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

    override fun myLocationSuccess(location: Location) {
        val distance = locationDataOperations.getDistance(location,destination)
        locationResult.successLocation(distance)
    }

    override fun myLocaionFaliure() {
        locationResult.faliedLocation()
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
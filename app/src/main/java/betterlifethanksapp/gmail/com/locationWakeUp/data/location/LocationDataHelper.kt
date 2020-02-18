package betterlifethanksapp.gmail.com.locationWakeUp.data.location

import android.util.Log

class LocationDataHelper(private val locationDataOperations:LocationDataOperations,
                         private val locationListener: CurrentSingleLocationListener)

                        //later user context.applicationContext()
{
    /*
    override fun myLocaionFaliure() {
        distanceSuccess.locationFaliure()
    }
    */

    companion object{
        private const val PERMISSION_STRING:String = android.Manifest.permission.ACCESS_FINE_LOCATION
    }

    suspend fun checkPermissionCorrect(){
        locationDataOperations.checkPermissionCorrect(PERMISSION_STRING)
    }



    suspend fun estimateDistance(text: String){
        val destination = locationDataOperations.getDestinationLocation(text)
        val myLocation = locationDataOperations.getMyLocation(locationListener)
        //locationDataOperations.getDistance(myLocation,destination)

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
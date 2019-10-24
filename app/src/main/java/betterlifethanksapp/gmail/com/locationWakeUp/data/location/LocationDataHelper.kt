package betterlifethanksapp.gmail.com.locationWakeUp.data.location

import android.app.Activity
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import kotlinx.android.synthetic.main.activity_main.*

class LocationDataHelper(val activity: Activity,
                         val address:String,
                         val locationDataOperations:LocationDataOperations = LocationDataOperations(activity),
                         val distanceSuccess: DistanceSuccess) :LocationEventsListener
{
    override fun myLocaionFaliure() {
        distanceSuccess.locationFaliure()
    }


    fun getDistanceInfo(){

        val locationListener: LocationListener = CurrentSingleLocationListener(activity,this)

        locationDataOperations.getMyLocation(locationListener) //get your current location


        //val distance = locationDataOperations.getDistance(myLocation,destination)

    }

    //TODO create interface and run this method if location is correct
    override fun myLocationSuccess(myLocation:Location)
    {
        val destination = locationDataOperations.getDestinationLocation(address)
        val distance = locationDataOperations.getDistance(myLocation,destination)
        distanceSuccess.displayToast(distance)

    }




}
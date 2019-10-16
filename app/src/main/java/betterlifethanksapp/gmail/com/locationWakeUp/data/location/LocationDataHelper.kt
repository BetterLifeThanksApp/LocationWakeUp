package betterlifethanksapp.gmail.com.locationWakeUp.data.location

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener

class LocationDataHelper(val context: Context, val address:String) {





    fun getDistanceInfo(){
        val locationDataOperations = LocationDataOperations(context)
        val locationListener: LocationListener = CurrentSingleLocationListener()
        val myLocation = locationDataOperations.getMyLocation(locationListener) //get your current location
        val destination = locationDataOperations.getDestinationLocation(address)

        myLocation.distanceTo(destination)

    }




}
package betterlifethanksapp.gmail.com.locationWakeUp.data.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.*

class LocationDataOperations(val context:Context) {




    fun getDestinationLocation(address:String):Location {
        val geocoder = Geocoder(context)
        val address: Address =
            geocoder.getFromLocationName(address, 1)[0]//get first element list of 'Address' class //TODO try to do something if throw exception
        val destination = Location("destination")
        destination.latitude = address.latitude
        destination.longitude = address.longitude
        return destination
    }


    @SuppressLint("MissingPermission")//TODO add permission later
    fun getMyLocation(locationListener:LocationListener):Location?{
        val locationManager = getLocationManager(locationListener)
        //TODO 'Location' should't be return here.It should notify other method uses listener in CurrentSingleLocationListener class
        //val provider = locationManager.getBestProvider(Criteria(),true)
        //locationManager.requestSingleUpdate(provider,CurrentSingleLocationListener(),null)
        locationManager.requestSingleUpdate(
            LocationManager.GPS_PROVIDER,
            locationListener,
            null
        )
        return null
    }


    @SuppressLint("MissingPermission")//TODO add permission later
    private fun getLocationManager(locationListener: LocationListener):LocationManager
    {

        return context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    fun getDistance(myLocation: Location, destination: Location): Float = myLocation.distanceTo(destination)//TODO change because I don't know what kind of units do you use


}
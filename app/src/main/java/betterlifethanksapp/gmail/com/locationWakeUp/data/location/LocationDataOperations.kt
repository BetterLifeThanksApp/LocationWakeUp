package betterlifethanksapp.gmail.com.locationWakeUp.data.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.*
import betterlifethanksapp.gmail.com.locationWakeUp.ui.CurrentRoute.CurrentRouteFragment

class LocationDataOperations(val context:Context) {


    private val REQUEST_CODE:Int = 800

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
        val locationPermission = LocationPermission(context.applicationContext)
        if(locationPermission.isPermissionGranted()) {
            locationManager.requestSingleUpdate(
                LocationManager.GPS_PROVIDER,
                locationListener,
                null
            )
       }
        else{
            locationPermission.requestPermission(REQUEST_CODE)
        }
        return null
    }

    fun onRequestPermissionResult(requestCode: Int,
                                  permissions: Array<out String>,
                                  grantResults: IntArray){
        val locationPermission = LocationPermission(context.applicationContext)
        when(requestCode){
            REQUEST_CODE->{
                if(locationPermission.isPermissionGranted(grantResults)){
                    //TODO run again all proccess(get location from edittextget and find longitude,latitude  and find my location etc..
                    val ldh = LocationDataHelper(context,"Zlota 44,Warsaw") //TODO It's only for testing,don't use this code
                    ldh.getDistanceInfo()
                }
                else
                {
                    //e.g. display some notification which warning you to turn on 'location' or go to settings
                }
            }
        }

    }




    //@SuppressLint("MissingPermission")//TODO add permission later
    private fun getLocationManager(locationListener: LocationListener):LocationManager
    {

        return context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    fun getDistance(myLocation: Location, destination: Location): Float = myLocation.distanceTo(destination)//TODO change because I don't know what kind of units do you use


}
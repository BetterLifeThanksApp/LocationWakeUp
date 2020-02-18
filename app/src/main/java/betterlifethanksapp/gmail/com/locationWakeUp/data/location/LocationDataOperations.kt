package betterlifethanksapp.gmail.com.locationWakeUp.data.location

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.util.AndroidException
import android.util.Log
import androidx.core.content.ContextCompat
import betterlifethanksapp.gmail.com.locationWakeUp.ui.currentRoute.CurrentRouteFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocationDataOperations(val context: Context)
//ALWAYS USE context.applicationContext because 'context' don't work.
{


    private val REQUEST_CODE:Int = 800

    suspend fun checkPermissionCorrect(permission:String) = withContext(Dispatchers.IO){

        //ALWAYS USE context.applicationContext because 'context' don't work.
        if(ContextCompat.checkSelfPermission(context.applicationContext,
                permission
            ) != PackageManager.PERMISSION_GRANTED)
        {
            throw AndroidException("No permission granted!")
        }

    }


    suspend fun getDestinationLocation(address:String):Location =withContext(Dispatchers.IO) {
        val geocoder = Geocoder(context.applicationContext)
        val address: Address =
            geocoder.getFromLocationName(address, 1)[0]//get first element list of 'Address' class //TODO try to do something if throw exception
        val destination = Location("destination")
        destination.latitude = address.latitude
        destination.longitude = address.longitude
        return@withContext destination
    }



    /*
    @SuppressLint("MissingPermission")//TODO add permission later
    fun getMyLocation(locationListener:LocationListener){
        val locationManager = getLocationManager(locationListener)
        //TODO 'Location' should't be return here.It should notify other method uses listener in CurrentSingleLocationListener class
        //val provider = locationManager.getBestProvider(Criteria(),true)
        //locationManager.requestSingleUpdate(provider,CurrentSingleLocationListener(),null)
        val locationPermission = LocationPermission(activity)
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
    }
     */
/*
    @SuppressLint("MissingPermission")
    fun getMyLocation(locationListener: LocationListener)
    {
        val locationManager = getLocationManager(locationListener)
        locationManager.requestSingleUpdate(
            LocationManager.GPS_PROVIDER,
            locationListener,
            null
        )

    }
    */
/*
    fun onRequestPermissionResult(requestCode: Int,
                                  permissions: Array<out String>,
                                  grantResults: IntArray){
        val locationPermission = LocationPermission(activity)
        when(requestCode){
            REQUEST_CODE->{
                if(locationPermission.isPermissionGranted(grantResults)){
                    //TODO run again all proccess(get location from edittextget and find longitude,latitude  and find my location etc..
                    //val ldh = LocationDataHelper(context,"Zlota 44,Warsaw") //TODO It's only for testing,don't use this code
                    //ldh.getDistanceInfo()
                    val siema="przyznane więc jest git"
                    Log.i("Permission","granted")
                    val cu = CurrentRouteFragment()
                    cu.getDistenceInfo()
                }
                else
                {
                    //e.g. display some notification which warning you to turn on 'location' or go to settings
                    val siema="nieprzyznane wiec zrob cos"
                    Log.i("Permission","denied")
                }
            }
        }

    }
*/


/*
    //@SuppressLint("MissingPermission")//TODO add permission later
    private fun getLocationManager(locationListener: LocationListener):LocationManager
    {

        return activity.applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

 */

    fun getDistance(myLocation: Location, destination: Location): Float = myLocation.distanceTo(destination) * 0.001f//TODO change because I don't know what kind of units do you use


}
package betterlifethanksapp.gmail.com.locationWakeUp.data.location

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.location.LocationListener
import android.os.Looper
import android.util.AndroidException
import android.util.Log
import androidx.core.content.ContextCompat
import betterlifethanksapp.gmail.com.locationWakeUp.ui.currentRoute.CurrentRouteFragment
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocationDataOperations(val context: Context)
//ALWAYS USE context.applicationContext because 'context' don't work.
{


    private val REQUEST_CODE:Int = 800
    private var locationRequest:LocationRequest? = null
    private lateinit var locationCallback: LocationCallback
    private lateinit var multipleEventsListener: LocationEventsListener
    //single
    private var locationSingleRequest:LocationRequest? = null //TODO maybe create all this variable(singla and multiple into LocationDataHelper class or another class think about it
    private lateinit var locationSingleCallback: LocationCallback
    private lateinit var singleEventsListener: LocationEventsListener




    suspend fun checkPermissionCorrect(permission1:String,permission2:String) = withContext(Dispatchers.IO){

        //ALWAYS USE context.applicationContext because 'context' don't work.
        if(
            (ContextCompat.checkSelfPermission
                (context.applicationContext,
                permission1) != PackageManager.PERMISSION_GRANTED)
            &&
                (ContextCompat.checkSelfPermission
                (context.applicationContext,
                    permission2) != PackageManager.PERMISSION_GRANTED)
        )
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

    fun createLocationRequest(eventsListener: LocationEventsListener){
        multipleEventsListener = eventsListener
        //create LocationRequest
        locationRequest = LocationRequest.create()?.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        //https://developer.android.com/training/location/change-location-settings#kotlin
    }

    fun createSettingsBuilder(){
        val builder = LocationSettingsRequest.Builder()

        val client:SettingsClient = LocationServices.getSettingsClient(context.applicationContext)
        val task: Task<LocationSettingsResponse> = client.
            checkLocationSettings(builder.build())
        task.addOnSuccessListener { locationSettingsResponse ->
            //Get locationrequest here.
            Log.i("LocationGoogle","Settings success")
        }
        task.addOnFailureListener { exception ->
            Log.i("LocationGoogle","Settings failed")
            /*
            try {
            // Show the dialog by calling startResolutionForResult(),
            // and check the result in onActivityResult().
            exception.startResolutionForResult(this@MainActivity,
                    REQUEST_CHECK_SETTINGS)
        } catch (sendEx: IntentSender.SendIntentException) {
            // Ignore the error.
        }
             */
        }
    }

    fun startLocationUpdated(){

        locationCallback = object :LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for(location in locationResult.locations){
                    Log.i("LocationGoogle","${location.longitude} + ${location.latitude}")
                    multipleEventsListener.multipleLocationSuccess(location)
                }
            }
        }

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context.applicationContext)
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()//TODO read more about this
        )



    }

    fun createSingleLocationRequest(eventsListener: LocationEventsListener){
        singleEventsListener = eventsListener
        //create LocationRequest
        locationSingleRequest = LocationRequest.create()?.apply {
            numUpdates=1
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    fun oneLocationUpdate() {
        locationSingleCallback = object :LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for(location in locationResult.locations){
                    Log.i("LocationGoogle","${location.longitude} + ${location.latitude}")
                    singleEventsListener.mySingleLocationSuccess(location)
                }
            }
        }

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context.applicationContext)
        fusedLocationClient.requestLocationUpdates(
            locationSingleRequest,
            locationSingleCallback,
            Looper.getMainLooper()
        )

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

    @SuppressLint("MissingPermission")
    fun getMySingleLocation(locationListener: LocationListener)
    {
        val locationManager = getLocationManager(locationListener)

        Log.i("LocationSlow","I get locationManager now i requestsingleUpdate")
        locationManager.requestSingleUpdate(
            LocationManager.GPS_PROVIDER,
            locationListener,
            Looper.getMainLooper()
        )
    }

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



    //@SuppressLint("MissingPermission")//TODO add permission later
    private fun getLocationManager(locationListener: LocationListener):LocationManager
    {

        return context.applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }



    fun getDistance(myLocation: Location, destination: Location): Float = myLocation.distanceTo(destination) * 0.001f//TODO change because I don't know what kind of units do you use



}
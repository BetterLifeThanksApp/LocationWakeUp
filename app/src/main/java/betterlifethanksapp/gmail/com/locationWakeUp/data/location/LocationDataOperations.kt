package betterlifethanksapp.gmail.com.locationWakeUp.data.location

import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.util.AndroidException
import androidx.core.content.ContextCompat
import betterlifethanksapp.gmail.com.locationWakeUp.R
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.IOException

class LocationDataOperations(val context: Context)
//ALWAYS USE context.applicationContext because 'context' don't work.
{


    var multiplierUnit: Float = 0.001f
    private val REQUEST_CODE:Int = 800
    private var locationRequest:LocationRequest? = null
    private lateinit var locationCallback: LocationCallback
    private lateinit var multipleEventsListener: LocationEventsListener
    //single
    private var locationSingleRequest:LocationRequest? = null //TODO maybe create all this variable(singla and multiple into LocationDataHelper class or another class think about it
    private lateinit var locationSingleCallback: LocationCallback
    private lateinit var singleEventsListener: LocationEventsListener




    suspend fun checkPermissionCorrect(permission1:String,permission2:String) = withContext(Dispatchers.IO){

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
            throw AndroidException(context.applicationContext.getString(R.string.no_permission_granted))
        }

    }


    suspend fun getDestinationLocation(address:String):Location =withContext(Dispatchers.IO) {
        val geocoder = Geocoder(context.applicationContext)
        val destination = Location("destination")
        try {
            var addressResult=
                geocoder.getFromLocationName(
                    address,
                    1
                )
            if(addressResult.isEmpty()) {
                delay(2000)
                addressResult=
                    geocoder.getFromLocationName(
                        address,
                        1
                    )
                destination.latitude = addressResult[0].latitude
                destination.longitude = addressResult[0].longitude
            }
            else {
                destination.latitude = addressResult[0].latitude
                destination.longitude = addressResult[0].longitude
            }
            }catch (e:Exception)
        {
            when(e){
                is IllegalArgumentException,is IOException ->{
                    throw IOException(context.getString(R.string.no_find_destination_address))
                }

            }
        }
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
        //TODO
        task.addOnSuccessListener { locationSettingsResponse ->
            //Get locationrequest here.
        }
        task.addOnFailureListener { exception ->
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


    fun getDistance(myLocation: Location, destination: Location): Float = myLocation.distanceTo(destination) * multiplierUnit

    fun isLocationOn(): Boolean {
        val lm = context.applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }


}
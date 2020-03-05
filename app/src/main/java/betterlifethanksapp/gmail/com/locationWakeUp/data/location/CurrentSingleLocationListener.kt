package betterlifethanksapp.gmail.com.locationWakeUp.data.location

import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.util.Log


class CurrentSingleLocationListener(val locationEventsListener: LocationEventsListener):LocationListener {
    override fun onLocationChanged(location: Location?) {
        //we have our location.It's time to notify some listener.
        //And this listener should run
        //val locationListener:LocationEventsListener = CurrentRouteViewModel albo CurrentRouteRepository
        //locationListener.getCurrentLocation(location)
        val test1 = location!!.latitude
        Log.i("LocationSlow","$test1")
        // LocationDataHelper.myLocationSuccess by interface
        locationEventsListener.mySingleLocationSuccess(location)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }

    override fun onProviderEnabled(provider: String?) {
    }

    override fun onProviderDisabled(provider: String?) {
        //val intent = Intent(ACTION_LOCATION_SOURCE_SETTINGS)
        //activity.startActivity(intent)
        locationEventsListener.mySingleLocationFailure()
    }



}
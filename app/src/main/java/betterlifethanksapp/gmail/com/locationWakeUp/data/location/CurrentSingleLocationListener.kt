package betterlifethanksapp.gmail.com.locationWakeUp.data.location

import android.location.Location
import android.location.LocationListener
import android.os.Bundle

class CurrentSingleLocationListener:LocationListener {
    override fun onLocationChanged(location: Location?) {
        //we have our location.It's time to notify some listener.
        //And this listener should run
        //val locationListener:LocationEventsListener = CurrentRouteViewModel albo CurrentRouteRepository
        //locationListener.getCurrentLocation(location)
        val test1 = location!!.latitude
        val test2 = location.longitude
        val test3 = "hello"
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }

    override fun onProviderEnabled(provider: String?) {

    }

    override fun onProviderDisabled(provider: String?) {

    }
}
package betterlifethanksapp.gmail.com.locationWakeUp.data.location

import android.location.Location
import android.location.LocationListener
import android.os.Bundle

class CurrentSingleLocationListener:LocationListener {
    override fun onLocationChanged(location: Location?) {
        //we have our location.It's time to notify some listener.
        //And this listener should run
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderEnabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderDisabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
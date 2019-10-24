package betterlifethanksapp.gmail.com.locationWakeUp.data.location

import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import android.content.Intent
import android.provider.Settings
import android.R.string.cancel
import android.app.Activity
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import betterlifethanksapp.gmail.com.locationWakeUp.ui.MainActivity


class CurrentSingleLocationListener(val activity: Activity,val locationEventsListener: LocationEventsListener):LocationListener {
    override fun onLocationChanged(location: Location?) {
        //we have our location.It's time to notify some listener.
        //And this listener should run
        //val locationListener:LocationEventsListener = CurrentRouteViewModel albo CurrentRouteRepository
        //locationListener.getCurrentLocation(location)
        val test1 = location!!.latitude
        Log.i("Location","$test1")
        //TODO LocationDataHelper.myLocationSuccess by interface
        locationEventsListener.myLocationSuccess(location)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }

    override fun onProviderEnabled(provider: String?) {
    }

    override fun onProviderDisabled(provider: String?) {
        //val intent = Intent(ACTION_LOCATION_SOURCE_SETTINGS)
        //activity.startActivity(intent)
        locationEventsListener.myLocaionFaliure()
    }



}
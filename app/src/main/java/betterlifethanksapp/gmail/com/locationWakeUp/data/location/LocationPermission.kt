package betterlifethanksapp.gmail.com.locationWakeUp.data.location

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class LocationPermission(val activity: Activity) {
    companion object{
       val PERMISSION_STRING:String = android.Manifest.permission.ACCESS_FINE_LOCATION
    }


    fun isPermissionGranted():Boolean = ContextCompat.checkSelfPermission(activity.applicationContext, PERMISSION_STRING) == PackageManager.PERMISSION_GRANTED
    fun requestPermission(requestCode:Int) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(PERMISSION_STRING),requestCode)
    }

    fun isPermissionGranted(grantResults: IntArray):Boolean = grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED





}
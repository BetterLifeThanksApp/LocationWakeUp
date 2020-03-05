package betterlifethanksapp.gmail.com.locationWakeUp.ui.currentRoute

import android.content.Context
import android.content.Intent
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import betterlifethanksapp.gmail.com.locationWakeUp.data.internet.InternetConnect
import betterlifethanksapp.gmail.com.locationWakeUp.data.internet.InternetConnectI
import betterlifethanksapp.gmail.com.locationWakeUp.data.location.LocationDataHelper
import betterlifethanksapp.gmail.com.locationWakeUp.data.location.LocationDataOperations
import betterlifethanksapp.gmail.com.locationWakeUp.data.location.LocationEventsListener
import betterlifethanksapp.gmail.com.locationWakeUp.data.services.NotificationWakeUpService
import kotlinx.coroutines.coroutineScope
import java.net.UnknownHostException

class CurrentRouteRepository(val context: Context,
                             private val callbackListener:LocationEventsListener.OnFinishedLocationSingleOperations.OnFinishedSingleLocationVm)
    :LocationEventsListener.OnFinishedLocationSingleOperations {

    private val ldo:LocationDataOperations = LocationDataOperations(context)
    private val ldh:LocationDataHelper
    private val internetConnect:InternetConnect
    private var isWakeUp = false

    init {
        //TODO send context.applicationContext and change variable into LoationDataOperation(don't use context.applicationContext in DataOperation class because i send context.applicationContext so use just context)
        ldh = LocationDataHelper(ldo,this)
        internetConnect = InternetConnect()
    }



    suspend fun getDistenceInfo(text:String)
    {
        internetConnect.isInternetAvailable() //TODO maybe inform when you have internet access
        ldh.checkPermissionCorrect() //TODO maybe not exception because if you grant permission(if you clicked yes on dialogbox) next method don't run ldh.estimateDistance(text) not run
        ldh.estimateDistance(text)
        //TODO waiting for location is long (sometimes 8 sec) maybe use google services location or maybe use location.bestProviderLocation or use location from internet
    }

    suspend fun setAlarmClockWithLocation() {
        ldh.launchLocationRequests()
        Log.i("LOCATION DESTINATION","${ldh.destination.latitude} and ${ldh.destination.longitude}")
    }

    override  fun successSingleLocation(distance: Float) {
        callbackListener.successSingleLocation(distance)
    }

    override fun failedSingleLocation() {
        callbackListener.failedSingleLocation()
    }

    override fun successMultipleLocation(distance: Float) {
        Log.i("distance","$distance")
        if(!isWakeUp && distance <=255.0f)//TODO 3.0 is temporary. In future I'll check value saved into memory and check if distance to destination is <3km I wake up the user.
        {
            isWakeUp=true
            wakeUpNow()
        }
    }

    private fun wakeUpNow() {
        val intent = Intent(context.applicationContext,NotificationWakeUpService::class.java)
        intent.putExtra(NotificationWakeUpService.MESSAGE(),"WSTAWEJ NIE UDAWEJ")
        context.applicationContext.startService(intent)
        Log.i("Wake up","WAKE UP")

    }

    override fun failedMultipleLocation() {
        //handle this later //TODO
    }
}
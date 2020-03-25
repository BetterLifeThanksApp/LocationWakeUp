package betterlifethanksapp.gmail.com.locationWakeUp.ui.currentRoute

import android.content.Context
import android.content.Intent
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import betterlifethanksapp.gmail.com.locationWakeUp.data.db.LocationDao
import betterlifethanksapp.gmail.com.locationWakeUp.data.internet.InternetConnect
import betterlifethanksapp.gmail.com.locationWakeUp.data.internet.InternetConnectI
import betterlifethanksapp.gmail.com.locationWakeUp.data.location.LocationDataHelper
import betterlifethanksapp.gmail.com.locationWakeUp.data.location.LocationDataOperations
import betterlifethanksapp.gmail.com.locationWakeUp.data.location.LocationEventsListener
import betterlifethanksapp.gmail.com.locationWakeUp.data.services.NotificationWakeUpService
import betterlifethanksapp.gmail.com.locationWakeUp.data.unit.PreferencesOperations
import betterlifethanksapp.gmail.com.locationWakeUp.data.unit.UnitSettings
import kotlinx.coroutines.coroutineScope
import java.net.UnknownHostException

class CurrentRouteRepository(val context: Context,
                             private val callbackListener:LocationEventsListener.OnFinishedLocationSingleOperations.OnFinishedSingleLocationVm,
                             private val locationDao:LocationDao)
    :LocationEventsListener.OnFinishedLocationSingleOperations {

    private val ldo:LocationDataOperations = LocationDataOperations(context)
    private val ldh:LocationDataHelper
    private val internetConnect:InternetConnect
    private var isWakeUp = false
    private var _distance = MutableLiveData<Float>()
    val distance:LiveData<Float>
        get() = _distance

    init {
        //TODO send context.applicationContext and change variable into LoationDataOperation(don't use context.applicationContext in DataOperation class because i send context.applicationContext so use just context)
        ldh = LocationDataHelper(ldo,this)
        internetConnect = InternetConnect()
    }



    suspend fun getDistenceInfo(text:String)
    {
        internetConnect.isInternetAvailable() //TODO maybe inform when you have internet access
        ldh.checkPermissionCorrect() //TODO maybe not exception because if you grant permission(if you clicked yes on dialogbox) next method don't run ldh.estimateDistance(text) not run
        if(ldh.checkLocationOn()) {
            ldh.estimateDistance(text)
        }
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
        _distance.value = distance
        if(!isWakeUp && distance <=3.0f)//TODO 3.0 is temporary. In future I'll check value saved into memory and check if distance to destination is <3km I wake up the user.
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

    suspend fun insertIfNotExist(locationName: String) {
        val location = betterlifethanksapp.gmail.com.locationWakeUp.data.db.Location(locationName)
        locationDao.insert(location)
        //check if database(db) exist
        //if not exist,create
        //check if locationName is in database
        //if not exist insert into db
        //dao.insert(locationName)
    }

    fun onUnitChanged(multiplierUnit: Float) {
        ldo.multiplierUnit= multiplierUnit
    }

    fun getUnitPreference():String?{
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)
        return sharedPreferences.getString("UNIT_SYSTEM","KM")
    }
}
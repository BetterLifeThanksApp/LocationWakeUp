package betterlifethanksapp.gmail.com.locationWakeUp.ui.currentRoute

import android.content.Context
import android.content.Intent
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import betterlifethanksapp.gmail.com.locationWakeUp.R
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
        ldh = LocationDataHelper(ldo,this)
        internetConnect = InternetConnect()
    }



    suspend fun getDistenceInfo(text:String)
    {
        internetConnect.isInternetAvailable()//TODO maybe in future I'll inform user when he have internet and location access ;)
        ldh.checkPermissionCorrect()
        if(ldh.checkLocationOn()) {
            ldh.estimateDistance(text)
        }
    }

    suspend fun setAlarmClockWithLocation() {
        ldh.launchLocationRequests()
    }

    override  fun successSingleLocation(distance: Float) {
        callbackListener.successSingleLocation(distance)
    }

    override fun failedSingleLocation() {
        callbackListener.failedSingleLocation()
    }

    override fun successMultipleLocation(distance: Float) {
        _distance.value = distance
        if(!isWakeUp && distance <=3.0f)
        {
            isWakeUp=true
            wakeUpNow()
        }
    }

    private fun wakeUpNow() {
        val intent = Intent(context.applicationContext,NotificationWakeUpService::class.java)
        intent.putExtra(NotificationWakeUpService.MESSAGE(),context.applicationContext.getString(R.string.wake_up))
        context.applicationContext.startService(intent)
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
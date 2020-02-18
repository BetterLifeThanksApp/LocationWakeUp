package betterlifethanksapp.gmail.com.locationWakeUp.ui.currentRoute

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import betterlifethanksapp.gmail.com.locationWakeUp.data.internet.InternetConnect
import betterlifethanksapp.gmail.com.locationWakeUp.data.internet.InternetConnectI
import betterlifethanksapp.gmail.com.locationWakeUp.data.location.LocationDataHelper
import kotlinx.coroutines.coroutineScope
import java.net.UnknownHostException

class CurrentRouteRepository(private val ldh:LocationDataHelper,private val internetConnect:InternetConnectI ) {

    suspend fun getDistenceInfo(text:String)
    {
        internetConnect.isInternetAvailable() //TODO maybe inform when you have internet access
        ldh.checkPermissionCorrect() //TODO maybe not exception because if you grant permission(if you clicked yes on dialogbox) next method don't run ldh.estimateDistance(text) not run
        ldh.estimateDistance(text)
    }
}
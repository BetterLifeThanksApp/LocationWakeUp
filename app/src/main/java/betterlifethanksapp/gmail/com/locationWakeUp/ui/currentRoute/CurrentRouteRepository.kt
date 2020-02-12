package betterlifethanksapp.gmail.com.locationWakeUp.ui.currentRoute

import android.util.Log
import betterlifethanksapp.gmail.com.locationWakeUp.data.internet.InternetConnect
import betterlifethanksapp.gmail.com.locationWakeUp.data.internet.InternetConnectI
import betterlifethanksapp.gmail.com.locationWakeUp.data.location.LocationDataHelper
import kotlinx.coroutines.coroutineScope
import java.net.UnknownHostException

class CurrentRouteRepository(private val ldh:LocationDataHelper,private val internetConnect:InternetConnectI ) {

    suspend fun getDistenceInfo(text:String)
    {
        internetConnect.isInternetAvailable()
        //ldh.checkPermissionCorrect()
        //ldh.estimateDistance(text)
    }
}
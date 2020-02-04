package betterlifethanksapp.gmail.com.locationWakeUp.ui.currentRoute

import android.util.Log
import betterlifethanksapp.gmail.com.locationWakeUp.data.internet.InternetConnect
import betterlifethanksapp.gmail.com.locationWakeUp.data.location.LocationDataHelper
import java.net.UnknownHostException

class CurrentRouteRepository(private val ldh:LocationDataHelper) {

    fun getDistenceInfo(text:String)
    {
        val internetConnect = InternetConnect()
        internetConnect.isInternetAvailable()
        //ldh.checkPermissionCorrect()
        //ldh.estimateDistance(text)
    }
}
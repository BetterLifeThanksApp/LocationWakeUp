package betterlifethanksapp.gmail.com.locationWakeUp.data.internet

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.net.InetAddress
import java.net.UnknownHostException


class InternetConnect:InternetConnectI {

    override suspend fun isInternetAvailable() = withContext(Dispatchers.IO){

        //throw UnknownHostException("Network doesn't work correct")

        try {
            InetAddress.getByName("www.google.com")
        } catch (e: UnknownHostException) {
            throw UnknownHostException("Network doesn't work correct")
        }

    }
}
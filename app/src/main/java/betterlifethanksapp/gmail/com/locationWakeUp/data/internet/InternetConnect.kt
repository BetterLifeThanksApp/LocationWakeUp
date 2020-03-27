package betterlifethanksapp.gmail.com.locationWakeUp.data.internet

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.InetAddress
import java.net.UnknownHostException


class InternetConnect:InternetConnectI {

    override suspend fun isInternetAvailable() = withContext(Dispatchers.IO){

        //throw UnknownHostException("Network doesn't work correct")

        try {
            InetAddress.getByName("www.google.com")
            //TODO change this method.Sometimes isn't throw exception.Sometimes it works when you don't have internet access yes but you connect to wifi
        } catch (e: UnknownHostException) {
            throw UnknownHostException("Network doesn't work correct")
        }

    }
}
package betterlifethanksapp.gmail.com.locationWakeUp.data.internet

import java.lang.Exception
import java.net.InetAddress
import java.net.UnknownHostException


class InternetConnect {

    fun isInternetAvailable(){

        throw UnknownHostException("Network doesn't work correct")
        /*
        try {
            //InetAddress.getByName("www.google.com")
        } catch (e: UnknownHostException) {
            throw UnknownHostException("Network doesn't work correct")
        }
        */
    }
}
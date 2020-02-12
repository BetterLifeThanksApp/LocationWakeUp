package betterlifethanksapp.gmail.com.locationWakeUp.data.internet

import java.net.InetAddress

interface InternetConnectI {

    suspend fun isInternetAvailable(): InetAddress
}
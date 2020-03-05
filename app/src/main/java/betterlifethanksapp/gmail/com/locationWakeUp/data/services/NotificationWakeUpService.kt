package betterlifethanksapp.gmail.com.locationWakeUp.data.services

import android.app.IntentService
import android.content.Intent
import android.util.Log

class NotificationWakeUpService: IntentService("NotificationWakeUpService") {

    companion object{
        fun MESSAGE()  = "NotificationMessage"
    }


    override fun onHandleIntent(intent: Intent?) {
        val text = intent?.getStringExtra(MESSAGE())
        Log.i("Intent","$text")
        //createNotification()
    }
}
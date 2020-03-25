package betterlifethanksapp.gmail.com.locationWakeUp.data.services

import android.R
import android.app.IntentService
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.renderscript.RenderScript
import androidx.core.app.NotificationCompat

class NotificationWakeUpService: IntentService("NotificationWakeUpService") {

    companion object{
        fun MESSAGE()  = "NotificationMessage"
        fun NOTIFICATION_ID():Int = 9472
    }


    override fun onHandleIntent(intent: Intent?) {
        val text = intent?.getStringExtra(MESSAGE())
        if (text != null) {
            createNotification(text)
        }
    }

    private fun createNotification(text:String) {
        val builder = createBuilder(text)
        sendNotification(builder)

    }

    private fun sendNotification(builder: NotificationCompat.Builder) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(NOTIFICATION_ID(),builder.build())
    }

    private fun createBuilder(text:String): NotificationCompat.Builder {

        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

        return NotificationCompat.Builder(applicationContext,"hello my ...")
            .setSmallIcon(R.drawable.ic_dialog_map)
            .setContentTitle("Tytuł")
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setTicker("Alert!")
            .setSound(alarmSound)//TODO zeby dzwiek gral uzytkownik musi miec nie wyciszona opcje 'powiadomienia' gdy klikniesz na przycisk glosnosi mozesz tą opcje wlaczyc
            .setVibrate(longArrayOf(0,2000,200,3000,500,1000,8000,600,400,300))//TODO jesli uzytkownik na wylaczone wibracje w telefonie to opcje nie zadziala
            .setAutoCancel(true)
    }
}
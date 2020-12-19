package kz.kolesateam.confapp.notifications

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*

const val NOTIFICATION_CONTENT_KEY = "notification_title"

class NotificationAlarmHelper(
        private val application: Application
){
    fun createNotificationAlarm(
            content: String
    ){
        val alarmManager: AlarmManager? = application.getSystemService(
                Context.ALARM_SERVICE
        ) as? AlarmManager

        val pendingIntent: PendingIntent = Intent(application, NotificationAlarmBroadcastReceiver::class.java).apply{
            putExtra(NOTIFICATION_CONTENT_KEY, content)
        }.let {
            PendingIntent.getBroadcast(application, 0, it, 0)
        }

        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR, 5)
        calendar.set(Calendar.MINUTE, 22)
        alarmManager?.setExact(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis()  + 5000,
                pendingIntent
        )
    }
}
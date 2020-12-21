package kz.kolesateam.confapp.notifications

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import kz.kolesateam.confapp.events.data.models.EventApiData
import java.util.*

const val NOTIFICATION_CONTENT_KEY = "notification_title"

class NotificationAlarmHelper(
        private val application: Application
){
    private var pendingIntent: PendingIntent? = null

    private val alarmManager: AlarmManager? = application.getSystemService(
        Context.ALARM_SERVICE
    ) as? AlarmManager

    fun createNotificationAlarm(
            content: String
    ){
        pendingIntent = Intent(application, NotificationAlarmBroadcastReceiver::class.java).apply{
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


    fun cancelNotificationAlarm(eventData: EventApiData) {
        pendingIntent ?: return
        val intent = Intent(application, NotificationAlarmBroadcastReceiver::class.java)
        val pendingIn = PendingIntent.getBroadcast(application,
            eventData.id!!,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT)

        alarmManager?.cancel(pendingIn)
    }
}
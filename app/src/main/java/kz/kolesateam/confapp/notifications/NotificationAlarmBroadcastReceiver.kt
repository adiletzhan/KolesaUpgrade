package kz.kolesateam.confapp.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class NotificationAlarmBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("CONF_APP", "broadcast fired")
        val content: String = intent?.getStringExtra(NOTIFICATION_CONTENT_KEY).orEmpty()

        NotificationHelper.sendNotification(
                title = "Не пропустите следующий доклад",
                content = content
        )
    }

}
package kz.kolesateam.confapp.notifications

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kz.kolesateam.confapp.R

const val NOTIFICATION_CHANNEL_NAME = "favorite_notification_channel"

object NotificationHelper{
    private var notificationIdCounter : Int = 0
    private lateinit var application: Application

    fun init(application: Application) {
        this.application = application
        initChannel()
    }

    fun sendNotification(
            title: String,
            content: String
    ){
        val notification: Notification = getNotification(
                title = title,
                content = content
        )

        NotificationManagerCompat.from(application).notify(
                notificationIdCounter++,
                notification
        )
    }

    private fun getNotification(
            title: String,
            content: String): Notification = NotificationCompat.Builder(
            application, NOTIFICATION_CHANNEL_NAME
    )
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_favorite_filled)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

    private fun initChannel(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val channelId: String = NOTIFICATION_CHANNEL_NAME
        val importance: Int = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(
                channelId,
                channelId,
                importance
        )

        val notificationManager: NotificationManager = application.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }
}
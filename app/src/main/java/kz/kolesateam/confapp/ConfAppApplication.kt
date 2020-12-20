package kz.kolesateam.confapp

import android.app.Application
import kz.kolesateam.confapp.allevents.di.eventScreenModule
import kz.kolesateam.confapp.details.eventDetailsModule
import kz.kolesateam.confapp.di.applicationModule
import kz.kolesateam.confapp.di.favoriteEventsModule
import kz.kolesateam.confapp.di.userNameModule
import kz.kolesateam.confapp.events.di.upcomingEventsScreenModule
import kz.kolesateam.confapp.notifications.ConfAppNotificationManager
import kz.kolesateam.confapp.notifications.NotificationHelper
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ConfAppApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationHelper.init(this)
        /*
        ConfAppNotificationManager.init(
            applicationContext = this
        )
        */
        startKoin{
            androidContext(this@ConfAppApplication)
            modules(
                    eventScreenModule,
                    upcomingEventsScreenModule,
                    applicationModule,
                    userNameModule,
                    favoriteEventsModule,
                    eventDetailsModule
            )
        }
    }
}
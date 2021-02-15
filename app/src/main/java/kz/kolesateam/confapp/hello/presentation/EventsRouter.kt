package kz.kolesateam.confapp.hello.presentation

import android.content.Context
import android.content.Intent
import kz.kolesateam.confapp.events.presentation.UpcomingEventsActivity

class EventsRouter {

    fun createIntent(
            context: Context
    ) : Intent = Intent(context, UpcomingEventsActivity:: class.java)
}
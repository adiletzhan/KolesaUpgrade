package kz.kolesateam.confapp.details

import android.content.Context
import android.content.Intent
import kz.kolesateam.confapp.details.presentation.EventDetailsActivity

internal const val EVENT_ID = "event_id"

class EventDetailsRouter {

    fun createIntent(
            context: Context,
            eventId: Int
    ): Intent = Intent(context, EventDetailsActivity::class.java).apply {
        putExtra(EVENT_ID, eventId)
    }
}
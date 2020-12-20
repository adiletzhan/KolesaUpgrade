package kz.kolesateam.confapp.details

import android.content.Context
import android.content.Intent
import kz.kolesateam.confapp.details.presentation.EventDetailsActivity

internal const val EVENT_TITLE_KEY = "event_title_key"

class EventDetailsRouter {

    fun createIntent(
            context: Context,
            eventTitle: String
    ): Intent = Intent(context, EventDetailsActivity::class.java).apply {
        putExtra(EVENT_TITLE_KEY, eventTitle)
    }
}
package kz.kolesateam.confapp.domain.listeners

import kz.kolesateam.confapp.events.data.models.EventApiData

interface EventClickListener {
    fun onEventClick(
            eventData: EventApiData,
    )
}
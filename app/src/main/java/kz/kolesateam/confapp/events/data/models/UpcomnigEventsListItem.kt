package kz.kolesateam.confapp.events.data.models

data class UpcomingEventsListItem<T>(
        val type: Int,
        val data: T)
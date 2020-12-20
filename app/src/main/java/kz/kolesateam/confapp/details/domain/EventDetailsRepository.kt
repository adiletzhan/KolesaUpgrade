package kz.kolesateam.confapp.details.domain

import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem
import kz.kolesateam.confapp.models.ResponseData


interface EventDetailsRepository {
    fun getEventDetails(
            eventId: Int
    ): ResponseData<EventApiData, Exception>
}
package kz.kolesateam.confapp.events.domain


import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem
import kz.kolesateam.confapp.models.ResponseData

interface UpcomingEventsRepository {
    fun getUpcomingEvents(): ResponseData<List<UpcomingEventsListItem>, Exception>
}
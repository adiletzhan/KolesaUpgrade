package kz.kolesateam.confapp.allevents.domain

import kz.kolesateam.confapp.allevents.data.datasource.AllEventsListItem
import kz.kolesateam.confapp.events.data.models.ResponseData
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem

interface AllEventsRepository {
    fun getAllEvents(
            branchId: Int,
            branchTitle: String
    ) :  ResponseData<List<AllEventsListItem.EventListItem>, Exception>
}
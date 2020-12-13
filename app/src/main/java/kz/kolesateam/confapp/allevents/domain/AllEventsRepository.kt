package kz.kolesateam.confapp.allevents.domain

import kz.kolesateam.confapp.allevents.data.models.AllEventsListItem
import kz.kolesateam.confapp.models.ResponseData

interface AllEventsRepository {
    fun getAllEvents(
            branchId: Int,
            branchTitle: String
    ) : ResponseData<List<AllEventsListItem.EventListItem>, Exception>
}
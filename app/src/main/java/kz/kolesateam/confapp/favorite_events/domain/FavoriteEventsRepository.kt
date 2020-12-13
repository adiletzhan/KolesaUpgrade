package kz.kolesateam.confapp.favorite_events.domain

import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.models.ResponseData

interface FavoriteEventsRepository {
    fun saveFavoriteEvents(
            eventApiData: EventApiData
    )

    fun removeFavoriteEvents(
            eventId: Int?
    )

    fun getAllFavoriteEvents(): ResponseData<List<EventApiData>, Exception>
}
package kz.kolesateam.confapp.domain.listeners

import kz.kolesateam.confapp.events.data.models.EventApiData

interface FavoritesClickListener {
    fun onFavoritesClicked(eventData: EventApiData)
}
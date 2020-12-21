package kz.kolesateam.confapp.details.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.allevents.data.models.AllEventsListItem
import kz.kolesateam.confapp.details.domain.EventDetailsRepository
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.favorite_events.domain.FavoriteEventsRepository
import kz.kolesateam.confapp.models.ProgressState
import kz.kolesateam.confapp.models.ResponseData
import kz.kolesateam.confapp.notifications.NotificationAlarmHelper
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

class EventDetailsViewModel(
        private val eventDetailsRepository: EventDetailsRepository,
        private val favoritesRepository: FavoriteEventsRepository,
        private val notificationAlarmHelper: NotificationAlarmHelper

): ViewModel() {

    private val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()
    private val eventDetailsLiveData: MutableLiveData<EventApiData> = MutableLiveData()
    private val errorLiveData: MutableLiveData<Exception> = MutableLiveData()

    fun getProgressLiveData(): LiveData<ProgressState> = progressLiveData

    fun getEventDetailsLiveData(): LiveData<EventApiData> = eventDetailsLiveData

    fun getErrorLiveData(): LiveData<Exception> = errorLiveData

    fun onStarted(eventId: Int) {
        getEventDetails(eventId)
    }

    private fun getEventDetails(eventId: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            progressLiveData.value = ProgressState.Loading

            val eventDetailsResponse: ResponseData<EventApiData, Exception> =
                    withContext(Dispatchers.IO) {
                        eventDetailsRepository.getEventDetails(eventId)
                    }

            when (eventDetailsResponse) {
                is ResponseData.Success -> eventDetailsLiveData.value = eventDetailsResponse.result

                is ResponseData.Error -> errorLiveData.value = eventDetailsResponse.error
            }
            progressLiveData.value = ProgressState.Done
        }
    }


    fun onFavoriteClick(eventData: EventApiData) {
        when (eventData.isFavorite) {
            true -> {
                favoritesRepository.saveFavoriteEvent(eventData)
                notificationAlarmHelper.createNotificationAlarm(eventData.title.toString())
            }

            else -> {
                favoritesRepository.removeFavoriteEvent(eventData.id)
                notificationAlarmHelper.cancelNotificationAlarm(eventData)
            }
        }
    }

}
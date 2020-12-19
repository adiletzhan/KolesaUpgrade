package kz.kolesateam.confapp.events.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.allevents.data.models.AllEventsListItem
import kz.kolesateam.confapp.allevents.domain.AllEventsRepository
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem
import kz.kolesateam.confapp.events.domain.UpcomingEventsRepository
import kz.kolesateam.confapp.favorite_events.domain.FavoriteEventsRepository
import kz.kolesateam.confapp.models.ProgressState
import kz.kolesateam.confapp.models.ResponseData
import kz.kolesateam.confapp.notifications.NotificationAlarmHelper
import kz.kolesateam.confapp.notifications.NotificationHelper

class UpcomingEventsViewModel(
        private val upcomingEventsRepository: UpcomingEventsRepository,
        private val favoriteEventsRepository: FavoriteEventsRepository,
        private val notificationAlarmHelper: NotificationAlarmHelper
): ViewModel() {

    private val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()
    private val upcomingEventsLiveData: MutableLiveData<List<UpcomingEventsListItem>> =
            MutableLiveData()
    private val errorLiveData: MutableLiveData<Exception> = MutableLiveData()

    fun getProgressLiveData(): LiveData<ProgressState> = progressLiveData

    fun getUpcomingEventsLiveData(): LiveData<List<UpcomingEventsListItem>> = upcomingEventsLiveData

    fun getErrorLiveData(): LiveData<Exception> = errorLiveData

    fun onStarted() {
        getAllEvents()
    }

    fun onFavoriteClick(
            eventData: EventApiData
    ){
        when(eventData.isFavorite) {
            true -> {
                favoriteEventsRepository.saveFavoriteEvent(eventData)
                scheduleEvent(eventData)
            }
            else -> favoriteEventsRepository.removeFavoriteEvent(eventData.id)
        }
    }

    private fun scheduleEvent(eventData: EventApiData){
        notificationAlarmHelper.createNotificationAlarm(
                content = eventData.title.orEmpty()
        )
    }
    private fun getAllEvents() {
        GlobalScope.launch(Dispatchers.Main) {

            progressLiveData.value = ProgressState.Loading

            val upcomingEventsResponse: ResponseData<List<UpcomingEventsListItem>, Exception> =
                    withContext(Dispatchers.IO) {
                        upcomingEventsRepository.getUpcomingEvents()
                    }

            when (upcomingEventsResponse) {
                is ResponseData.Success -> {
                    upcomingEventsLiveData.value = upcomingEventsResponse.result
                }

                is ResponseData.Error -> errorLiveData.value = upcomingEventsResponse.error
            }
            progressLiveData.value = ProgressState.Done
        }
    }

}
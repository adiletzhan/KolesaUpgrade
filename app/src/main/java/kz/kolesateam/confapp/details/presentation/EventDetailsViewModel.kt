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
import kz.kolesateam.confapp.models.ProgressState
import kz.kolesateam.confapp.models.ResponseData

class EventDetailsViewModel(
        private val eventDetailsRepository: EventDetailsRepository
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
}
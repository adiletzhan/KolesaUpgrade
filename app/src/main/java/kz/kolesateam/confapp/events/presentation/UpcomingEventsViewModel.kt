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
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem
import kz.kolesateam.confapp.events.domain.UpcomingEventsRepository
import kz.kolesateam.confapp.models.ProgressState
import kz.kolesateam.confapp.models.ResponseData

class UpcomingEventsViewModel(
        private val upcomingEventsRepository: UpcomingEventsRepository
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
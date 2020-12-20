package kz.kolesateam.confapp.allevents.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.allevents.data.models.AllEventsListItem
import kz.kolesateam.confapp.allevents.domain.AllEventsRepository
import kz.kolesateam.confapp.models.ProgressState
import kz.kolesateam.confapp.models.ResponseData

class AllEventsViewModel(
    private val allEventsRepository: AllEventsRepository
) : ViewModel() {

    private val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()
    private val allEventsLiveData: MutableLiveData<List<AllEventsListItem.EventListItem>> =
        MutableLiveData()
    private val errorLiveData: MutableLiveData<Exception> = MutableLiveData()

    fun getProgressLiveData(): LiveData<ProgressState> = progressLiveData

    fun getAllEventsLiveData(): LiveData<List<AllEventsListItem.EventListItem>> = allEventsLiveData

    fun getErrorLiveData(): LiveData<Exception> = errorLiveData

    fun onStarted(branchId: Int, branchTitle: String) {
        getAllEvents(branchId, branchTitle)
    }

    private fun getAllEvents(branchId: Int, branchTitle: String) {
        GlobalScope.launch(Dispatchers.Main) {
            progressLiveData.value = ProgressState.Loading

            val allEventsResponse: ResponseData<List<AllEventsListItem.EventListItem>, Exception> =
                withContext(Dispatchers.IO) {
                    allEventsRepository.getAllEvents(branchId, branchTitle)
                }

            when (allEventsResponse) {
                is ResponseData.Success -> allEventsLiveData.value = allEventsResponse.result

                is ResponseData.Error -> errorLiveData.value = allEventsResponse.error
            }
            progressLiveData.value = ProgressState.Done
        }
    }
}
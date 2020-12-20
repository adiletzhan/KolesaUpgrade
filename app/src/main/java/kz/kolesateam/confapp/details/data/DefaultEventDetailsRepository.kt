package kz.kolesateam.confapp.details.data

import kz.kolesateam.confapp.allevents.data.models.AllEventsListItem
import kz.kolesateam.confapp.details.domain.EventDetailsRepository
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem
import kz.kolesateam.confapp.models.ResponseData

class DefaultEventDetailsRepository(
        private val eventDetailsDataSource: EventDetailsDataSource
): EventDetailsRepository {

    override fun getEventDetails(
            eventId: Int
    ): ResponseData<EventApiData, Exception> {
        return try {
            val response = eventDetailsDataSource.getEventDetails(eventId).execute()

            if (response.isSuccessful){
                ResponseData.Success(response.body()!!)
            } else{
                ResponseData.Error(java.lang.Exception("Response not Successful"))
            }
        }catch (e: Exception) {
            ResponseData.Error(e)
        }
    }
}
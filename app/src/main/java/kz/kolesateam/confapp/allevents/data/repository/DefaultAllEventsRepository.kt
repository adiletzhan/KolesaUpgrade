package kz.kolesateam.confapp.allevents.data.repository

import kz.kolesateam.confapp.allevents.data.datasource.AllEventsDataSource
import kz.kolesateam.confapp.allevents.data.datasource.AllEventsListItem
import kz.kolesateam.confapp.allevents.data.datasource.EVENT_TYPE
import kz.kolesateam.confapp.allevents.domain.AllEventsRepository
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.ResponseData
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem

class DefaultAllEventsRepository(private val allEventsDataSource: AllEventsDataSource): AllEventsRepository{
    /*
    override fun getAllEvents(branchId: Int, branchTitle: String): ResponseData<List<UpcomingEventsListItem>, Exception> {
        TODO("Not yet implemented")
    }
     */

     override fun getAllEvents(
             branchId: Int,
             branchTitle: String
     ): ResponseData<List<AllEventsListItem.EventListItem>, Exception> {
         try {
             val response = allEventsDataSource.getAllEvents(branchId).execute()

             if (response.isSuccessful){
                /*
                 val allEventsListItemList: MutableList<AllEventsListItem> =
                         mutableListOf()
                */

                 val eventsListItemList: List<AllEventsListItem.EventListItem> =
                         response.body()!!.map { eventApiData ->
                             AllEventsListItem.EventListItem(
                                     data = eventApiData
                             )
                         }
                 return ResponseData.Success(eventsListItemList)
             } else{

                 return ResponseData.Error(java.lang.Exception("Response not Successful"))
             }
         }catch (e: Exception) {
             return ResponseData.Error(e)
         }
     }
}
package kz.kolesateam.confapp.allevents.data.repository

import kz.kolesateam.confapp.allevents.data.datasource.AllEventsDataSource
import kz.kolesateam.confapp.allevents.domain.AllEventsRepository
import kz.kolesateam.confapp.events.data.models.ResponseData
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem

class DefaultAllEventsRepository(private val allEventsDataSource: AllEventsDataSource): AllEventsRepository{
    override fun getAllEvents(branchId: Int, branchTitle: String): ResponseData<List<UpcomingEventsListItem>, Exception> {
        TODO("Not yet implemented")
    }
    /*
     override fun getAllEvents(
             branchId: Int,
             branchTitle: String
     ): ResponseData<List<UpcomingEventsListItem>, Exception> {
         try {
             val response = allEventsDataSource.getAllEvents(branchId).execute()
             //TODO
         }catch (e: Exception) {
             return ResponseData.Error(e)
         }
     }
     */

}
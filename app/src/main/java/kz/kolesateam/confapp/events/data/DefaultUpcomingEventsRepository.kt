package kz.kolesateam.confapp.events.data

import kz.kolesateam.confapp.domain.UserNameDataSource
import kz.kolesateam.confapp.events.data.datasource.UpcomingEventsDataSource
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem
import kz.kolesateam.confapp.events.domain.UpcomingEventsRepository
import kz.kolesateam.confapp.models.ResponseData


class DefaultUpcomingEventsRepository (
        private val upcomingEventsDataSource : UpcomingEventsDataSource,
        private val userNameDataSource: UserNameDataSource,
) : UpcomingEventsRepository {

    override fun getUpcomingEvents(): ResponseData<List<UpcomingEventsListItem>, Exception> {
        try {

            val response = upcomingEventsDataSource.getUpcomingEvents().execute()

            if (response.isSuccessful){

                val upcomingEventsListItemList: List<UpcomingEventsListItem> =
                        listOf(getHeaderItem()) + getBranchItems(response.body()!!)

                return ResponseData.Success(upcomingEventsListItemList)
            } else{
                return ResponseData.Error(java.lang.Exception("Response not Successful"))
            }
        }catch (e: Exception) {
            return ResponseData.Error(e)
        }
    }

    private fun getBranchItems(
            branchList: List<BranchApiData>
    ): List<UpcomingEventsListItem> = branchList.map { branchApiData ->
        UpcomingEventsListItem(
                type = 2,
                data = branchApiData
        )
    }

    private fun getHeaderItem(): UpcomingEventsListItem = UpcomingEventsListItem(
            type = 1,
            data = userNameDataSource.getUserName()
    )


    private fun prepareUpcomingEventsList(
        upcomingEvents: List<UpcomingEventsListItem>
    ){
        upcomingEvents.forEach{
            val upcomingEvent: EventApiData = it.data as? EventApiData ?: return@forEach

        }
    }
}
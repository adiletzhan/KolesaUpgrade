package kz.kolesateam.confapp.events.data

import kz.kolesateam.confapp.events.data.models.*
import retrofit2.Response


class UpcomingEventsRepository {

    private val apiClient: ApiClient = ApiService.getApiClient()

    fun getAllEventsByBranchId(branchId: Int): Response<List<EventApiData>>{
        return try{
            val response: retrofit2.Response<List<EventApiData>> =
                apiClient.getAllEventsByBranchId(branchId = branchId).execute()

            if(response.isSuccessful){

            }else{

            }
        }catch (ex: Exception){
            Response.Error(ex.javaClass.toString())
        }
    }
}
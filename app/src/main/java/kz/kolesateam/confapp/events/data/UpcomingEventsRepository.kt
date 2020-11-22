package kz.kolesateam.confapp.events.data

import kz.kolesateam.confapp.events.data.models.BranchApiData
import okhttp3.ResponseBody
import retrofit2.Response

class UpcomingEventsRepository {
    private val apiClient: ApiClient =ApiClient.create()

    fun getUpcomingEvents(): ResponseData<List<BranchApiData>, String>{
        return try {
            val response: Response<List<BranchApiData>> = apiClient.getUpcomingEvents().execute()

            if (response.isSuccessful){
                ResponseData.S
            }
        }
    }
}
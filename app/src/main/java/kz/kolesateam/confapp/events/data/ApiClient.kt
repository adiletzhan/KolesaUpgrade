package kz.kolesateam.confapp.events.data


import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "http://37.143.8.68:2020/"

interface ApiClient {
    @GET("/upcoming_events")
    fun getUpcomingEvents(): Call<List<BranchApiData>>

    @GET("/branch_events/{branch_id}")
    fun getAllEventsByBranchId(@Path("branch_id") branchId: Int): Call<List<EventApiData>>

    /*
    companion object RetrofitClient {
        fun create(): ApiClient {
            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build()

            return retrofit.create(ApiClient::class.java);
        }
    }
    */
}
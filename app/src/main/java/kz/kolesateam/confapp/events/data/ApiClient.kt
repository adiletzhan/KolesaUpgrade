package kz.kolesateam.confapp.events.data


import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiClient {
    @GET("/upcoming_events")
    fun getUpcomingEvents(): Call<List<BranchApiData>>

}
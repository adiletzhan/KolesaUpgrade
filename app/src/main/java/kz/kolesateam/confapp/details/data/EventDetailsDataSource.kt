package kz.kolesateam.confapp.details.data

import kz.kolesateam.confapp.events.data.models.EventApiData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EventDetailsDataSource {

    @GET("/events/{event_id}")
    fun getEventDetails(@Path("event_id") eventId: Int): Call<EventApiData>
}
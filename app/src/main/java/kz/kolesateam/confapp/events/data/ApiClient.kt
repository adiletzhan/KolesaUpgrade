package kz.kolesateam.confapp.events.data

import com.fasterxml.jackson.databind.JsonNode
import kz.kolesateam.confapp.events.data.models.BranchApiData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "http://37.143.8.68:2020/"

interface ApiClient {
    @GET("/upcoming_events")
    fun getUpcomingEvents(): Call<List<BranchApiData>>

    companion object RetrofitClient {
        fun create(): ApiClient {
            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build()

            return retrofit.create(ApiClient::class.java);
        }
    }
}
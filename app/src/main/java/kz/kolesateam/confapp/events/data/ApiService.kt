package kz.kolesateam.confapp.events.data


import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

private const val BASE_URL = "http://37.143.8.68:2020"

object ApiService {
    fun getApiClient(): ApiClient{
        return getRetrofitApi().create(ApiClient::class.java)
    }

    private fun getRetrofitApi(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }
}
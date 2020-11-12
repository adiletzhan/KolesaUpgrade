package kz.kolesateam.confapp.events.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.fasterxml.jackson.databind.JsonNode
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.io.InterruptedIOException
import java.net.SocketException


val apiRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://37.143.8.68:2020")
        .addConverterFactory(JacksonConverterFactory.create())
        .build()

val apiClient: ApiClient = apiRetrofit.create(ApiClient::class.java)

@Suppress("DEPRECATION")
class UpcomingEventsActivity : AppCompatActivity() {
    private lateinit var responseTextView: TextView
    private lateinit var syncLoadButton: Button
    private lateinit var asyncLoadButton: Button
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)

        bindViews()



        syncLoadButton.setOnClickListener{
            Log.d("UpcomingEventsActivity", "Synchronous loading")
            progressBar.visibility = ProgressBar.VISIBLE
            loadApiDataSync()
        }

        asyncLoadButton.setOnClickListener{
            Log.d("UpcomingEventsActivity", "Asynchronous loading")
            progressBar.visibility = ProgressBar.VISIBLE
            loadApiDataAsync()
        }
    }

    private fun bindViews(){
        responseTextView = findViewById(R.id.activity_upcoming_events_text_view)
        syncLoadButton = findViewById(R.id.activity_upcoming_events_sync_load_button)
        asyncLoadButton = findViewById(R.id.activity_upcoming_events_async_load_button)
        progressBar = findViewById(R.id.activity_upcoming_progressBar)
    }

    private fun loadApiDataSync(){
        Thread{
            try {
                val response: Response<JsonNode> = apiClient.getUpcomingEvents().execute()
                if (response.isSuccessful) {
                    val body: JsonNode = response.body()!!
                    runOnUiThread {
                        responseTextView.setTextColor(resources.getColor(R.color.upcoming_events_sync_load_textColor))
                        progressBar.visibility = ProgressBar.INVISIBLE
                        responseTextView.text = body.toString()
                    }
                }
            } catch (ConnectException: SocketException){
                runOnUiThread {
                    responseTextView.setTextColor(resources.getColor(R.color.upcoming_events_error_load_textColor))
                    progressBar.visibility = ProgressBar.INVISIBLE
                    responseTextView.text = ConnectException.toString()
                }
            } catch (SocketTimeoutException: InterruptedIOException){
                runOnUiThread {
                    responseTextView.setTextColor(resources.getColor(R.color.upcoming_events_error_load_textColor))
                    progressBar.visibility = ProgressBar.INVISIBLE
                    responseTextView.text = SocketTimeoutException.toString()
                }
            }
        }.start()
    }

    private fun loadApiDataAsync(){

        apiClient.getUpcomingEvents().enqueue(object : Callback<JsonNode> {

            override fun onResponse(call: Call<JsonNode>, response: Response<JsonNode>) {
                if (response.isSuccessful) {
                    val body: JsonNode = response.body()!!
                    responseTextView.setTextColor(resources.getColor(R.color.upcoming_events_async_load_textColor))
                    progressBar.visibility = ProgressBar.INVISIBLE
                    responseTextView.text = body.toString()
                }
            }

            override fun onFailure(call: Call<JsonNode>, t: Throwable) {
                progressBar.visibility = ProgressBar.INVISIBLE
                responseTextView.setTextColor(resources.getColor(R.color.upcoming_events_error_load_textColor))
                responseTextView.text = t.localizedMessage
            }
        })

    }
}
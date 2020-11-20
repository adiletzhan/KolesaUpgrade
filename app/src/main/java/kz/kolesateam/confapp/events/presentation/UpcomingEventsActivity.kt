package kz.kolesateam.confapp.events.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.ApiClient
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.SpeakerApiData
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
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
                val response: Response<ResponseBody> = apiClient.getUpcomingEventsSync().execute()
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    val responseJsonString = responseBody.string()
                    val responseJsonArray = JSONArray(responseJsonString)

                    val apiBranchDataList = parseBranchesJsonArray(responseJsonArray)
                    runOnUiThread {
                        //
                        Log.d("ParsingResult", apiBranchDataList.toString())
                        responseTextView.setTextColor(resources.getColor(R.color.upcoming_events_sync_load_textColor))
                        progressBar.visibility = ProgressBar.INVISIBLE
                        responseTextView.text = apiBranchDataList.toString()
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
        apiClient.getUpcomingEventsAsync().enqueue(object : Callback<List<BranchApiData>> {
            override fun onResponse(call: Call<List<BranchApiData>>, response: Response<List<BranchApiData>>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!

                    val apiBranchDataList = responseBody

                    Log.d("ParsingResult", apiBranchDataList.toString())

                    responseTextView.setTextColor(resources.getColor(R.color.upcoming_events_async_load_textColor))
                    progressBar.visibility = ProgressBar.INVISIBLE
                    responseTextView.text = apiBranchDataList.toString()
                }
            }

            override fun onFailure(call: Call<List<BranchApiData>>, t: Throwable) {
                progressBar.visibility = ProgressBar.INVISIBLE
                responseTextView.setTextColor(resources.getColor(R.color.upcoming_events_error_load_textColor))
                responseTextView.text = t.localizedMessage
            }
        })
    }


    private fun parseBranchesJsonArray(
        responseJsonArray: JSONArray
    ): List<BranchApiData>{
        val branchList = mutableListOf<BranchApiData>()
        for (index in 0 until responseJsonArray.length()){
            val branchJsonObject= (responseJsonArray[index] as? JSONObject)?: continue
            val branchApiData = parseBranchJsonObject(branchJsonObject)
            branchList.add(branchApiData)
        }
        return branchList
    }

    private fun parseBranchJsonObject(
            branchJsonObject: JSONObject
    ): BranchApiData {
        val id = branchJsonObject.getInt("id")
        val title = branchJsonObject.getString("title")
        val eventsJsonArray = branchJsonObject.getJSONArray("events")

        val eventsApiList = mutableListOf<EventApiData>()
        for (index in 0 until eventsJsonArray.length()){
            val eventJsonObject = (eventsJsonArray[index] as? JSONObject) ?: continue

            val eventApiData = parseEventJsonObject(eventJsonObject)

            eventsApiList.add(eventApiData)
        }
        return BranchApiData(
                id = id,
                title = title,
                events = eventsApiList
        )
    }

    private fun parseEventJsonObject(
            eventJsonObject: JSONObject
    ): EventApiData{
        val id =    eventJsonObject.getInt("id")
        val title = eventJsonObject.getString("title")
        val startTime = eventJsonObject.getString("startTime")
        val endTime = eventJsonObject.getString("endTime")
        val description = eventJsonObject.getString("description")
        val place = eventJsonObject.getString("place")

        val speakerJsonObject: JSONObject? = (eventJsonObject.get("speaker") as? JSONObject)
        var speakerApiData: SpeakerApiData? = null

        speakerJsonObject?.let {
            speakerApiData = parseSpeakerJsonObject(it)
        }

        return EventApiData(
                id = id,
                title = title,
                startTime = startTime,
                endTime = endTime,
                description = description,
                place = place,
                speaker = speakerApiData,

        )
    }

    private fun parseSpeakerJsonObject(
            speakerJsonObject: JSONObject
    ): SpeakerApiData = SpeakerApiData(
            id = speakerJsonObject.getInt("id"),
            fullName = speakerJsonObject.getString("fullName"),
            job = speakerJsonObject.getString("job"),
            photoUrl = speakerJsonObject.getString("photoUrl")
    )
}
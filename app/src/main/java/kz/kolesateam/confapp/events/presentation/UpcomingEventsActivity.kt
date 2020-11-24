    package kz.kolesateam.confapp.events.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.ApiClient
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.SpeakerApiData
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem
import kz.kolesateam.confapp.events.presentation.view.BranchAdapter
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
    private lateinit var recyclerView: RecyclerView
    private val branchAdapter: BranchAdapter = BranchAdapter()

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)

        bindViews()

        loadApiData()
    }

    private fun bindViews() {
        progressBar = findViewById(R.id.activity_upcoming_progressBar)
        recyclerView = findViewById(R.id.activity_upcoming_events_recycler_view)
        recyclerView.adapter = branchAdapter
    }

    private fun loadApiData() {

        apiClient.getUpcomingEvents().enqueue(object : Callback<List<BranchApiData>> {

            override fun onResponse(call: Call<List<BranchApiData>>, response: Response<List<BranchApiData>>) {
                if (response.isSuccessful) {
                    val upcomingEventListItemList: MutableList<UpcomingEventsListItem> = mutableListOf()
                    val headerListItem: UpcomingEventsListItem = UpcomingEventsListItem(
                            type = 1,
                            data="asdsad"
                    )

                    val branchListItemList : List<UpcomingEventsListItem> = response.body()!!.map {branchApiData ->
                        UpcomingEventsListItem(
                                type = 2,
                                data = branchApiData
                        )
                    }

                    upcomingEventListItemList.add(headerListItem)
                    upcomingEventListItemList.addAll(branchListItemList)

                    branchAdapter.setList(upcomingEventListItemList)

                    progressBar.visibility = ProgressBar.INVISIBLE
                }
            }

            override fun onFailure(call: Call<List<BranchApiData>>, t: Throwable) {
                progressBar.visibility = ProgressBar.INVISIBLE
            }
        })
    }
}
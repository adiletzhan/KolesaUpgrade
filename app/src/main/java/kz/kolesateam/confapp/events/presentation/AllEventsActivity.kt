package kz.kolesateam.confapp.events.presentation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.ApiClient
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem
import kz.kolesateam.confapp.events.presentation.view.BranchAdapter
import kz.kolesateam.confapp.events.presentation.view.EventClickListener
import kz.kolesateam.confapp.hello.presentation.SHARED_PREFERENCES
import kz.kolesateam.confapp.hello.presentation.USER_NAME_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

/*
val apiRetrofit: Retrofit = Retrofit.Builder()
    .baseUrl("http://37.143.8.68:2020")
    .addConverterFactory(JacksonConverterFactory.create())
    .build()

val apiClient: ApiClient = apiRetrofit.create(ApiClient::class.java)
*/

@Suppress("DEPRECATION")
class AllEventsActivity: AppCompatActivity(), EventClickListener {
    private lateinit var recyclerView: RecyclerView
    private val branchAdapter: BranchAdapter = BranchAdapter(eventClickListener = this)

    private lateinit var progressBar: ProgressBar


    private var isPressed = false

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

                    fillAdapterList(response.body()!!)

                    progressBar.visibility = ProgressBar.INVISIBLE
                }
            }

            override fun onFailure(call: Call<List<BranchApiData>>, t: Throwable) {
                progressBar.visibility = ProgressBar.INVISIBLE
            }
        })
    }

    private fun fillAdapterList(branchList: List<BranchApiData>){
        val upcomingEventListItemList: List<UpcomingEventsListItem> =
            listOf(getHeaderItem()) + getBranchItems(branchList)

        branchAdapter.setList(upcomingEventListItemList)
    }

    private fun getHeaderItem(): UpcomingEventsListItem = UpcomingEventsListItem(
        type = 1,
        data = "Hello, ${getUserName()}!"
    )

    private fun getBranchItems(
        branchList: List<BranchApiData>
    ): List<UpcomingEventsListItem> = branchList.map { branchApiData ->
        UpcomingEventsListItem(
            type = 2,
            data = branchApiData
        )
    }

    private fun getUserName(): String {
        val sharedPreferences: SharedPreferences = getSharedPreferences(
            SHARED_PREFERENCES,
            Context.MODE_PRIVATE)
        return sharedPreferences.getString(USER_NAME_KEY, "Someone") ?: "Someone"
    }

    override fun onBranchClick(view: View, branchTitle: String) {
        navigateToAllEventsActivity()
        // Toast.makeText(this, branchTitle, Toast.LENGTH_SHORT).show()
    }

    override fun onEventClickListener(view: View, eventTitle: String) {
        Toast.makeText(this, eventTitle, Toast.LENGTH_SHORT).show()
    }

    override fun onFavoriteClickListener(view: View) {
        view as ImageButton
        if (isPressed) {
            view.setImageDrawable(
                ContextCompat.getDrawable(
                    view.context,
                    R.drawable.ic_baseline_favorite_filled
                )
            )
        }
        else {
            view.setImageDrawable(
                ContextCompat.getDrawable(
                    view.context,
                    R.drawable.ic_like_big
                )
            )
        }
        isPressed = !isPressed
    }

    private fun navigateToAllEventsActivity(){
        val allEventsIntent = Intent(this, AllEventsActivity::class.java)
        startActivity(allEventsIntent)
    }
}
package kz.kolesateam.confapp.events.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.allevents.presentation.AllEventsActivity
import kz.kolesateam.confapp.domain.listeners.UpcomingEventsClickListener
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem
import kz.kolesateam.confapp.events.di.UPCOMING_EVENTS_VIEW_MODEL
import kz.kolesateam.confapp.events.domain.EventClickListener
import kz.kolesateam.confapp.events.presentation.view.BranchAdapter
import kz.kolesateam.confapp.favorite_events.presentation.FavoriteEventsActivity
import kz.kolesateam.confapp.models.ProgressState
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

const val BRANCH_ID = "branch_id"
const val BRANCH_TITLE = "branch_title"


@Suppress("DEPRECATION")
class UpcomingEventsActivity : AppCompatActivity(), UpcomingEventsClickListener {

    private lateinit var recyclerView: RecyclerView
    //private lateinit var buttonToFavorites: Button

    private val upcomingEventsViewModel: UpcomingEventsViewModel by viewModel(named(
            UPCOMING_EVENTS_VIEW_MODEL))

    private val branchAdapter: BranchAdapter = BranchAdapter(eventClickListener = this)

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)

        bindViews()

        observeUpcomingEventsViewModel()
        upcomingEventsViewModel.onStarted()

    }

    private fun bindViews() {
        val favoritesButton: Button = findViewById(R.id.activity_upcoming_events_toFavoritesButton)
        favoritesButton.setOnClickListener{
            startActivity(Intent(this, FavoriteEventsActivity::class.java))
        }
        progressBar = findViewById(R.id.activity_upcoming_progressBar)
        recyclerView = findViewById(R.id.activity_upcoming_events_recycler_view)
        recyclerView.adapter = branchAdapter
    }

    private fun observeUpcomingEventsViewModel() {
        upcomingEventsViewModel.getProgressLiveData().observe(this, ::handleProgressBarState)
        upcomingEventsViewModel.getUpcomingEventsLiveData().observe(this,  ::showResult)
        upcomingEventsViewModel.getErrorLiveData().observe(this, ::showError)
    }

    private fun handleProgressBarState(
            progressState: ProgressState
    ) {
        progressBar.isVisible = progressState is ProgressState.Loading
    }

    private fun showError(errorMessage: Exception) {
        Toast.makeText(this, errorMessage.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    private fun showResult(upcomingEventsList: List<UpcomingEventsListItem>) {
        branchAdapter.setList(upcomingEventsList)
    }


    override fun onBranchClick(branchData: BranchApiData) {
        val allEventsIntent = Intent(this, AllEventsActivity::class.java)

        allEventsIntent.putExtra(BRANCH_ID, branchData.id)
        allEventsIntent.putExtra(BRANCH_TITLE, branchData.title)

        startActivity(allEventsIntent)
    }

    override fun onEventClick(eventData: EventApiData) {
        Toast.makeText(this, eventData.title, Toast.LENGTH_SHORT).show()
    }

    override fun onFavoritesClicked(eventData: EventApiData) {
        upcomingEventsViewModel.onFavoriteClick(eventData)
    }
}
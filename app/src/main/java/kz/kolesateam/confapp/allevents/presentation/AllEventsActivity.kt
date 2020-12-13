package kz.kolesateam.confapp.allevents.presentation

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
import kz.kolesateam.confapp.allevents.data.models.AllEventsListItem
import kz.kolesateam.confapp.allevents.di.ALL_EVENTS_VIEW_MODEL
import kz.kolesateam.confapp.models.ProgressState
import kz.kolesateam.confapp.allevents.presentation.view.AllEventsAdapter
import kz.kolesateam.confapp.events.presentation.BRANCH_ID
import kz.kolesateam.confapp.events.presentation.BRANCH_TITLE
import kz.kolesateam.confapp.events.domain.EventClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named


@Suppress("DEPRECATION")
class AllEventsActivity: AppCompatActivity(),
    EventClickListener {
    private val allEventsViewModel: AllEventsViewModel by viewModel(named(
            ALL_EVENTS_VIEW_MODEL))

    private lateinit var allEventsProgressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonToFavorites: Button

    private val adapter = AllEventsAdapter(this)
    private var branchId: Int = 0
    private var branchTitle: String = "Default Branch Name"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_events)
        bindViews()
        observeUpcomingEventsViewModel()
        allEventsViewModel.onStarted(branchId, branchTitle)
        setOnClickListeners()
    }

    private fun bindViews() {
        branchId = intent.getIntExtra(BRANCH_ID, 0)
        branchTitle = intent.getStringExtra(BRANCH_TITLE) ?: ""

        buttonToFavorites = findViewById(R.id.all_events_favorite_button)

        recyclerView = findViewById(R.id.activity_allEvents_recycler_view)

        recyclerView.adapter = adapter
        /*
        recyclerView.apply {
            this.adapter = this@AllEventsActivity.adapter
            this.layoutManager = LinearLayoutManager(this@AllEventsActivity)
        } */

        allEventsProgressBar = findViewById(R.id.activity_allEvents_progressBar)
    }

    private fun setOnClickListeners() {
        buttonToFavorites.setOnClickListener{
            Toast.makeText(this, "Нажата кнопка ${"В избранные"}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeUpcomingEventsViewModel() {
        allEventsViewModel.getProgressLiveData().observe(this, ::handleProgressBarState)
        allEventsViewModel.getAllEventsLiveData().observe(this,  ::showResult)
        allEventsViewModel.getErrorLiveData().observe(this, ::showError)
    }

    private fun handleProgressBarState(
        progressState: ProgressState
    ) {
        allEventsProgressBar.isVisible = progressState is ProgressState.Loading
    }

    private fun showError(errorMessage: Exception) {
        Toast.makeText(this, errorMessage.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    private fun showResult(upcomingEventsList: List<AllEventsListItem.EventListItem>) {
        adapter.setList(upcomingEventsList)
    }

    override fun onEventClickListener(view: View, eventTitle: String) {
        Toast.makeText(this, eventTitle, Toast.LENGTH_SHORT).show()
    }

    override fun onFavoriteClickListener(view: View) {
        TODO("Not yet implemented")
    }

    override fun onBranchClick(view: View, branchId: Int, branchTitle: String) {
    }
}
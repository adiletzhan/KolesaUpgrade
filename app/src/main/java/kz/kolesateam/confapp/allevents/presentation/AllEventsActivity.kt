package kz.kolesateam.confapp.allevents.presentation

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.di.ALL_EVENTS_VIEW_MODEL
import kz.kolesateam.confapp.events.data.models.ProgressState
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem
import kz.kolesateam.confapp.events.presentation.BRANCH_ID
import kz.kolesateam.confapp.events.presentation.BRANCH_TITLE
import kz.kolesateam.confapp.events.presentation.view.BranchAdapter
import kz.kolesateam.confapp.events.presentation.view.EventClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named


@Suppress("DEPRECATION")
class AllEventsActivity: AppCompatActivity(), EventClickListener {


    private val allEventsViewModel: AllEventsViewModel by viewModel(named(
        ALL_EVENTS_VIEW_MODEL))

    private lateinit var allEventsProgressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonToFavorites: Button

    private val adapter = BranchAdapter(this)
    private var branchId: Int = 0
    private var branchTitle: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_events)
        bindViews()
        observeUpcomingEventsViewModel()
        allEventsViewModel.onStart(branchId, branchTitle)
    }

    private fun bindViews() {
        branchId = intent.getIntExtra(BRANCH_ID, 0)
        branchTitle = intent.getStringExtra(BRANCH_TITLE) ?: ""

        allEventsProgressBar = findViewById(R.id.activity_allEvents_progressBar)

        recyclerView = findViewById(R.id.activity_allEvents_recycler_view)
        recyclerView.apply {
            this.adapter = this@AllEventsActivity.adapter
            this.layoutManager = LinearLayoutManager(this@AllEventsActivity)
        }

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        buttonToFavorites.setOnClickListener{
            Toast.makeText(this, "Нажата кнопка ${"В избранные"}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeUpcomingEventsViewModel() {
        allEventsViewModel.getProgressLiveData().observe(this, ::handleProgressBarState)
        allEventsViewModel.getAllEventsLiveData().observe(this, ::showResult)
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

    private fun showResult(upcomingEventsList: List<UpcomingEventsListItem>) {
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
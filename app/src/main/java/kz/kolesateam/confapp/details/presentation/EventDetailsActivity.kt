package kz.kolesateam.confapp.details.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.allevents.data.models.AllEventsListItem
import kz.kolesateam.confapp.details.EVENT_ID
import kz.kolesateam.confapp.details.di.EVENT_DETAILS_VIEW_MODEL
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.models.ProgressState
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

private const val  DEEPLINK_TITLE_KEY = "title"
private const val DEEPLINK_EVENT_KEY = "event_id"

class EventDetailsActivity: AppCompatActivity()
{

    private val eventDetailsViewModel: EventDetailsViewModel by viewModel(named(EVENT_DETAILS_VIEW_MODEL))

    private lateinit var speakerFullName: TextView
    private lateinit var speakerJob: TextView
    private lateinit var eventTimeAndPlace: TextView
    private lateinit var eventTitle: TextView
    private lateinit var eventDescription: TextView

    private lateinit var evenDetailsProgressBar: ProgressBar

    private var eventId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)
        initViews()
        eventDetailsViewModel.onStarted(eventId)
        setData()
        observeUpcomingEventsViewModel()
    }

    private fun initViews(){
        eventId = intent.getIntExtra(EVENT_ID, 0)

        speakerFullName = findViewById(R.id.activity_event_details_text_view_speaker_full_name)
        speakerJob = findViewById(R.id.activity_event_details_text_view_speaker_job)
        eventTimeAndPlace = findViewById(R.id.activity_event_details_text_view_time_and_place)
        eventTitle = findViewById(R.id.activity_event_details_text_view_event_title)
        eventDescription = findViewById(R.id.activity_event_details_text_view_event_description)

        evenDetailsProgressBar = findViewById(R.id.activity_event_details_progressBar)
    }

    private fun setData(){

    }

    private fun observeUpcomingEventsViewModel() {
        eventDetailsViewModel.getProgressLiveData().observe(this, ::handleProgressBarState)
        eventDetailsViewModel.getEventDetailsLiveData().observe(this,  ::showResult)
        eventDetailsViewModel.getErrorLiveData().observe(this, ::showError)
    }

    private fun handleProgressBarState(
            progressState: ProgressState
    ) {
        evenDetailsProgressBar.isVisible = progressState is ProgressState.Loading
    }

    private fun showError(errorMessage: Exception) {
        Toast.makeText(this, errorMessage.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    private fun showResult(eventData: EventApiData) {
        setViews(eventData)
    }

    private fun setViews(eventData: EventApiData){
        speakerFullName.text = eventData.speaker?.fullName
        speakerJob.text = eventData.speaker?.job
        eventTimeAndPlace.text = "%s - %s • %s".format(
                eventData.startTime,
                eventData.endTime,
                eventData.place
        )

        eventTitle.text = eventData.title
        eventDescription.text = eventData.description

    }

}
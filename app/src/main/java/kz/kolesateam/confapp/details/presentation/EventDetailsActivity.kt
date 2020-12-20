package kz.kolesateam.confapp.details.presentation

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.details.EVENT_TITLE_KEY

private const val  DEEPLINK_TITLE_KEY = "title"
private const val DEEPLINK_EVENT_KEY = "event_id"

class EventDetailsActivity: Activity(){

    private lateinit var speakerFullName: TextView
    private lateinit var speakerJob: TextView
    private lateinit var eventTimeAndPlace: TextView
    private lateinit var eventTitle: TextView
    private lateinit var eventDescription: TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)
        bindViews()
        setData()
    }

    private fun bindViews(){
        speakerFullName = findViewById(R.id.activity_event_details_text_view_speaker_full_name)
        speakerJob = findViewById(R.id.activity_event_details_text_view_speaker_job)
        eventTimeAndPlace = findViewById(R.id.activity_event_details_text_view_time_and_place)
        eventTitle = findViewById(R.id.activity_event_details_text_view_event_title)
        eventDescription = findViewById(R.id.activity_event_details_text_view_event_description)
    }

    private fun setData(){

    }
}
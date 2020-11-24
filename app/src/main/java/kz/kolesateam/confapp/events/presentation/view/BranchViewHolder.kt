package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData
import org.w3c.dom.Text

class BranchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val branchCurrentEvent: View = itemView.findViewById(R.id.branch_current_event)
    private val branchNextEvent: View = itemView.findViewById(R.id.branch_next_event)

    private val branchTitle: TextView = itemView.findViewById(R.id.branch_item_title)

    private val currentEventTimePlace: TextView = branchCurrentEvent.findViewById(R.id.event_card_time_place)
    private val currentSpeakerName: TextView = branchCurrentEvent.findViewById(R.id.event_card_speaker_name)
    private val currentSpeakerJob: TextView = branchCurrentEvent.findViewById(R.id.event_card_speaker_job)
    private val currentEventTitle: TextView = branchCurrentEvent.findViewById(R.id.event_card_speaker_speech_topic)

    private val nextEventTimePlace: TextView = branchNextEvent.findViewById(R.id.event_card_time_place)
    private val nextSpeakerName: TextView = branchNextEvent.findViewById(R.id.event_card_speaker_name)
    private val nextSpeakerJob: TextView = branchNextEvent.findViewById(R.id.event_card_speaker_job)
    private val nextEventTitle: TextView = branchNextEvent.findViewById(R.id.event_card_speaker_speech_topic)

    init {
        branchCurrentEvent.findViewById<TextView>(R.id.event_card_state_text_vew).visibility = View.GONE
    }

    fun onBind(branchApiData: BranchApiData){
        branchTitle.text = branchApiData.title

        val currentEvent: EventApiData = branchApiData.events.first()
        val nextEvent: EventApiData = branchApiData.events.component1()

        val currentEventTimePlaceText = "%s - %s • %s".format(
                currentEvent.startTime,
                currentEvent.endTime,
                currentEvent.place
        )

        currentEventTimePlace.text = currentEventTimePlaceText
        currentSpeakerName.text = currentEvent.speaker?.fullName ?: "No name"
        currentSpeakerJob.text = currentEvent.speaker?.job ?: "none"
        currentEventTitle.text = currentEvent.title ?: "None"

        val nextEventTimePlaceText = "%s - %s • %s".format(
                nextEvent.startTime,
                nextEvent.endTime,
                nextEvent.place
        )

        nextEventTimePlace.text = nextEventTimePlaceText
        nextSpeakerName.text = nextEvent.speaker?.fullName ?: "No name"
        nextSpeakerJob.text = nextEvent.speaker?.job ?: "none"
        nextEventTitle.text = nextEvent.title ?: "None"

    }
}
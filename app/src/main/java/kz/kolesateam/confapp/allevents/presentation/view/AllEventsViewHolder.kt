package kz.kolesateam.confapp.allevents.presentation.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.domain.EventClickListener

class AllEventsViewHolder(
    itemView: View,
    private val eventClickListener: EventClickListener
) : RecyclerView.ViewHolder(itemView){

    private val eventTimePlace: TextView = itemView.findViewById(R.id.event_card_time_place)
    private val eventSpeakerName: TextView = itemView.findViewById(R.id.event_card_speaker_name)
    private val eventSpeakerJob: TextView = itemView.findViewById(R.id.event_card_speaker_job)
    private val eventTitle: TextView = itemView.findViewById(R.id.event_card_speaker_speech_topic)

    private val eventFavoriteIcon: ImageView = itemView.findViewById(R.id.event_card_favorite_icon)

    fun onBind(
        eventApiData: EventApiData
    ){

        val currentEventTimePlaceText = "%s - %s • %s".format(
            eventApiData.startTime,
            eventApiData.endTime,
            eventApiData.place
        )

        eventTimePlace.text = currentEventTimePlaceText
        eventSpeakerName.text = eventApiData.speaker?.fullName ?: "No name"
        eventSpeakerJob.text = eventApiData.speaker?.job ?: "none"
        eventTitle.text = eventApiData.title ?: "None"

        eventFavoriteIcon.setOnClickListener{
            eventClickListener.onFavoriteClickListener(it)
        }
    }
}
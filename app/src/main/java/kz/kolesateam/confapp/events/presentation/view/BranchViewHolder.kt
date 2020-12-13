package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.domain.listeners.UpcomingEventsClickListener
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData

class BranchViewHolder(
        itemView: View,
        private val eventClickListener: UpcomingEventsClickListener
        ) : RecyclerView.ViewHolder(itemView) {

    private lateinit var currentEvent: EventApiData
    private lateinit var nextEvent: EventApiData

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

    private val branchHeader: View = itemView.findViewById(R.id.branch_item_header)
    private val currentEventFavoriteIcon: ImageView = branchCurrentEvent.findViewById(R.id.event_card_favorite_icon)
    private val nextEventFavoriteIcon: ImageView = branchNextEvent.findViewById(R.id.event_card_favorite_icon)

    init {
        //branchCurrentEvent.setBackgroundResource(R.drawable.bg_dark)
        branchCurrentEvent.findViewById<TextView>(R.id.event_card_state_text_vew).visibility = View.INVISIBLE

        currentEventFavoriteIcon.setOnClickListener {
            currentEvent.isFavorite =! currentEvent.isFavorite
            val favoriteIconResource =getFavoriteImageResource(currentEvent.isFavorite)
            currentEventFavoriteIcon.setImageResource(favoriteIconResource)

            eventClickListener.onFavoritesClicked(currentEvent)
        }

        nextEventFavoriteIcon.setOnClickListener {
            nextEvent.isFavorite =! nextEvent.isFavorite
            val favoriteIconResource =getFavoriteImageResource(nextEvent.isFavorite)
            nextEventFavoriteIcon.setImageResource(favoriteIconResource)

            eventClickListener.onFavoritesClicked(nextEvent)
        }

    }

    fun onBind(branchApiData: BranchApiData){

        branchTitle.text = branchApiData.title

        if (branchApiData.events.isEmpty()){
            branchCurrentEvent.visibility = View.GONE
            branchNextEvent.visibility = View.GONE

            return
        }

        currentEvent = branchApiData.events!!.first()
        nextEvent = branchApiData.events.last()

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
//review
        branchHeader.setOnClickListener{
                eventClickListener.onBranchClick(
                    branchApiData
                )
        }

        branchCurrentEvent.setOnClickListener{
            eventClickListener.onEventClick(
                currentEvent
            )
        }

        branchNextEvent.setOnClickListener{
            eventClickListener.onEventClick(
                    nextEvent
            )
        }

        /*
        currentEventFavoriteIcon.setOnClickListener{
            eventClickListener.onFavoriteClicked(it)
        }

        nextEventFavoriteIcon.setOnClickListener{
            eventClickListener.onFavoriteClickListener(it)
        }

         */
    }

    private fun getFavoriteImageResource(
            isFavorite: Boolean
    ): Int = when(isFavorite){
        true -> R.drawable.ic_favorite_filled
        else -> R.drawable.ic_favorite_border

    }
}
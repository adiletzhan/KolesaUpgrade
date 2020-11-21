package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import org.w3c.dom.Text

class BranchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val branchTitle: TextView = itemView.findViewById(R.id.branch_title)
    private val eventData: TextView = itemView.findViewById(R.id.event_time_place)
    private val speakerNameCurrent: TextView = itemView.findViewById(R.id.speaker_name)
    private val speaker


    fun onBind(userName: String){

    }
}
package kz.kolesateam.confapp.allevents.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem
import kz.kolesateam.confapp.events.presentation.view.BranchViewHolder
import kz.kolesateam.confapp.events.presentation.view.EventClickListener
import kz.kolesateam.confapp.events.presentation.view.HeaderViewHolder

class  AllEventsAdapter(private val eventClickListener: EventClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataList: MutableList<UpcomingEventsListItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            1 ->  HeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.header_layout, parent,false))
            else -> AllEventsViewHolder(View.inflate(parent.context, R.layout.activity_all_events, null), eventClickListener = eventClickListener)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}
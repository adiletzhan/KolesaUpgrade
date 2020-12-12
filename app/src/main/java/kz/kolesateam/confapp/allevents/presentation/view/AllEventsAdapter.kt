package kz.kolesateam.confapp.allevents.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.allevents.data.datasource.AllEventsListItem
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.domain.EventClickListener


class  AllEventsAdapter(private val eventClickListener: EventClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataList: MutableList<AllEventsListItem.EventListItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AllEventsViewHolder(View.inflate(parent.context, R.layout.events_card_layout, null), eventClickListener = eventClickListener)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is AllEventsViewHolder){
            holder.onBind(dataList[position].data as EventApiData)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setList(eventsApiDataList: List<AllEventsListItem.EventListItem>){
        dataList.clear()
        dataList.addAll(eventsApiDataList)
        notifyDataSetChanged()
    }
}
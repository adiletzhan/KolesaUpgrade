package kz.kolesateam.confapp.events.presentation.view

import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem

class BranchAdapter() : RecyclerView.Adapter<BranchViewHolder>() {
    private val dataList: MutableList<UpcomingEventsListItem<Any>> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        return when(viewType){
            1 -> HeaderViewHolder(View.inflate(parent.context, R.layout.header_layout, null))
            else -> BranchViewHolder(View.inflate(parent.context, R.layout.branch_item, null))
        }
    }

    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getItemViewType(position: Int): Int {
        return dataList[position].type
    }
}
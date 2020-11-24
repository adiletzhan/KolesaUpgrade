package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R

class HeaderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val userNameTextView = itemView.findViewById<TextView>(R.id.header_user_name)

    fun onBind(userName : String){
        userNameTextView.text = userName
    }

}
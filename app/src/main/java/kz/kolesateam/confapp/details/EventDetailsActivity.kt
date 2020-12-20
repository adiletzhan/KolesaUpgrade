package kz.kolesateam.confapp.details

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import kz.kolesateam.confapp.R

private const val  DEEPLINK_TITLE_KEY = "title"
private const val DEEPLINK_EVENT_KEY = "event_id"

class EventDetailsActivity: Activity(){

    private lateinit var infoTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)
        bindViews()
        setData()
    }

    private fun bindViews(){
        infoTextView = findViewById(R.id.activity_event_details_info_text_view)

    }

    private fun setData(){
        val title: String = intent?.getStringExtra(EVENT_TITLE_KEY) ?: run{
            intent?.data?.getQueryParameter(DEEPLINK_TITLE_KEY)
        } ?: "Title is not present"

        val eventId: String = intent?.data?.getQueryParameter(DEEPLINK_EVENT_KEY) ?: "0"

        infoTextView.text = "$title||Event id = $eventId"
    }
}
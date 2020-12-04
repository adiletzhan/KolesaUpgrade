package kz.kolesateam.confapp.events.presentation.view

import android.view.View

interface EventClickListener {
    fun onBranchClick(
        view: View,
        branchId: Int,
        branchTitle: String
    )

    fun onEventClickListener(
        view: View,
        eventTitle: String
    )

    fun onFavoriteClickListener(
        view: View
    )
}
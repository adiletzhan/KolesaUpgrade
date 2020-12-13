package kz.kolesateam.confapp.domain.listeners

import kz.kolesateam.confapp.events.data.models.BranchApiData

interface BranchClickListener {
    fun onBranchClick(
            branchData: BranchApiData,
    )
}
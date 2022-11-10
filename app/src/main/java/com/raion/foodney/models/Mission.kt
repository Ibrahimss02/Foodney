package com.raion.foodney.models

data class Mission(
    val id: String,
    val name: String,
    val warung: Warung,
    val coinReward: Int,
    var distance: Int,
)

object MissionDummy {
    val missionData = arrayListOf<Mission>(
        // TODO: INSERT DUMMY DATA HERE

    )
}
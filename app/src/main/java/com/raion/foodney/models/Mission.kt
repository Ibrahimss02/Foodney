package com.raion.foodney.models

import com.google.android.gms.maps.model.LatLng

data class Mission(
    val id: String,
    val name: String,
    val warung: Warung,
    val latLng: LatLng,
    val coinReward: Int,
    var distance: Int,
)

object MissionDummy {
    val missionData = arrayListOf<Mission>(
        // TODO: INSERT DUMMY DATA HERE

    )
}
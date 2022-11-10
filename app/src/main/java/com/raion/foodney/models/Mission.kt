package com.raion.foodney.models

import com.google.android.gms.maps.model.LatLng

data class Mission(
    val id: String,
    val name: String,
    val coinReward: Int,
    var distance: Int,
    val address: String,
    val rating: Float,
    val latLng: LatLng,
    val photoUrls: List<String>,
    val reviews: List<Review>
)

object MissionDummy {
    val missionData = arrayListOf<Mission>(
        // TODO: INSERT DUMMY DATA HERE

    )
}
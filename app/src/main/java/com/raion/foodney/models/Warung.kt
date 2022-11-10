package com.raion.foodney.models

import com.google.android.gms.maps.model.LatLng

data class Warung(
    val id: String,
    val name: String,
    val address: String,
    val rating: Float,
    val latLng: LatLng,
    val photoUrls: List<String>,
    val reviews: List<Review>
)

object WarungDummy{
    val warungData = arrayListOf<Warung>(
        // TODO: INSERT DUMMY DATA HERE

    )
}
package com.raion.foodney.models

data class Profile(
    val id: String,
    val name: String,
    val email: String,
    val coins: Int = 0,
    val level: Int = 0,
    val exp: Int = 0,
    var completedMission: List<Mission>? = null,
    var ownedCoupon: List<Coupon>? = null
)
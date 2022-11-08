package com.raion.foodney.models

data class Profile(
    val id: String,
    val name: String,
    val email: String,
    val coins: Int,
    val level: Int,
    val exp: Int,
    var completedMission: List<Mission>,
    var ownedCoupon: List<Coupon>
)
package com.raion.foodney.models

data class Profile(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    var coins: Int = 0,
    var level: Int = 1,
    var exp: Int = 0,
    var completedMission: List<Mission>? = null,
    var ownedCoupon: List<Coupon>? = null
)
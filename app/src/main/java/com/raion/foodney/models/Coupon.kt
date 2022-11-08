package com.raion.foodney.models

data class Coupon(
    val id: String,
    val name: String,
    val couponDesc: String,
    val cost: Int,
    val image: String
)

object CouponDummy {
    val couponData = arrayListOf<Coupon>(
        // TODO: INSERT DUMMY DATA HERE

    )
}
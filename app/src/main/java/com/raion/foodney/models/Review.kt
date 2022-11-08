package com.raion.foodney.models

data class Review(
    val id: String,
    val name: String,
    val review: String,
    val rating: Float,
)

object ReviewDummy{
    val reviewData = arrayListOf<Review>(
        // TODO: INSERT DUMMY DATA HERE
    )
}
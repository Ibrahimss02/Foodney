package com.raion.foodney.models

import com.raion.foodney.R

data class Review(
    val id: String,
    val name: String,
    val review: String,
    val rating: Float,
    val avatar: Int,
) {
    constructor() : this("", "", "", 0f, R.drawable.arnold)
}

object ReviewDummy{
    val reviewData = arrayListOf<Review>(
        // TODO: INSERT DUMMY DATA HERE
        Review(
            "review_01",
            "putin",
            "Makanannya enak, porsinya banyak, dan murah",
            5f,
            R.drawable.putin
        ),
        Review(
            "review_02",
            "arnold",
            "Harganya murah, rasanya sesuai harga",
            4f,
            R.drawable.arnold
        ),
        Review(
            "review_03",
            "renatta",
            "Makanannya enak banget, pelayanannya juga cepat. Ada banyak menu yang bisa coba",
            4.5f,
            R.drawable.renatta
        ),
        Review(
            "review_04",
            "quinn",
            "Ini ketiga kalinya saya datang ke tempat ini. Lokasinya strategis, banyak pilihan menu",
            5f,
            R.drawable.quinn
        ),
        Review(
            "review_05",
            "reynold",
            "Tempatnya lumayan nyaman, makanannya juga enak enak",
            4f,
            R.drawable.reynold
        ),
        Review(
            "review_06",
            "zelenskyy",
            "Rekomendasi banget dehh, harganya terjangkau dan enak",
            4.5f,
            R.drawable.zelenskyy
        )
    )
}

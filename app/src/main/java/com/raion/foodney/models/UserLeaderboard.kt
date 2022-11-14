package com.raion.foodney.models

import com.raion.foodney.R

data class UserLeaderboard(
    val name: String,
    val level: Int,
    val avatar: Int
)
object LeaderboardDummy {
    val leaderboardData = listOf<UserLeaderboard>(
        /* TODO : INSERT DUMMY HERE */
        UserLeaderboard(
            "arnold",
            50,
            R.drawable.arnold
        ),
        UserLeaderboard (
            "renatta",
            49,
            R.drawable.renatta
        ),
        UserLeaderboard (
            "quinn",
            48,
            R.drawable.quinn
        ),
        UserLeaderboard (
            "putin",
            47,
            R.drawable.putin
        ),
        UserLeaderboard (
            "reynold",
            46,
            R.drawable.reynold
        ),
        UserLeaderboard (
            "juna",
            45,
            R.drawable.juna
        ),
        UserLeaderboard (
            "zelenskyy",
            44,
            R.drawable.zelenskyy
        ),
        UserLeaderboard (
            "spongebob",
            43,
            R.drawable.spongebob
        ),
        UserLeaderboard (
            "remy",
            42,
            R.drawable.remy
        ),
        UserLeaderboard (
            "kimjongun",
            41,
            R.drawable.kimjongun
        )
    )
}

package com.raion.foodney.models

data class UserLeaderboard(
    val name: String,
    val level: Int,
    val avatar: Int
)
object LeaderboardDummy {
    val leaderboardData = listOf<LeaderboardDummy>(
        /* TODO : INSERT DUMMY HERE */
    )
}
package com.games.gamex.domain.model

data class Platform(
    val platformId: Int? = null,
    val platformName: String? = null,
    val gameId: Int? = null,
    val gameName: String? = null,
    val gameImage: String? = null,
    val gameReleased: String? = null,
    val gameRating: Float? = null,
)
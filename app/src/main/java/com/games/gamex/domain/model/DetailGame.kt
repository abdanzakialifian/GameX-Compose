package com.games.gamex.domain.model

data class DetailGame(
    val name: String? = null,
    val imageBackground: String? = null,
    val backgroundImageAdditional: String? = null,
    val genres: List<String>? = null,
    val rating: Double? = null,
    val description: String? = null,
    var images: List<Pair<Int, String>>? = null,
    val metacritic: Int? = null,
    val released: String? = null,
    val publishers: List<String>? = null,
)
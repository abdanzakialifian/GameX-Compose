package com.games.gamex.domain.model

import com.games.gamex.data.source.remote.response.StoresItemResponse

data class DetailGame(
    val name: String? = null,
    val imageBackground: String? = null,
    val backgroundImageAdditional: String? = null,
    val genres: List<ListResultItem>? = null,
    val rating: Double? = null,
    val description: String? = null,
)
package com.games.gamex.domain.model

import androidx.paging.PagingData

data class DetailGame(
    val name: String? = null,
    val imageBackground: String? = null,
    val backgroundImageAdditional: String? = null,
    val genres: List<ListResultItem>? = null,
    val rating: Double? = null,
    val description: String? = null,
    var images: List<Pair<Int, String>>? = null,
    var gameSeries: Pair<Int, List<ListResultItem>>? = null,
)
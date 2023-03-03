package com.games.gamex.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class PlatformsResponse(

    @field:SerializedName("next")
    val next: String? = null,

    @field:SerializedName("previous")
    val previous: Any? = null,

    @field:SerializedName("count")
    val count: Int? = null,

    @field:SerializedName("results")
    val results: List<PlatformsResultItemResponse>? = null
)

data class PlatformsResultItemResponse(

    @field:SerializedName("image")
    val image: Any? = null,

    @field:SerializedName("games_count")
    val gamesCount: Int? = null,

    @field:SerializedName("year_start")
    val yearStart: Any? = null,

    @field:SerializedName("year_end")
    val yearEnd: Any? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("games")
    val games: List<GamesItemResponse>? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("image_background")
    val imageBackground: String? = null,

    @field:SerializedName("slug")
    val slug: String? = null
)

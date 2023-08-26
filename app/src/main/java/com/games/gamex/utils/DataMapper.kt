package com.games.gamex.utils

import com.games.gamex.data.source.remote.response.DetailGameResponse
import com.games.gamex.data.source.remote.response.GamesResultItemResponse
import com.games.gamex.data.source.remote.response.GenresResultItemResponse
import com.games.gamex.data.source.remote.response.PlatformsResultItemResponse
import com.games.gamex.domain.model.DetailGame
import com.games.gamex.domain.model.ListResultItem

object DataMapper {
    fun GamesResultItemResponse.mapGamesResultItemResponseToListResultItem(): ListResultItem =
        ListResultItem(
            id = id,
            image = backgroundImage,
            name = name,
            released = released,
            rating = rating.toString().toFloat()
        )

    fun GenresResultItemResponse.mapGenresResultItemResponseToListResultItem(): ListResultItem =
        ListResultItem(id = id, name = name, image = imageBackground)

    fun PlatformsResultItemResponse.mapPlatformsResultItemResponseToListResultItem(): ListResultItem =
        ListResultItem(
            id = id,
            image = imageBackground,
            name = name,
            gamesCount = gamesCount
        )

    fun DetailGameResponse.mapDetailGameResponseToDetailGame(): DetailGame = DetailGame(
        name = name,
        imageBackground = backgroundImage,
        backgroundImageAdditional = backgroundImageAdditional,
        genres = genres?.map { genre ->
            ListResultItem(
                id = genre.id,
                name = genre.name,
            )
        },
        rating = rating,
        description = descriptionRaw,
        dominantColor = dominantColor,
        saturatedColor = saturatedColor
    )
}
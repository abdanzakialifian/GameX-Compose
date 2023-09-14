package com.games.gamex.utils

import com.games.gamex.data.source.remote.response.DetailGameResponse
import com.games.gamex.data.source.remote.response.GamesResultItemResponse
import com.games.gamex.data.source.remote.response.GenresResultItemResponse
import com.games.gamex.data.source.remote.response.PlatformsResultItemResponse
import com.games.gamex.domain.model.DetailGame
import com.games.gamex.domain.model.ListResultItem
import com.games.gamex.domain.model.Platform

object DataMapper {
    fun GamesResultItemResponse.mapGamesResultItemResponseToListResultItem(): ListResultItem {
        val platforms = platforms?.map { map ->
            Platform(
                platformId = map.platform?.id,
                platformName = map.platform?.name,
                gameId = id,
                gameName = name,
                gameImage = backgroundImage,
                gameReleased = released,
                gameRating = rating.toString().toFloat(),

                )
        }

        return ListResultItem(
            id = id,
            image = backgroundImage,
            name = name,
            released = released,
            rating = rating.toString().toFloat(),
            listPlatforms = platforms
        )
    }

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
            genre.name ?: ""
        },
        rating = rating,
        description = descriptionRaw,
        metacritic = metacritic,
        released = released,
        publishers = publishers?.map { publisher ->
            publisher.name ?: ""
        }
    )
}
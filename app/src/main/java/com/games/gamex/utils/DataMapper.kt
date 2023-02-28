package com.games.gamex.utils

import com.games.gamex.data.source.remote.response.GamesResultItemResponse
import com.games.gamex.data.source.remote.response.GenresResultItemResponse
import com.games.gamex.data.source.remote.response.PlatformsResultItemResponse
import com.games.gamex.domain.model.GamesResultItem
import com.games.gamex.domain.model.GenresResultItem
import com.games.gamex.domain.model.PlatformsResultItem

object DataMapper {
    fun mapGamesResultItemResponseToGamesResultItem(input: GamesResultItemResponse): GamesResultItem =
        GamesResultItem(
            id = input.id,
            image = input.backgroundImage,
            name = input.name,
            released = input.released
        )

    fun mapGenresResultItemResponseToGenresResultItem(input: GenresResultItemResponse): GenresResultItem =
        GenresResultItem(id = input.id, name = input.name, image = input.imageBackground)

    fun mapPlatformsResultItemResponseToPlatformsResultItem(input: PlatformsResultItemResponse): PlatformsResultItem =
        PlatformsResultItem(
            id = input.id,
            image = input.imageBackground,
            name = input.name,
            gamesCount = input.gamesCount
        )
}
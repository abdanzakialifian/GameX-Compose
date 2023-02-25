package com.games.gamex.utils

import com.games.gamex.data.source.remote.response.GamesResultItemResponse
import com.games.gamex.data.source.remote.response.GenresResultItemResponse
import com.games.gamex.domain.model.GamesResultItem
import com.games.gamex.domain.model.GenresResultItem

object DataMapper {
    fun mapGamesResultItemResponseToGamesResultItem(input: GamesResultItemResponse): GamesResultItem =
        GamesResultItem(id = input.id, image = input.backgroundImage, name = input.name)

    fun mapGenresResultItemResponseToGenresResultItem(input: GenresResultItemResponse): GenresResultItem =
        GenresResultItem(id = input.id, name = input.name)
}
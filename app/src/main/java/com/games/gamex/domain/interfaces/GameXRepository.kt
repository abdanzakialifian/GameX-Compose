package com.games.gamex.domain.interfaces

import androidx.paging.PagingData
import com.games.gamex.domain.model.GamesResultItem
import com.games.gamex.domain.model.GenresResultItem
import com.games.gamex.domain.model.PlatformsResultItem
import kotlinx.coroutines.flow.Flow

interface GameXRepository {
    fun getAllGames(): Flow<PagingData<GamesResultItem>>
    fun getAllGenres(): Flow<PagingData<GenresResultItem>>
    fun getAllPlatforms(): Flow<PagingData<PlatformsResultItem>>
}
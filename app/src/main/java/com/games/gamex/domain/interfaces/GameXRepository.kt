package com.games.gamex.domain.interfaces

import androidx.paging.PagingData
import com.games.gamex.domain.model.DetailGame
import com.games.gamex.domain.model.ListResultItem
import kotlinx.coroutines.flow.Flow

interface GameXRepository {
    fun getAllGames(querySearch: String, isPaging: Boolean): Flow<PagingData<ListResultItem>>
    fun getAllGenres(): Flow<PagingData<ListResultItem>>
    fun getAllPlatforms(): Flow<PagingData<ListResultItem>>
    fun getDetailGame(gameId: String): Flow<DetailGame>
    fun getScreenshotsGame(gameId: String): Flow<List<Pair<Int, String>>>
    fun getGameSeriesPaging(gameId: String, isPaging: Boolean): Flow<PagingData<ListResultItem>>
    fun getGamePlatforms(platformId: Int): Flow<PagingData<ListResultItem>>
}
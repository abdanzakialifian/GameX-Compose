package com.games.gamex.domain.interfaces

import androidx.paging.PagingData
import com.games.gamex.domain.model.DetailGame
import com.games.gamex.domain.model.ListResultItem
import com.games.gamex.utils.UiState
import kotlinx.coroutines.flow.Flow

interface GameXRepository {
    fun getAllGames(querySearch: String): Flow<PagingData<ListResultItem>>
    fun getAllGameGenres(): Flow<PagingData<ListResultItem>>
    fun getAllGamePlatforms(): Flow<PagingData<ListResultItem>>
    fun getDetailGame(id: String): Flow<UiState<DetailGame>>
    fun getScreenshotsGame(id: String): Flow<UiState<List<Pair<Int, String>>>>
    fun getGameSeriesPaging(gameId: String): Flow<PagingData<ListResultItem>>
    fun getGameSeries(gameId: String): Flow<UiState<List<ListResultItem>>>
}
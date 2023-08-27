package com.games.gamex.data.source.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.games.gamex.data.source.remote.paging.GameSeriesPagingSource
import com.games.gamex.data.source.remote.paging.GamesPagingSource
import com.games.gamex.data.source.remote.paging.GenresPagingSource
import com.games.gamex.data.source.remote.paging.PlatformsPagingSource
import com.games.gamex.data.source.remote.response.DetailGameResponse
import com.games.gamex.data.source.remote.response.GamesResponse
import com.games.gamex.data.source.remote.response.GamesResultItemResponse
import com.games.gamex.data.source.remote.response.GenresResultItemResponse
import com.games.gamex.data.source.remote.response.PlatformsResultItemResponse
import com.games.gamex.data.source.remote.response.ScreenshotsGameResponse
import com.games.gamex.data.source.remote.services.ApiService
import com.games.gamex.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val apiService: ApiService,
    private val gamesPagingSource: GamesPagingSource,
    private val genresPagingSource: GenresPagingSource,
    private val platformsPagingSource: PlatformsPagingSource,
    private val gameSeriesPagingSource: GameSeriesPagingSource,
) {
    fun getAllGames(querySearch: String): Flow<PagingData<GamesResultItemResponse>> =
        Pager(config = PagingConfig(
            pageSize = 10,
            initialLoadSize = 10,
        ), pagingSourceFactory = {
            gamesPagingSource.apply {
                setQuerySearch(querySearch)
            }
        }).flow.flowOn(Dispatchers.IO)

    fun getAllGenres(): Flow<PagingData<GenresResultItemResponse>> = Pager(config = PagingConfig(
        pageSize = 10, initialLoadSize = 10
    ), pagingSourceFactory = {
        genresPagingSource
    }).flow.flowOn(Dispatchers.IO)

    fun getAllPlatforms(): Flow<PagingData<PlatformsResultItemResponse>> =
        Pager(config = PagingConfig(
            pageSize = 10,
            initialLoadSize = 10,
        ), pagingSourceFactory = {
            platformsPagingSource
        }).flow.flowOn(Dispatchers.IO)

    fun getDetailGame(id: String): Flow<UiState<DetailGameResponse>> = flow {
        val response = apiService.getDetailGame(id)
        val responseBody = response.body()
        emit(UiState.Loading)
        if (response.isSuccessful && responseBody != null) {
            emit(UiState.Success(responseBody))
        } else {
            emit(UiState.Error(response.message()))
        }
    }.flowOn(Dispatchers.IO)

    fun getScreenshotsGame(id: String): Flow<UiState<ScreenshotsGameResponse>> = flow {
        val response = apiService.getScreenshotsGame(id)
        val responseBody = response.body()
        emit(UiState.Loading)
        if (response.isSuccessful && responseBody != null) {
            emit(UiState.Success(responseBody))
        } else {
            emit(UiState.Error(response.message()))
        }
    }.flowOn(Dispatchers.IO)

    fun getGameSeriesPaging(gameId: String): Flow<PagingData<GamesResultItemResponse>> =
        Pager(config = PagingConfig(
            pageSize = 10,
            initialLoadSize = 10,
        ), pagingSourceFactory = {
            gameSeriesPagingSource.apply {
                setGameId(gameId)
            }
        }).flow.flowOn(Dispatchers.IO)

    fun getGameSeries(gameId: String): Flow<UiState<GamesResponse>> = flow {
        val response = apiService.getGameSeries(gameId, 1, 6)
        val responseBody = response.body()
        emit(UiState.Loading)
        if (response.isSuccessful && responseBody != null) {
            emit(UiState.Success(responseBody))
        } else {
            emit(UiState.Error(response.message()))
        }
    }.flowOn(Dispatchers.IO)
}
package com.games.gamex.data.source.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.games.gamex.data.source.remote.paging.GamePlatformsPagingSource
import com.games.gamex.data.source.remote.paging.GameSeriesPagingSource
import com.games.gamex.data.source.remote.paging.GamesPagingSource
import com.games.gamex.data.source.remote.paging.GenresPagingSource
import com.games.gamex.data.source.remote.paging.PlatformsPagingSource
import com.games.gamex.data.source.remote.response.DetailGameResponse
import com.games.gamex.data.source.remote.response.GamesResultItemResponse
import com.games.gamex.data.source.remote.response.GenresResultItemResponse
import com.games.gamex.data.source.remote.response.PlatformsResultItemResponse
import com.games.gamex.data.source.remote.response.ScreenshotsGameResponse
import com.games.gamex.data.source.remote.services.ApiService
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
    private val gamePlatformsPagingSource: GamePlatformsPagingSource
) {
    fun getAllGames(
        querySearch: String,
        isPaging: Boolean
    ): Flow<PagingData<GamesResultItemResponse>> = Pager(config = PagingConfig(
        pageSize = 10,
        initialLoadSize = 10,
    ), pagingSourceFactory = {
        gamesPagingSource.apply {
            setDataGame(
                querySearch = querySearch,
                isPaging = isPaging,
            )
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

    fun getDetailGame(gameId: String): Flow<DetailGameResponse> = flow {
        val response = apiService.getDetailGame(gameId)
        val responseBody = response.body()
        responseBody?.let { detailGameResponse ->
            emit(detailGameResponse)
        }
    }.flowOn(Dispatchers.IO)

    fun getScreenshotsGame(gameId: String): Flow<ScreenshotsGameResponse> = flow {
        val response = apiService.getScreenshotsGame(gameId)
        val responseBody = response.body()
        responseBody?.let { screenshotsGameResponse ->
            emit(screenshotsGameResponse)
        }
    }.flowOn(Dispatchers.IO)

    fun getGameSeriesPaging(
        gameId: String, isPaging: Boolean
    ): Flow<PagingData<GamesResultItemResponse>> = Pager(config = PagingConfig(
        pageSize = 10,
        initialLoadSize = 10,
    ), pagingSourceFactory = {
        gameSeriesPagingSource.apply {
            setDataGameSeries(gameId, isPaging)
        }
    }).flow.flowOn(Dispatchers.IO)

    fun getGamePlatforms(platformId: Int): Flow<PagingData<GamesResultItemResponse>> =
        Pager(config = PagingConfig(
            pageSize = 10,
            initialLoadSize = 10,
        ), pagingSourceFactory = {
            gamePlatformsPagingSource.apply {
                setDataGame(platformId)
            }
        }).flow.flowOn(Dispatchers.IO)
}
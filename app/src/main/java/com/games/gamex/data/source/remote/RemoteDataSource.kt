package com.games.gamex.data.source.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.games.gamex.data.source.remote.paging.GamesPagingSource
import com.games.gamex.data.source.remote.paging.GenresPagingSource
import com.games.gamex.data.source.remote.paging.PlatformsPagingSource
import com.games.gamex.data.source.remote.response.GamesResultItemResponse
import com.games.gamex.data.source.remote.response.GenresResultItemResponse
import com.games.gamex.data.source.remote.response.PlatformsResultItemResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val gamesPagingSource: GamesPagingSource,
    private val genresPagingSource: GenresPagingSource,
    private val platformsPagingSource: PlatformsPagingSource
) {
    fun getAllGames(): Flow<PagingData<GamesResultItemResponse>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            initialLoadSize = 10,
        ),
        pagingSourceFactory = {
            gamesPagingSource
        }
    ).flow

    fun getAllGenres(): Flow<PagingData<GenresResultItemResponse>> = Pager(
        config = PagingConfig(
            pageSize = 6,
            initialLoadSize = 6
        ),
        pagingSourceFactory = {
            genresPagingSource
        }
    ).flow

    fun getAllPlatforms(): Flow<PagingData<PlatformsResultItemResponse>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            initialLoadSize = 10
        ),
        pagingSourceFactory = {
            platformsPagingSource
        }
    ).flow
}
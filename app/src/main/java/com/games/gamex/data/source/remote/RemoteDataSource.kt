package com.games.gamex.data.source.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.games.gamex.data.source.remote.paging.GamesPagingSource
import com.games.gamex.data.source.remote.response.GamesResultItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val gamesPagingSource: GamesPagingSource) {
    fun getAllGames(): Flow<PagingData<GamesResultItem>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            initialLoadSize = 10,
        ),
        pagingSourceFactory = {
            gamesPagingSource
        }
    ).flow
}
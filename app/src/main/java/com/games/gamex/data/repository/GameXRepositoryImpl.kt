package com.games.gamex.data.repository

import androidx.paging.PagingData
import com.games.gamex.data.source.remote.RemoteDataSource
import com.games.gamex.data.source.remote.response.GamesResultItem
import com.games.gamex.domain.interfaces.GameXRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameXRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    GameXRepository {
    override fun getAllGames(): Flow<PagingData<GamesResultItem>> = remoteDataSource.getAllGames()
}
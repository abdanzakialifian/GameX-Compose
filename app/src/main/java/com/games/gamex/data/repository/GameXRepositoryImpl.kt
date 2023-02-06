package com.games.gamex.data.repository

import com.games.gamex.data.source.remote.RemoteDataSource
import com.games.gamex.data.source.remote.response.GamesResponse
import com.games.gamex.domain.interfaces.GameXRepository
import com.games.gamex.utils.UiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameXRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    GameXRepository {
    override fun getGames(): Flow<UiState<GamesResponse>> = remoteDataSource.getGames()
}
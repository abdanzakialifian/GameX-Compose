package com.games.gamex.data.source.remote

import com.games.gamex.data.source.remote.response.GamesResponse
import com.games.gamex.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    fun getGames(): Flow<UiState<GamesResponse>> = flow {
        val response = apiService.getGames()
        val responseBody = response.body()
        emit(UiState.Loading)
        if (response.isSuccessful && responseBody != null) {
            emit(UiState.Success(responseBody))
        } else {
            emit(UiState.Error(response.errorBody().toString()))
        }
    }.flowOn(Dispatchers.IO)
}
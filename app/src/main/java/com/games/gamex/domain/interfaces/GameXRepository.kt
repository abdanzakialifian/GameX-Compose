package com.games.gamex.domain.interfaces

import com.games.gamex.data.source.remote.response.GamesResponse
import com.games.gamex.utils.UiState
import kotlinx.coroutines.flow.Flow

interface GameXRepository {
    fun getGames(): Flow<UiState<GamesResponse>>
}
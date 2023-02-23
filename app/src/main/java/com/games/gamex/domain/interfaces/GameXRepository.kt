package com.games.gamex.domain.interfaces

import androidx.paging.PagingData
import com.games.gamex.data.source.remote.response.GamesResultItem
import kotlinx.coroutines.flow.Flow

interface GameXRepository {
    fun getAllGames(): Flow<PagingData<GamesResultItem>>
}
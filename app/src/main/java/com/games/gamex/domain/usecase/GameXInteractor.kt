package com.games.gamex.domain.usecase

import androidx.paging.PagingData
import com.games.gamex.data.source.remote.response.GamesResultItem
import com.games.gamex.domain.interfaces.GameXRepository
import com.games.gamex.domain.interfaces.GameXUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameXInteractor @Inject constructor(private val gameXRepository: GameXRepository) :
    GameXUseCase {
    override fun getAllGames(): Flow<PagingData<GamesResultItem>> = gameXRepository.getAllGames()
}
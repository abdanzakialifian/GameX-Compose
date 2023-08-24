package com.games.gamex.domain.usecase

import com.games.gamex.domain.interfaces.GameXRepository
import com.games.gamex.domain.model.DetailGame
import com.games.gamex.utils.UiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetDetailGame @Inject constructor(private val gameXRepository: GameXRepository) {
    operator fun invoke(gameId: String): Flow<UiState<DetailGame>> =
        gameXRepository.getDetailGame(gameId)
}
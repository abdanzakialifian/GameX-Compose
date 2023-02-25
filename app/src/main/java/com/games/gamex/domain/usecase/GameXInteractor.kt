package com.games.gamex.domain.usecase

import androidx.paging.PagingData
import com.games.gamex.domain.interfaces.GameXRepository
import com.games.gamex.domain.interfaces.GameXUseCase
import com.games.gamex.domain.model.GamesResultItem
import com.games.gamex.domain.model.GenresResultItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameXInteractor @Inject constructor(private val gameXRepository: GameXRepository) :
    GameXUseCase {
    override fun getAllGames(): Flow<PagingData<GamesResultItem>> = gameXRepository.getAllGames()
    override fun getAllGenres(): Flow<PagingData<GenresResultItem>> = gameXRepository.getAllGenres()
}
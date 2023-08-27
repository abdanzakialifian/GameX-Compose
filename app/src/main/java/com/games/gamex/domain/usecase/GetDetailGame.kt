package com.games.gamex.domain.usecase

import com.games.gamex.domain.interfaces.GameXRepository
import com.games.gamex.domain.model.DetailGame
import com.games.gamex.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetDetailGame @Inject constructor(private val gameXRepository: GameXRepository) {
    operator fun invoke(gameId: String): Flow<UiState<DetailGame>> = flow {
        emit(UiState.Loading)
        gameXRepository.getDetailGame(gameId)
            .zip(gameXRepository.getScreenshotsGame(gameId)) { detail, images ->
                detail.images = images.filter { data -> data.second != "" }
                detail
            }.zip(gameXRepository.getGameSeries(gameId)) { detail, series ->
                val gamesSeriesCount = series.first
                val listGameSeries = series.second.filter { data -> data.image != "" }
                detail.gameSeries = Pair(gamesSeriesCount, listGameSeries)
                detail
            }
            .flowOn(Dispatchers.IO)
            .catch { throwable ->
                emit(UiState.Error(throwable.message ?: ""))
            }.collect { gameDetail ->
                emit(UiState.Success(gameDetail))
            }
    }
}
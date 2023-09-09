package com.games.gamex.domain.usecase

import androidx.paging.PagingData
import com.games.gamex.domain.interfaces.GameXRepository
import com.games.gamex.domain.model.DetailGame
import com.games.gamex.domain.model.ListResultItem
import com.games.gamex.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetDetailGame @Inject constructor(private val gameXRepository: GameXRepository) {
    operator fun invoke(
        gameId: String, isPaging: Boolean
    ): Flow<Pair<UiState<DetailGame>, StateFlow<PagingData<ListResultItem>>>> = flow {
        emit(Pair(UiState.Loading, MutableStateFlow(PagingData.empty())))
        gameXRepository.getDetailGame(gameId)
            .zip(gameXRepository.getScreenshotsGame(gameId)) { detail, images ->
                detail.images = images.filter { data -> data.second != "" }
                detail
            }.zip(
                gameXRepository.getGameSeriesPaging(
                    gameId,
                    isPaging
                )
            ) { detail, pagingDataGameSeries ->
                Pair(detail, pagingDataGameSeries)
            }.flowOn(Dispatchers.IO).catch { throwable ->
                emit(
                    Pair(
                        UiState.Error(throwable.message ?: ""), MutableStateFlow(PagingData.empty())
                    )
                )
            }.collect { gameDetail ->
                val detailGame = gameDetail.first
                val pagingDataGameSeries = gameDetail.second
                emit(Pair(UiState.Success(detailGame), MutableStateFlow(pagingDataGameSeries)))
            }
    }
}
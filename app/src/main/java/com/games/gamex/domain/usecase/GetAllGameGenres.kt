package com.games.gamex.domain.usecase

import androidx.paging.PagingData
import com.games.gamex.domain.interfaces.GameXRepository
import com.games.gamex.domain.model.ListResultItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAllGameGenres @Inject constructor(private val gameXRepository: GameXRepository) {
    operator fun invoke(genreId: Int): Flow<PagingData<ListResultItem>> =
        gameXRepository.getGameGenres(genreId)
}
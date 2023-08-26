package com.games.gamex.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.games.gamex.data.source.remote.RemoteDataSource
import com.games.gamex.domain.interfaces.GameXRepository
import com.games.gamex.domain.model.DetailGame
import com.games.gamex.domain.model.ListResultItem
import com.games.gamex.utils.DataMapper
import com.games.gamex.utils.DataMapper.mapDetailGameResponseToDetailGame
import com.games.gamex.utils.DataMapper.mapGamesResultItemResponseToListResultItem
import com.games.gamex.utils.DataMapper.mapGenresResultItemResponseToListResultItem
import com.games.gamex.utils.DataMapper.mapPlatformsResultItemResponseToListResultItem
import com.games.gamex.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameXRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    GameXRepository {
    override fun getAllGames(querySearch: String): Flow<PagingData<ListResultItem>> =
        remoteDataSource.getAllGames(querySearch).map { pagingData ->
            pagingData.map { map ->
                map.mapGamesResultItemResponseToListResultItem()
            }
        }

    override fun getAllGameGenres(): Flow<PagingData<ListResultItem>> =
        remoteDataSource.getAllGenres().map { pagingData ->
            pagingData.map { map ->
                map.mapGenresResultItemResponseToListResultItem()
            }
        }

    override fun getAllGamePlatforms(): Flow<PagingData<ListResultItem>> =
        remoteDataSource.getAllPlatforms().map { pagingData ->
            pagingData.map { map ->
                map.mapPlatformsResultItemResponseToListResultItem()
            }
        }

    override fun getDetailGame(id: String): Flow<UiState<DetailGame>> =
        remoteDataSource.getDetailGame(id).map { uiState ->
            when (uiState) {
                is UiState.Loading -> UiState.Loading
                is UiState.Success -> {
                    val mapper = uiState.data.mapDetailGameResponseToDetailGame()
                    UiState.Success(mapper)
                }
                is UiState.Error -> {
                    val error = uiState.message
                    UiState.Error(error)
                }
            }
        }
}
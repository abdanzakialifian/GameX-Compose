package com.games.gamex.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.games.gamex.data.source.remote.RemoteDataSource
import com.games.gamex.domain.interfaces.GameXRepository
import com.games.gamex.domain.model.DetailGame
import com.games.gamex.domain.model.GamesResultItem
import com.games.gamex.domain.model.GenresResultItem
import com.games.gamex.domain.model.PlatformsResultItem
import com.games.gamex.utils.DataMapper
import com.games.gamex.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameXRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    GameXRepository {
    override fun getAllGames(querySearch: String): Flow<PagingData<GamesResultItem>> =
        remoteDataSource.getAllGames(querySearch).map { pagingData ->
            pagingData.map { map ->
                DataMapper.mapGamesResultItemResponseToGamesResultItem(map)
            }
        }

    override fun getAllGenres(): Flow<PagingData<GenresResultItem>> =
        remoteDataSource.getAllGenres().map { pagingData ->
            pagingData.map { map ->
                DataMapper.mapGenresResultItemResponseToGenresResultItem(map)
            }
        }

    override fun getAllPlatforms(): Flow<PagingData<PlatformsResultItem>> =
        remoteDataSource.getAllPlatforms().map { pagingData ->
            pagingData.map { map ->
                DataMapper.mapPlatformsResultItemResponseToPlatformsResultItem(map)
            }
        }

    override fun getDetailGame(id: String): Flow<UiState<DetailGame>> =
        remoteDataSource.getDetailGame(id).map { uiState ->
            when (uiState) {
                is UiState.Loading -> UiState.Loading
                is UiState.Success -> {
                    val mapper = DataMapper.mapDetailGameResponseToDetailGame(uiState.data)
                    UiState.Success(mapper)
                }
                is UiState.Error -> {
                    val error = uiState.message
                    UiState.Error(error)
                }
            }
        }
}
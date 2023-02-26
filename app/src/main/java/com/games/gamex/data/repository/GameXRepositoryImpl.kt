package com.games.gamex.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.games.gamex.data.source.remote.RemoteDataSource
import com.games.gamex.domain.interfaces.GameXRepository
import com.games.gamex.domain.model.GamesResultItem
import com.games.gamex.domain.model.GenresResultItem
import com.games.gamex.domain.model.PlatformsResultItem
import com.games.gamex.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameXRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    GameXRepository {
    override fun getAllGames(): Flow<PagingData<GamesResultItem>> =
        remoteDataSource.getAllGames().map { pagingData ->
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
}
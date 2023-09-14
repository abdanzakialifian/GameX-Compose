package com.games.gamex.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.games.gamex.data.source.remote.RemoteDataSource
import com.games.gamex.domain.interfaces.GameXRepository
import com.games.gamex.domain.model.DetailGame
import com.games.gamex.domain.model.ListResultItem
import com.games.gamex.utils.DataMapper.mapDetailGameResponseToDetailGame
import com.games.gamex.utils.DataMapper.mapGamesResultItemResponseToListResultItem
import com.games.gamex.utils.DataMapper.mapGenresResultItemResponseToListResultItem
import com.games.gamex.utils.DataMapper.mapPlatformsResultItemResponseToListResultItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameXRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    GameXRepository {
    override fun getAllGames(
        querySearch: String,
        isPaging: Boolean,
    ): Flow<PagingData<ListResultItem>> =
        remoteDataSource.getAllGames(
            querySearch = querySearch,
            isPaging = isPaging,
        )
            .map { pagingData ->
                pagingData.map { map ->
                    map.mapGamesResultItemResponseToListResultItem()
                }
            }

    override fun getAllGenres(): Flow<PagingData<ListResultItem>> =
        remoteDataSource.getAllGenres().map { pagingData ->
            pagingData.map { map ->
                map.mapGenresResultItemResponseToListResultItem()
            }
        }

    override fun getAllPlatforms(): Flow<PagingData<ListResultItem>> =
        remoteDataSource.getAllPlatforms().map { pagingData ->
            pagingData.map { map ->
                map.mapPlatformsResultItemResponseToListResultItem()
            }
        }

    override fun getDetailGame(gameId: String): Flow<DetailGame> =
        remoteDataSource.getDetailGame(gameId).map { detailGameResponse ->
            detailGameResponse.mapDetailGameResponseToDetailGame()
        }

    override fun getScreenshotsGame(gameId: String): Flow<List<Pair<Int, String>>> =
        remoteDataSource.getScreenshotsGame(gameId).map { screenshotsGameResponse ->
            val results = screenshotsGameResponse.results ?: listOf()
            results.map { screenshotsResultItemResponse ->
                Pair(
                    screenshotsResultItemResponse.id ?: 0,
                    screenshotsResultItemResponse.image ?: ""
                )
            }
        }

    override fun getGameSeriesPaging(
        gameId: String, isPaging: Boolean
    ): Flow<PagingData<ListResultItem>> =
        remoteDataSource.getGameSeriesPaging(gameId, isPaging).map { pagingData ->
            pagingData.map { map ->
                map.mapGamesResultItemResponseToListResultItem()
            }
        }

    override fun getGamePlatforms(platformId: Int): Flow<PagingData<ListResultItem>> =
        remoteDataSource.getGamePlatforms(platformId)
            .map { pagingData ->
                pagingData.map { map ->
                    map.mapGamesResultItemResponseToListResultItem()
                }
            }
}
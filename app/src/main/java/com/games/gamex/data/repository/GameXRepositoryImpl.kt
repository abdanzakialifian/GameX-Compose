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

    override fun getDetailGame(id: String): Flow<DetailGame> =
        remoteDataSource.getDetailGame(id).map { detailGameResponse ->
            detailGameResponse.mapDetailGameResponseToDetailGame()
        }

    override fun getScreenshotsGame(id: String): Flow<List<Pair<Int, String>>> =
        remoteDataSource.getScreenshotsGame(id).map { screenshotsGameResponse ->
            val results = screenshotsGameResponse.results ?: listOf()
            results.map { screenshotsResultItemResponse ->
                Pair(
                    screenshotsResultItemResponse.id ?: 0,
                    screenshotsResultItemResponse.image ?: ""
                )
            }
        }

    override fun getGameSeries(gameId: String): Flow<Pair<Int, List<ListResultItem>>> =
        remoteDataSource.getGameSeries(gameId).map { gamesResponse ->
            val results = gamesResponse.results ?: listOf()
            val mapper = results.map { gamesResultItemResponse ->
                gamesResultItemResponse.mapGamesResultItemResponseToListResultItem()
            }
            val gamesCount = gamesResponse.count ?: 0
            Pair(gamesCount, mapper)
        }

    override fun getGameSeriesPaging(gameId: String): Flow<PagingData<ListResultItem>> =
        remoteDataSource.getGameSeriesPaging(gameId).map { pagingData ->
            pagingData.map { map ->
                map.mapGamesResultItemResponseToListResultItem()
            }
        }
}
package com.games.gamex.data.source.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.games.gamex.data.source.remote.response.GamesResultItemResponse
import com.games.gamex.data.source.remote.services.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameSeriesPagingSource @Inject constructor(private val apiService: ApiService) :
    PagingSource<Int, GamesResultItemResponse>() {

    private var gameId = ""
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GamesResultItemResponse> {
        val position = params.key ?: INITIAL_POSITION

//        return try {
//            val response = apiService.getGameSeries(position, params.loadSize, gameId)
//            val responseBody = response.body()?.results ?: listOf()
//            val next = response.body()?.next
//
//            val nextKey = if (next?.isEmpty() == true || next == null) {
//                null
//            } else {
//                position + INITIAL_POSITION
//            }
//
//            val prevKey = if (position == INITIAL_POSITION) null else position
//
//            LoadResult.Page(
//                data = responseBody,
//                prevKey = prevKey,
//                nextKey = nextKey
//            )
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
        return LoadResult.Page(
            data = listOf(GamesResultItemResponse()),
            prevKey = 0,
            nextKey = 0
        )
    }

    override fun getRefreshKey(state: PagingState<Int, GamesResultItemResponse>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    fun setGameId(gameId: String) {
        this.gameId = gameId
    }

    companion object {
        private const val INITIAL_POSITION = 1
    }
}
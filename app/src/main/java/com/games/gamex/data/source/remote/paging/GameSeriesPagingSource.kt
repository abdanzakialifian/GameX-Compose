package com.games.gamex.data.source.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.games.gamex.data.source.remote.response.GamesResultItemResponse
import com.games.gamex.data.source.remote.services.ApiService
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameSeriesPagingSource @Inject constructor(private val apiService: ApiService) :
    PagingSource<Int, GamesResultItemResponse>() {

    private var gameId = ""
    private var isPaging = false

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GamesResultItemResponse> {
        val position = params.key ?: INITIAL_POSITION

        return try {
            val page = if (isPaging) position else INITIAL_POSITION
            val response = apiService.getGameSeries(gameId, page, params.loadSize)
            val responseBody = response.body()?.results ?: listOf()
            val next = response.body()?.next

            val nextKey = if (next?.isEmpty() == true || next == null) {
                null
            } else {
                position + INITIAL_POSITION
            }

            val prevKey = if (position == INITIAL_POSITION) null else position

            LoadResult.Page(
                data = responseBody,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GamesResultItemResponse>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    fun setDataGameSeries(gameId: String, isPaging: Boolean) {
        this.gameId = gameId
        this.isPaging = isPaging
    }

    companion object {
        private const val INITIAL_POSITION = 1
    }
}
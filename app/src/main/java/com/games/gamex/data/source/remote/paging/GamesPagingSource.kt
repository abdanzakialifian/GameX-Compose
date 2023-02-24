package com.games.gamex.data.source.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.games.gamex.data.source.remote.response.GamesResultItem
import com.games.gamex.data.source.remote.services.ApiService
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GamesPagingSource @Inject constructor(private val apiService: ApiService) :
    PagingSource<Int, GamesResultItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GamesResultItem> {
        val position = params.key ?: INITIAL_POSITION

        return try {
            val response = apiService.getGames(position, params.loadSize)
            delay(3000L)
            val responseBody = response.body()?.results
            LoadResult.Page(
                data = responseBody ?: listOf(),
                prevKey = if (position == 1) null else position,
                nextKey = if (responseBody?.isEmpty() == true) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GamesResultItem>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    companion object {
        private const val INITIAL_POSITION = 1
    }
}
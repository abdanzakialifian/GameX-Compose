package com.games.gamex.data.source.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.games.gamex.data.source.remote.response.GamesResultItemResponse
import com.games.gamex.data.source.remote.services.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GamesPagingSource @Inject constructor(private val apiService: ApiService) :
    PagingSource<Int, GamesResultItemResponse>() {

    private var querySearch = ""

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GamesResultItemResponse> {
        val position = params.key ?: INITIAL_POSITION

        return try {
            val response = apiService.getGames(position, params.loadSize, querySearch)
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

    fun setQuerySearch(querySearch: String) {
        this.querySearch = querySearch
    }

    companion object {
        private const val INITIAL_POSITION = 1
    }
}
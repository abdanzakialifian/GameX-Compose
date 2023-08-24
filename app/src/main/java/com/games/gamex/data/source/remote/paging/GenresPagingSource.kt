package com.games.gamex.data.source.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.games.gamex.data.source.remote.response.GenresResultItemResponse
import com.games.gamex.data.source.remote.services.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GenresPagingSource @Inject constructor(private val apiService: ApiService) :
    PagingSource<Int, GenresResultItemResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GenresResultItemResponse> {
        val position = params.key ?: INITIAL_POSITION

        return try {
            val response = apiService.getGenres(position, params.loadSize)
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

    override fun getRefreshKey(state: PagingState<Int, GenresResultItemResponse>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    companion object {
        private const val INITIAL_POSITION = 1
    }
}
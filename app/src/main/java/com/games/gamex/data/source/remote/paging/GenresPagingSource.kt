package com.games.gamex.data.source.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.games.gamex.data.source.remote.response.GenresResultItemResponse
import com.games.gamex.data.source.remote.services.ApiService
import kotlinx.coroutines.delay
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GenresPagingSource @Inject constructor(private val apiService: ApiService) :
    PagingSource<Int, GenresResultItemResponse>() {

    private var totalItem = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GenresResultItemResponse> {
        val position = params.key ?: INITIAL_POSITION

        return try {
            val response = apiService.getGenres(position, params.loadSize)
            delay(500L)
            val responseBody = response.body()?.results
            totalItem += responseBody?.size ?: 0

            val nextKey = if (totalItem >= (response.body()?.count ?: 0)) {
                null
            } else {
                position + INITIAL_POSITION
            }

            LoadResult.Page(
                data = responseBody ?: listOf(),
                prevKey = if (position == INITIAL_POSITION) null else position,
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
package com.games.gamex.data.source.remote.services

import com.games.gamex.data.source.remote.response.DetailGameResponse
import com.games.gamex.data.source.remote.response.GamesResponse
import com.games.gamex.data.source.remote.response.GenresResponse
import com.games.gamex.data.source.remote.response.PlatformsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("games")
    suspend fun getGames(
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
        @Query("search") search: String
    ): Response<GamesResponse>

    @GET("genres")
    suspend fun getGenres(
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int
    ): Response<GenresResponse>

    @GET("platforms")
    suspend fun getPlatforms(
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int
    ): Response<PlatformsResponse>

    @GET("games/{id}")
    suspend fun getDetailGame(
        @Path("id") id: String
    ): Response<DetailGameResponse>
}
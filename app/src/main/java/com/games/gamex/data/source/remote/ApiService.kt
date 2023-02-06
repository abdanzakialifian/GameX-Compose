package com.games.gamex.data.source.remote

import com.games.gamex.data.source.remote.response.GamesResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("games")
    suspend fun getGames(): Response<GamesResponse>
}
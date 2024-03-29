package com.games.gamex.domain.usecase.gameslist

import com.games.gamex.domain.usecase.GetAllGameGenres
import com.games.gamex.domain.usecase.GetAllGamePlatforms
import com.games.gamex.domain.usecase.GetAllGames
import com.games.gamex.domain.usecase.GetAllGamesSeries
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
data class GamesListWrapper @Inject constructor(
    val getAllGamesSeries: GetAllGamesSeries,
    val getAllGames: GetAllGames,
    val getAllGamePlatforms: GetAllGamePlatforms,
    val getAllGameGenres: GetAllGameGenres
)
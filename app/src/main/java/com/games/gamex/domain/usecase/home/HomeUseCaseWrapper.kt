package com.games.gamex.domain.usecase.home

import com.games.gamex.domain.usecase.GetAllGameGenres
import com.games.gamex.domain.usecase.GetAllGamePlatforms
import com.games.gamex.domain.usecase.GetAllGames
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
data class HomeUseCaseWrapper @Inject constructor(
    val getAllGames: GetAllGames,
    val getAllGameGenres: GetAllGameGenres,
    val getAllGamePlatforms: GetAllGamePlatforms
)

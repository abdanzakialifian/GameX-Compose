package com.games.gamex.domain.usecase.home

import com.games.gamex.domain.usecase.GetAllGenres
import com.games.gamex.domain.usecase.GetAllPlatforms
import com.games.gamex.domain.usecase.GetAllGames
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
data class HomeUseCaseWrapper @Inject constructor(
    val getAllGames: GetAllGames,
    val getAllGenres: GetAllGenres,
    val getAllPlatforms: GetAllPlatforms
)

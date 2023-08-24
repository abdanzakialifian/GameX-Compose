package com.games.gamex.presentation.navigation

import com.games.gamex.utils.GAME_ID

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object HomeScreen : Screen("home_screen")
    object DetailScreen : Screen("detail_screen/{$GAME_ID}") {
        fun createRoute(gameId: String): String = "detail_screen/$gameId"
    }
}

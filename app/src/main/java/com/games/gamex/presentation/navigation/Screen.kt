package com.games.gamex.presentation.navigation

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object HomeScreen : Screen("home_screen")
    object DetailScreen : Screen("detail_screen/{gameId}") {
        fun createRoute(gameId: String): String = "detail_screen/$gameId"
    }
}

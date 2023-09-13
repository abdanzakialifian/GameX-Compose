package com.games.gamex.presentation.navigation

import com.games.gamex.utils.GAME_ID
import com.games.gamex.utils.NAVIGATE_FROM

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object HomeScreen : Screen("home_screen")
    object DetailScreen : Screen("detail_screen/{$GAME_ID}") {
        fun createRoute(gameId: String): String = "detail_screen/$gameId"
    }

    object GamesListScreen : Screen("games_list_screen/{$NAVIGATE_FROM}?$GAME_ID={$GAME_ID}") {
        fun createRoute( navigateFrom: String, gameId: String = ""): String =
            "games_list_screen/$navigateFrom?$GAME_ID=$gameId"
    }
}

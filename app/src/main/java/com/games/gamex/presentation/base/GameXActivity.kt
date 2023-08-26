package com.games.gamex.presentation.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.games.gamex.presentation.detail.view.DetailScreen
import com.games.gamex.presentation.home.view.HomeScreen
import com.games.gamex.presentation.navigation.Screen
import com.games.gamex.presentation.splash.SplashScreen
import com.games.gamex.presentation.ui.theme.GameXTheme
import com.games.gamex.utils.GAME_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameXActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameXApp()
        }
    }
}

@Composable
fun GameXApp() {
    GameXTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background,
        ) {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = Screen.SplashScreen.route,
            ) {
                composable(route = Screen.SplashScreen.route) {
                    SplashScreen(onNavigate = {
                        navController.navigate(Screen.HomeScreen.route) {
                            popUpTo(
                                Screen.SplashScreen.route
                            ) {
                                inclusive = true
                            }
                        }
                    })
                }
                composable(route = Screen.HomeScreen.route) {
                    HomeScreen(
                        onGenreClicked = { navController.navigate(Screen.DetailScreen.route) },
                        onGameHorizontalClicked = { gameId ->
                            navController.navigate(
                                Screen.DetailScreen.createRoute(
                                    gameId.toString()
                                )
                            )
                        },
                        onPlatformClicked = { navController.navigate(Screen.DetailScreen.route) },
                        onGameVerticalClicked = { navController.navigate(Screen.DetailScreen.route) },
                    )
                }
                composable(
                    route = Screen.DetailScreen.route,
                    arguments = listOf(
                        navArgument(GAME_ID) {
                            type = NavType.StringType
                        },
                    ),
                ) { backStackEntry ->
                    val gameId = backStackEntry.arguments?.getString(GAME_ID)
                    DetailScreen(gameId = gameId ?: "")
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4_XL)
@Composable
fun GameXPreview() {
    GameXApp()
}
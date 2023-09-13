package com.games.gamex.presentation.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.games.gamex.presentation.detail.view.DetailScreen
import com.games.gamex.presentation.gameslist.view.GamesListScreen
import com.games.gamex.presentation.home.view.HomeScreen
import com.games.gamex.presentation.navigation.Screen
import com.games.gamex.presentation.splash.SplashScreen
import com.games.gamex.presentation.ui.theme.GameXTheme
import com.games.gamex.presentation.ui.theme.Purple
import com.games.gamex.utils.FROM_GAMES
import com.games.gamex.utils.FROM_SIMILAR_GAMES
import com.games.gamex.utils.GAME_ID
import com.games.gamex.utils.NAVIGATE_FROM
import com.games.gamex.utils.VIBRANT
import com.games.gamex.utils.fromHex
import com.google.accompanist.systemuicontroller.rememberSystemUiController
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
            val systemUiController = rememberSystemUiController()
            val currentRoute =
                navController.currentBackStackEntryAsState().value?.destination?.route
            val colorStringPurple = "#5F67EA"

            var vibrant by remember {
                mutableStateOf(colorStringPurple)
            }

            if (currentRoute == Screen.HomeScreen.route || currentRoute == Screen.SplashScreen.route) {
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = Purple
                    )
                    // reset color vibrant outside detail screen
                    vibrant = colorStringPurple
                }
            } else {
                LaunchedEffect(key1 = vibrant) {
                    systemUiController.setStatusBarColor(
                        color = vibrant.fromHex()
                    )
                }
            }

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
                        onGameVerticalClicked = { gameId ->
                            navController.navigate(
                                Screen.DetailScreen.createRoute(
                                    gameId.toString()
                                )
                            )
                        },
                        onSeeAllGamesClicked = {
                            navController.navigate(
                                Screen.GamesListScreen.createRoute(FROM_GAMES)
                            )
                        }
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
                    AnimatedVisibility(visible = true) {
                        DetailScreen(gameId = gameId ?: "", onColorPalette = { colors ->
                            vibrant = colors[VIBRANT] ?: colorStringPurple
                        }, onImageBackClicked = {
                            navController.navigateUp()
                        }, onSeeAllClicked = { gameId ->
                            navController.navigate(
                                Screen.GamesListScreen.createRoute(
                                    FROM_SIMILAR_GAMES,
                                    gameId
                                )
                            )
                        }, onSimilarGameClicked = { gameId ->
                            navController.navigate(
                                Screen.DetailScreen.createRoute(
                                    gameId.toString()
                                )
                            ) {
                                popUpTo(Screen.HomeScreen.route) {
                                    inclusive = false
                                }
                            }
                        })
                    }
                }
                composable(
                    route = Screen.GamesListScreen.route,
                    arguments = listOf(
                        navArgument(GAME_ID) {
                            type = NavType.StringType
                        }, navArgument(NAVIGATE_FROM) {
                            type = NavType.StringType
                        }
                    )
                ) { backStackEntry ->
                    val navigateFrom = backStackEntry.arguments?.getString(NAVIGATE_FROM)
                    val gameId = backStackEntry.arguments?.getString(GAME_ID)

                    GamesListScreen(
                        gameId = gameId ?: "",
                        navigateFrom = navigateFrom ?: "",
                        onArrowBackClicked = {
                            navController.navigateUp()
                        },
                        onSimilarGameClicked = { gamesId ->
                            navController.navigate(
                                Screen.DetailScreen.createRoute(
                                    gamesId.toString()
                                )
                            ) {
                                if (navigateFrom == FROM_SIMILAR_GAMES) {
                                    popUpTo(Screen.HomeScreen.route) {
                                        inclusive = false
                                    }
                                }
                            }
                        }
                    )
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
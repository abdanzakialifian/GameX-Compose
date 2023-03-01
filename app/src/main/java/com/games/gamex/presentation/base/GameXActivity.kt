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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.games.gamex.presentation.detail.view.DetailScreen
import com.games.gamex.presentation.home.view.HomeScreen
import com.games.gamex.presentation.home.viewmodel.HomeViewModel
import com.games.gamex.presentation.navigation.Screen
import com.games.gamex.presentation.splash.SplashScreen
import com.games.gamex.presentation.ui.theme.GameXTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameXActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameXTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    GameXApp()
                }
            }
        }
    }
}

@Composable
fun GameXApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(Screen.SplashScreen.route) {
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
        composable(Screen.HomeScreen.route) {
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
                onAllGenresClicked = { navController.navigate(Screen.DetailScreen.route) },
                onAllGamesClicked = { navController.navigate(Screen.DetailScreen.route) },
                onAllPlatformsClicked = { navController.navigate(Screen.DetailScreen.route) },
                onSearchAllGamesClicked = { navController.navigate(Screen.DetailScreen.route) },
                viewModel = viewModel,
            )
        }
        composable(Screen.DetailScreen.route) {
            DetailScreen()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4_XL)
@Composable
fun GameXPreview() {
    GameXTheme {
        GameXApp()
    }
}
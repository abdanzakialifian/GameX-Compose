package com.games.gamex.presentation.splash

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.games.gamex.R
import com.games.gamex.presentation.ui.theme.GameXTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigate: () -> Unit, modifier: Modifier = Modifier) {
    LaunchedEffect(key1 = Unit) {
        delay(3000L)
        onNavigate()
    }

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_animation))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        speed = 2F,
        restartOnPlay = false
    )

    SplashContent(composition = composition, progress = progress, modifier = modifier)
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4_XL)
@Composable
fun SplashScreenPreview() {
    GameXTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            SplashScreen(onNavigate = {})
        }
    }
}
package com.games.gamex.presentation.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation

@Composable
fun SplashContent(composition: LottieComposition?, progress: Float, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        LottieAnimation(
            modifier = Modifier
                .size(250.dp)
                .align(Alignment.Center),
            composition = composition,
            progress = progress,
        )
    }
}
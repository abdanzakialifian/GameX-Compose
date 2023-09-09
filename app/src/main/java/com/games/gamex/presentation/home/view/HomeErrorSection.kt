package com.games.gamex.presentation.home.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.games.gamex.R

@Composable
fun HomeErrorSection() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error_animation))
    val progress by animateLottieCompositionAsState(
        composition = composition, speed = 2F, restartOnPlay = true
    )

    Box(modifier = Modifier.fillMaxSize()) {
        LottieAnimation(
            modifier = Modifier
                .size(250.dp)
                .align(Alignment.Center),
            composition = composition,
            progress = progress
        )
    }
}
package com.games.gamex.presentation.component

import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.games.gamex.utils.Shimmer

@Composable
fun ShimmerAnimation(shimmer: Shimmer) {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6F),
        Color.LightGray.copy(alpha = 0.2F),
        Color.LightGray.copy(alpha = 0.6F)
    )
    val transition = rememberInfiniteTransition()
    val translateAnimation = transition.animateFloat(
        initialValue = 0F,
        targetValue = 1000F,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )
    )
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnimation.value, y = translateAnimation.value)
    )

    when (shimmer) {
        Shimmer.GAME_ITEM_SHIMMER -> GameItemShimmer(brush = brush)
        Shimmer.CATEGORIES_ITEM_SHIMMER -> CategoriesItemShimmer(brush = brush)
        Shimmer.PLATFORM_ITEM_SHIMMER -> PlatformItemShimmer(brush = brush)
    }
}
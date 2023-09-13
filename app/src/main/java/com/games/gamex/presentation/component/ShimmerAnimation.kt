package com.games.gamex.presentation.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
    val transition = rememberInfiniteTransition(label = "transition")
    val translateAnimation = transition.animateFloat(
        initialValue = 0F,
        targetValue = 1000F,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        ), label = "transition animation"
    )
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnimation.value, y = translateAnimation.value)
    )

    when (shimmer) {
        Shimmer.GAME_ITEM_HORIZONTAL_PLACEHOLDER -> GameItemHorizontalPlaceholder(brush = brush)
        Shimmer.CATEGORIES_ITEM_PLACEHOLDER -> CategoriesItemPlaceholder(brush = brush)
        Shimmer.PLATFORM_ITEM_PLACEHOLDER -> PlatformItemPlaceholder(brush = brush)
        Shimmer.GAME_ITEM_VERTICAL_PLACEHOLDER -> GameItemPagingPlaceholder(brush = brush)
        Shimmer.GAME_DETAIL_PLACEHOLDER -> DetailPlaceholder(brush = brush)
    }
}
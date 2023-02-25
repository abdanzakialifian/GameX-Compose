package com.games.gamex.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.games.gamex.presentation.ui.theme.GameXTheme

@Composable
fun GameItemShimmer(brush: Brush, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .size(width = 130.dp, height = 190.dp),
        backgroundColor = Color.White,
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(brush = brush))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(brush = brush)
                    .height(30.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Row(modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(horizontal = 8.dp)) {
                    Box(
                        modifier = Modifier
                            .size(15.dp)
                            .clip(CircleShape)
                            .background(brush = brush)
                            .align(Alignment.CenterVertically)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(15.dp)
                            .padding(start = 2.dp)
                            .background(brush = brush)
                            .align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameItemShimmerPreview() {
    GameXTheme {
        GameItemShimmer(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color.LightGray.copy(alpha = 0.6F),
                    Color.LightGray.copy(alpha = 0.2F),
                    Color.LightGray.copy(alpha = 0.6F)
                )
            )
        )
    }
}
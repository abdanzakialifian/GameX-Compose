package com.games.gamex.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.games.gamex.R
import com.games.gamex.presentation.ui.theme.GameXTheme

@Composable
fun GameItemHorizontalPlaceholder(brush: Brush, modifier: Modifier = Modifier) {
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
                    Text(
                        modifier = Modifier
                            .padding(start = 2.dp)
                            .fillMaxWidth()
                            .align(Alignment.CenterVertically)
                            .background(brush = brush, shape = RoundedCornerShape(6.dp)),
                        text = "",
                        color = colorResource(id = R.color.white),
                        fontSize = 12.sp,
                        overflow = TextOverflow.Ellipsis,
                        fontFamily = FontFamily(Font(resId = R.font.open_sans_medium))
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameItemPlaceholderPreview() {
    GameXTheme {
        GameItemHorizontalPlaceholder(
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
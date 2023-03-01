package com.games.gamex.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.games.gamex.R
import com.games.gamex.presentation.ui.theme.GameXTheme

@Composable
fun PlatformItemPlaceholder(brush: Brush, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.size(width = 250.dp, height = 230.dp),
        backgroundColor = Color.White,
        shape = RoundedCornerShape(20.dp),
        elevation = 0.dp
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(brush = brush),
            )
            Text(
                modifier = Modifier
                    .width(80.dp)
                    .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                    .background(brush = brush, shape = RoundedCornerShape(6.dp)),
                text = "",
                fontFamily = FontFamily(Font(resId = R.font.open_sans_bold)),
                fontSize = 16.sp,
                color = Color.Black,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier
                    .width(140.dp)
                    .padding(start = 10.dp, top = 2.dp, end = 10.dp)
                    .background(brush = brush, shape = RoundedCornerShape(6.dp)),
                text = "",
                fontFamily = FontFamily(Font(resId = R.font.open_sans_medium)),
                fontSize = 12.sp,
                color = Color.Gray,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlatformItemPlaceholderPreview() {
    GameXTheme {
        PlatformItemPlaceholder(
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
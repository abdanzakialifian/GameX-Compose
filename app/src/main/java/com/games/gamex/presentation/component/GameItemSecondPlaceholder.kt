package com.games.gamex.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun GameItemSecondPlaceholder(brush: Brush, modifier: Modifier = Modifier) {
    Card(
        modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color.White,
        elevation = 0.dp,
    ) {
        Row {
            Box(
                modifier = Modifier
                    .size(height = 100.dp, width = 100.dp)
                    .padding(vertical = 10.dp, horizontal = 10.dp)
                    .background(brush = brush, shape = RoundedCornerShape(10.dp))
            )
            Column(Modifier.align(Alignment.CenterVertically)) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 100.dp)
                        .background(brush = brush, shape = RoundedCornerShape(8.dp)),
                    text = "",
                    fontFamily = FontFamily(Font(resId = R.font.open_sans_semi_bold)),
                    fontSize = 16.sp,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 160.dp)
                        .background(brush = brush, shape = RoundedCornerShape(8.dp)),
                    text = "",
                    fontFamily = FontFamily(Font(R.font.open_sans_medium)),
                    fontSize = 12.sp,
                    color = Color.LightGray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 20.dp)
                        .padding(end = 140.dp)
                        .background(brush = brush, shape = RoundedCornerShape(8.dp))
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameItemSecondPlaceholderPreview() {
    GameXTheme {
        GameItemSecondPlaceholder(
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
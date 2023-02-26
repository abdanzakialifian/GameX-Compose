package com.games.gamex.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.games.gamex.R
import com.games.gamex.presentation.ui.theme.GameXTheme

@Composable
fun PlatformItem(image: String, name: String, totalGames: Int, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.size(width = 250.dp, height = 210.dp),
        backgroundColor = Color.White,
        shape = RoundedCornerShape(20.dp),
        elevation = 4.dp
    ) {
        Column {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(20.dp)),
                model = image,
                contentDescription = "Image Platform",
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier.padding(start = 10.dp, top = 10.dp, end = 10.dp),
                text = name,
                fontFamily = FontFamily(Font(resId = R.font.open_sans_medium)),
                fontSize = 16.sp,
                color = Color.Black,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier.padding(start = 10.dp, top = 4.dp, end = 10.dp),
                text = "Total games : $totalGames",
                fontFamily = FontFamily(Font(resId = R.font.open_sans_medium)),
                fontSize = 14.sp,
                color = Color.LightGray,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlatformItemPreview() {
    GameXTheme {
        PlatformItem(image = "", name = "PC", totalGames = 531329)
    }
}
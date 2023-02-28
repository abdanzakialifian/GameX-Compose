package com.games.gamex.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.games.gamex.R
import com.games.gamex.presentation.ui.theme.GameXTheme

@Composable
fun GameItemSecond(
    image: String,
    name: String,
    onItemClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color.White,
        elevation = 0.dp,
    ) {
        Row(modifier = Modifier.clickable { onItemClicked() }) {
            AsyncImage(
                modifier = Modifier
                    .size(height = 100.dp, width = 100.dp)
                    .padding(vertical = 10.dp, horizontal = 10.dp)
                    .clip(RoundedCornerShape(10.dp)),
                model = image,
                contentDescription = "Image Game",
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = name,
                fontFamily = FontFamily(Font(resId = R.font.open_sans_semi_bold)),
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameItemSecondPreview() {
    GameXTheme {
        GameItemSecond(image = "", name = "", onItemClicked = {})
    }
}
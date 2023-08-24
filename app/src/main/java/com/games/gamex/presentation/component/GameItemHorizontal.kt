package com.games.gamex.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
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
fun GameItemHorizontal(image: String, title: String, onItemClicked: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .size(width = 130.dp, height = 190.dp),
        backgroundColor = Color.White,
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp,
    ) {
        Box(modifier = Modifier.fillMaxSize().clickable { onItemClicked() }) {
            AsyncImage(
                modifier = Modifier.align(Alignment.Center),
                model = image,
                placeholder = painterResource(id = R.drawable.ic_load_64),
                error = painterResource(id = R.drawable.ic_broken_image_64),
                contentDescription = "Image Game",
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                colorResource(id = R.color.black_opacity_15),
                                colorResource(id = R.color.black_opacity_50),
                            )
                        )
                    )
                    .height(30.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(horizontal = 4.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(20.dp),
                        painter = painterResource(id = R.drawable.ic_verified),
                        contentDescription = "Icon Verified"
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 2.dp)
                            .align(Alignment.CenterVertically),
                        text = title,
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
fun GameItemPreview() {
    GameXTheme {
        GameItemHorizontal(image = "", title = "", onItemClicked = {})
    }
}

package com.games.gamex.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.games.gamex.R
import com.games.gamex.presentation.ui.theme.GameXTheme

@Composable
fun CategoriesItem(
    category: String,
    image: String,
    onItemClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.size(width = 90.dp, height = 110.dp)) {
        AsyncImage(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(80.dp)
                .clip(CircleShape)
                .clickable { onItemClicked() },
            model = image,
            placeholder = painterResource(id = R.drawable.ic_load_64),
            error = painterResource(id = R.drawable.ic_broken_image_64),
            contentDescription = "Image Categories",
            contentScale = ContentScale.Crop,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(top = 6.dp),
            text = category,
            fontSize = 14.sp,
            fontFamily = FontFamily(
                Font(resId = R.font.open_sans_semi_bold)
            ),
            color = Color.Black,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoriesItemPreview() {
    GameXTheme {
        CategoriesItem("Adventure", image = "", onItemClicked = {})
    }
}
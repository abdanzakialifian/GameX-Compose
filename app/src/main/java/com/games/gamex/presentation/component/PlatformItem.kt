package com.games.gamex.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.games.gamex.R
import com.games.gamex.presentation.ui.theme.GameXTheme
import com.games.gamex.presentation.ui.theme.GreyPlaceholder

@Composable
fun PlatformItem(
    image: String,
    name: String,
    totalGames: Int,
    onItemClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isLoading by remember {
        mutableStateOf(true)
    }

    var isError by remember {
        mutableStateOf(true)
    }

    Card(
        modifier = modifier.size(width = 250.dp, height = 230.dp),
        backgroundColor = Color.White,
        shape = RoundedCornerShape(20.dp),
        elevation = 0.dp
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (!isLoading && !isError) {
                    onItemClicked()
                }
            }) {
            AsyncImage(
                modifier = Modifier
                    .height(160.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .align(Alignment.CenterHorizontally),
                model = image,
                placeholder = ColorPainter(GreyPlaceholder),
                error = painterResource(id = R.drawable.ic_broken_image_64),
                contentDescription = "Image Platform",
                onSuccess = {
                    isLoading = false
                    isError = false
                },
                onLoading = { isLoading = true },
                onError = { isError = true },
                contentScale = if (isLoading || isError) ContentScale.None else ContentScale.Crop
            )
            Text(
                modifier = Modifier.padding(start = 10.dp, top = 10.dp, end = 10.dp),
                text = name,
                fontFamily = FontFamily(Font(resId = R.font.open_sans_bold)),
                fontSize = 16.sp,
                color = Color.Black,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier.padding(start = 10.dp, top = 2.dp, end = 10.dp),
                text = stringResource(id = R.string.total_games, totalGames),
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
fun PlatformItemPreview() {
    GameXTheme {
        PlatformItem(image = "", name = "PC", totalGames = 531329, onItemClicked = {})
    }
}
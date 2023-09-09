package com.games.gamex.presentation.detail.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.games.gamex.R
import com.games.gamex.presentation.ui.theme.GreyPlaceholder

@Composable
fun DetailImageScreenshot(
    images: List<Pair<Int, String>>?, modifier: Modifier = Modifier, isPreview: Boolean = false
) {
    if (isPreview) {
        LazyRow(
            modifier = modifier,
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(10) {
                Box(
                    modifier = Modifier
                        .size(width = 230.dp, height = 150.dp)
                        .background(color = Color.LightGray, shape = RoundedCornerShape(10.dp))
                )
            }
        }
    } else {
        LazyRow(
            modifier = modifier,
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(images ?: listOf(), key = { data -> data.first }) { screenshot ->
                AsyncImage(
                    modifier = Modifier
                        .size(width = 230.dp, height = 150.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    model = screenshot.second,
                    placeholder = ColorPainter(GreyPlaceholder),
                    error = painterResource(id = R.drawable.ic_broken_image_64),
                    contentDescription = "Image Screenshot",
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}
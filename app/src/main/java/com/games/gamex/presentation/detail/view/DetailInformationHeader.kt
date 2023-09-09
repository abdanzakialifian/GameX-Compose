package com.games.gamex.presentation.detail.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.games.gamex.R
import com.games.gamex.presentation.ui.theme.GreyPlaceholder
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize

@Composable
fun DetailInformationHeader(
    genres: List<String>?,
    imageBackground: String?,
    name: String?,
    rating: Double?,
    modifier: Modifier = Modifier,
    isPreview: Boolean = false,
) {
    val genre = genres?.joinToString(", ")

    Row(modifier.padding(horizontal = 20.dp)) {
        if (isPreview) {
            Box(
                modifier = Modifier
                    .size(height = 80.dp, width = 80.dp)
                    .background(color = Color.LightGray, shape = RoundedCornerShape(10.dp)),
            )
        } else {
            AsyncImage(
                modifier = Modifier
                    .size(height = 80.dp, width = 80.dp)
                    .clip(RoundedCornerShape(10.dp)),
                model = imageBackground,
                placeholder = ColorPainter(GreyPlaceholder),
                error = painterResource(id = R.drawable.ic_broken_image_64),
                contentDescription = "Image Game",
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier
                .padding(start = 10.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = name ?: "No Game Name",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontFamily = FontFamily(
                    Font(R.font.open_sans_bold)
                ),
                fontSize = 18.sp
            )

            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = if (!genre.isNullOrEmpty()) genre else "-",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontFamily = FontFamily(
                    Font(R.font.open_sans_regular),
                ),
                color = colorResource(id = R.color.dark_grey),
                fontSize = 12.sp
            )

            Row(
                modifier = Modifier.padding(top = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RatingBar(
                    value = rating?.toFloat() ?: 0F,
                    stepSize = StepSize.HALF,
                    isIndicator = true,
                    size = 16.dp,
                    style = RatingBarStyle.Fill(),
                    spaceBetween = 2.dp,
                    onValueChange = {},
                    onRatingChanged = {},
                )

                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = rating?.toString() ?: "0",
                    fontFamily = FontFamily(
                        Font(R.font.open_sans_semi_bold)
                    ),
                    fontSize = 12.sp
                )
            }
        }
    }
}
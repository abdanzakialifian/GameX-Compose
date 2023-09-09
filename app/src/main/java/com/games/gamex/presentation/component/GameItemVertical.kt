package com.games.gamex.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.games.gamex.utils.convertDate
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize

@Composable
fun GameItemVertical(
    image: String,
    name: String,
    date: String,
    rating: Float,
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
                placeholder = ColorPainter(GreyPlaceholder),
                error = painterResource(id = R.drawable.ic_broken_image_64),
                contentDescription = "Image Game",
                contentScale = ContentScale.Crop
            )
            Column(Modifier.align(Alignment.CenterVertically)) {
                Text(
                    text = name,
                    fontFamily = FontFamily(Font(resId = R.font.open_sans_semi_bold)),
                    fontSize = 16.sp,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(
                        id = R.string.release_date,
                        if (date != "") date.convertDate() else "-"
                    ),
                    fontFamily = FontFamily(Font(R.font.open_sans_medium)),
                    fontSize = 12.sp,
                    color = Color.LightGray
                )
                Spacer(modifier = Modifier.height(4.dp))
                RatingBar(
                    value = rating,
                    stepSize = StepSize.HALF,
                    isIndicator = true,
                    size = 16.dp,
                    style = RatingBarStyle.Fill(),
                    spaceBetween = 2.dp,
                    onValueChange = {},
                    onRatingChanged = {},
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameItemSecondPreview() {
    GameXTheme {
        GameItemVertical(
            image = "",
            name = "GTA-V",
            date = "2011-04-18",
            rating = 3.5F,
            onItemClicked = {})
    }
}
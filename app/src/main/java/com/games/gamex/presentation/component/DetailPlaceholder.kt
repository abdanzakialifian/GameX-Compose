package com.games.gamex.presentation.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.games.gamex.R
import com.games.gamex.domain.model.ListResultItem
import com.games.gamex.presentation.detail.view.DetailContent
import com.games.gamex.presentation.ui.theme.GameXTheme
import com.games.gamex.presentation.ui.theme.Purple
import com.games.gamex.presentation.ui.theme.WhiteHeavy
import com.games.gamex.presentation.ui.theme.WhiteTransparent
import com.games.gamex.utils.UiState
import kotlinx.coroutines.flow.flowOf

@Composable
fun DetailPlaceholder(brush: Brush, modifier: Modifier = Modifier) {
    Box(modifier = modifier.background(color = Color.LightGray)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .align(Alignment.TopCenter)
                .background(brush = brush),
        )

        Box(
            Modifier
                .padding(start = 10.dp, top = 10.dp)
                .size(40.dp)
                .clip(CircleShape)
                .background(WhiteTransparent)
                .align(Alignment.TopStart)
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(20.dp),
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back Button"
            )
        }

        DetailContentInformationPlaceholder(brush)
    }
}

@Composable
fun DetailContentInformationPlaceholder(brush: Brush, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 250.dp),
        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(top = 20.dp)
        ) {
            DetailContentInformationHeaderPlaceholder(brush)

            DetailContentInformationSubHeaderPlaceholder(brush)

            ImageScreenshotPlaceholder(brush)

            OverviewPlaceholder(
                brush,
                modifier = Modifier.padding(20.dp),
            )

            SimilarGamesPlaceholder(brush)
        }
    }
}

@Composable
fun DetailContentInformationHeaderPlaceholder(brush: Brush, modifier: Modifier = Modifier) {
    Row(modifier.padding(horizontal = 20.dp)) {

        Box(
            modifier = Modifier
                .size(height = 80.dp, width = 80.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(brush),
        )

        Column(
            modifier = Modifier
                .padding(start = 10.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 2.dp, end = 150.dp)
                    .fillMaxWidth()
                    .background(brush = brush, shape = RoundedCornerShape(6.dp)),
                text = "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontFamily = FontFamily(
                    Font(R.font.open_sans_bold)
                ),
                fontSize = 18.sp
            )

            Text(
                modifier = Modifier
                    .padding(start = 2.dp, top = 4.dp, end = 100.dp)
                    .fillMaxWidth()
                    .background(brush = brush, shape = RoundedCornerShape(6.dp)),
                text = "",
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
                Row {
                    Icon(
                        modifier = Modifier.size(19.dp),
                        imageVector = Icons.Filled.Star,
                        contentDescription = "star",
                        tint = Color.LightGray
                    )
                    Icon(
                        modifier = Modifier.size(19.dp),
                        imageVector = Icons.Filled.Star,
                        contentDescription = "star",
                        tint = Color.LightGray
                    )
                    Icon(
                        modifier = Modifier.size(19.dp),
                        imageVector = Icons.Filled.Star,
                        contentDescription = "star",
                        tint = Color.LightGray
                    )
                    Icon(
                        modifier = Modifier.size(19.dp),
                        imageVector = Icons.Filled.Star,
                        contentDescription = "star",
                        tint = Color.LightGray
                    )
                    Icon(
                        modifier = Modifier.size(19.dp),
                        imageVector = Icons.Filled.Star,
                        contentDescription = "star",
                        tint = Color.LightGray
                    )
                }

                Text(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .width(20.dp)
                        .background(brush = brush, shape = RoundedCornerShape(6.dp)),
                    text = "",
                    fontFamily = FontFamily(
                        Font(R.font.open_sans_semi_bold)
                    ),
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun DetailContentInformationSubHeaderPlaceholder(brush: Brush, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .padding(30.dp)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = modifier
                .weight(1F),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .width(50.dp)
                    .background(brush = brush, shape = RoundedCornerShape(6.dp)),
                text = "",
                fontFamily = FontFamily(Font(R.font.open_sans_medium)),
                fontSize = 12.sp,
                color = colorResource(id = R.color.dark_grey)
            )

            Text(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .background(brush = brush, shape = RoundedCornerShape(6.dp)),
                text = "",
                fontFamily = FontFamily(Font(R.font.open_sans_semi_bold)),
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Divider(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxHeight()
                .width(1.dp),
            color = WhiteHeavy,
        )

        Column(
            modifier = modifier
                .weight(1F),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .width(50.dp)
                    .background(brush = brush, shape = RoundedCornerShape(6.dp)),
                text = "",
                fontFamily = FontFamily(Font(R.font.open_sans_medium)),
                fontSize = 12.sp,
                color = colorResource(id = R.color.dark_grey)
            )

            Box(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .size(35.dp)
                    .border(
                        width = 1.5.dp,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Text(
                    modifier = Modifier
                        .width(20.dp)
                        .align(Alignment.Center)
                        .background(brush = brush, shape = RoundedCornerShape(4.dp)),
                    text = "",
                    color = Purple,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.open_sans_bold))
                )
            }
        }

        Divider(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxHeight()
                .width(1.dp),
            color = WhiteHeavy,
        )

        Column(
            modifier = modifier
                .weight(1F),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .width(50.dp)
                    .background(brush = brush, shape = RoundedCornerShape(6.dp)),
                text = "",
                fontFamily = FontFamily(Font(R.font.open_sans_medium)),
                fontSize = 12.sp,
                color = colorResource(id = R.color.dark_grey)
            )

            Text(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .background(brush = brush, shape = RoundedCornerShape(6.dp)),
                text = "",
                fontFamily = FontFamily(Font(R.font.open_sans_semi_bold)),
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun ImageScreenshotPlaceholder(brush: Brush, modifier: Modifier = Modifier) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(10) {
            Box(
                modifier = Modifier
                    .size(width = 230.dp, height = 150.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(brush)
            )
        }
    }
}

@Composable
fun OverviewPlaceholder(brush: Brush, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier
                .width(100.dp)
                .background(brush = brush, shape = RoundedCornerShape(6.dp)),
            text = "",
            fontFamily = FontFamily(
                Font(R.font.open_sans_bold)
            ),
            fontSize = 16.sp
        )

        Text(
            modifier = Modifier
                .padding(top = 6.dp)
                .fillMaxWidth()
                .height(100.dp)
                .animateContentSize(
                    // add animation expandable
                    animationSpec = tween(
                        durationMillis = 300, easing = LinearOutSlowInEasing
                    )
                )
                .background(brush = brush, shape = RoundedCornerShape(6.dp)),
            text = "",
            fontFamily = FontFamily(
                Font(R.font.open_sans_regular)
            ),
            color = colorResource(id = R.color.dark_grey),
            fontSize = 12.sp,
            lineHeight = 20.sp
        )

        Text(
            modifier = Modifier
                .width(50.dp)
                .padding(vertical = 4.dp)
                .background(brush = brush, shape = RoundedCornerShape(6.dp)),
            text = "",
            fontFamily = FontFamily(
                Font(R.font.open_sans_medium)
            ),
            color = colorResource(id = R.color.purple),
            fontSize = 12.sp
        )
    }
}

@Composable
fun SimilarGamesPlaceholder(brush: Brush, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .width(70.dp)
                    .background(brush = brush, shape = RoundedCornerShape(6.dp)),
                text = "",
                fontFamily = FontFamily(
                    Font(R.font.open_sans_bold)
                ),
                fontSize = 16.sp
            )
            Text(
                modifier = Modifier
                    .width(40.dp)
                    .background(brush = brush, shape = RoundedCornerShape(6.dp)),
                text = "",
                fontFamily = FontFamily(
                    Font(R.font.open_sans_semi_bold)
                ),
                color = Purple,
                fontSize = 14.sp
            )
        }

        LazyRow(
            modifier = Modifier.padding(vertical = 14.dp),
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(10) {
                GameItemHorizontalPlaceholder(brush = brush)
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_4_XL,
    backgroundColor = 0xFF5F67EA
)
@Composable
fun DetailScreenLoadingPreview() {
    val listResultPagingItems =
        flowOf(PagingData.empty<ListResultItem>()).collectAsLazyPagingItems()

    GameXTheme {
        DetailContent(
            uiState = UiState.Loading,
            gameSeriesPagingItems = listResultPagingItems,
            onImageUrl = {},
            onImageBackClick = { }
        )
    }
}
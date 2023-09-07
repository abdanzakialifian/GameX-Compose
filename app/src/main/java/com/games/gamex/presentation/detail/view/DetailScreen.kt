package com.games.gamex.presentation.detail.view

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.games.gamex.R
import com.games.gamex.domain.model.DetailGame
import com.games.gamex.presentation.component.GameItemHorizontal
import com.games.gamex.presentation.component.ShimmerAnimation
import com.games.gamex.presentation.detail.viewmodel.DetailViewModel
import com.games.gamex.presentation.ui.theme.GameXTheme
import com.games.gamex.presentation.ui.theme.GreyPlaceholder
import com.games.gamex.presentation.ui.theme.LightPurple
import com.games.gamex.presentation.ui.theme.Purple
import com.games.gamex.presentation.ui.theme.WhiteHeavy
import com.games.gamex.presentation.ui.theme.WhiteTransparent
import com.games.gamex.utils.PaletteGenerator.convertImageUrlToBitmap
import com.games.gamex.utils.PaletteGenerator.extractColorsFromBitmap
import com.games.gamex.utils.Shimmer
import com.games.gamex.utils.UiState
import com.games.gamex.utils.convertDate
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize

@Composable
fun DetailScreen(
    gameId: String,
    onColorPalette: (Map<String, String>) -> Unit,
    onImageBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    var imageUrl by remember {
        mutableStateOf("")
    }

    val getDetailGameState by viewModel.getDetailGame.collectAsStateWithLifecycle(initialValue = UiState.Loading)

    // get color from image background
    LaunchedEffect(key1 = imageUrl) {
        try {
            val bitmap = convertImageUrlToBitmap(imageUrl, context)
            if (bitmap != null) {
                val colors = extractColorsFromBitmap(bitmap)
                onColorPalette(colors)
            }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.setGameId(gameId)
    }

    DetailContent(
        uiState = getDetailGameState, onImageUrl = { url ->
            imageUrl = url
        }, onImageBackClick = onImageBackClick, modifier = modifier
    )
}

@Composable
fun DetailContent(
    uiState: UiState<DetailGame>,
    onImageUrl: (String) -> Unit,
    onImageBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    isPreview: Boolean = false,
) {
    when (uiState) {
        is UiState.Loading -> ShimmerAnimation(shimmer = Shimmer.GAME_DETAIL_PlACEHOLDER)

        is UiState.Success -> {
            val data = uiState.data

            onImageUrl(data.backgroundImageAdditional ?: "")

            Box(modifier = modifier) {
                if (isPreview) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .align(Alignment.TopCenter)
                            .background(color = Color.LightGray)
                    )
                } else {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .align(Alignment.TopCenter),
                        model = data.backgroundImageAdditional,
                        contentDescription = "Image Background",
                        contentScale = ContentScale.Crop
                    )
                }

                Box(
                    Modifier
                        .padding(start = 10.dp, top = 10.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(WhiteTransparent)
                        .align(Alignment.TopStart)
                        .clickable { onImageBackClick() }) {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(20.dp),
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back Button"
                    )
                }

                DetailContentInformation(data = data, isPreview = isPreview)
            }
        }

        is UiState.Error -> {}
    }
}

@Composable
fun DetailContentInformation(
    data: DetailGame,
    modifier: Modifier = Modifier,
    isPreview: Boolean = false,
) {
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
            DetailContentInformationHeader(data = data, isPreview = isPreview)

            DetailContentInformationSubHeader(data)

            ImageScreenshot(images = data.images, isPreview = isPreview)

            Overview(
                data = data,
                modifier = Modifier.padding(20.dp),
            )

            SimilarGames(data = data, isPreview = isPreview)
        }
    }
}

@Composable
fun DetailContentInformationHeader(
    data: DetailGame,
    modifier: Modifier = Modifier,
    isPreview: Boolean = false,
) {
    val genres = data.genres?.joinToString(", ")

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
                model = data.imageBackground,
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
                text = data.name ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontFamily = FontFamily(
                    Font(R.font.open_sans_bold)
                ),
                fontSize = 18.sp
            )

            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = genres ?: "",
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
                    value = data.rating?.toFloat() ?: 0F,
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
                    text = data.rating?.toString() ?: "",
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
fun DetailContentInformationSubHeader(data: DetailGame, modifier: Modifier = Modifier) {
    val publisher = data.publishers?.joinToString(", ")

    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .padding(30.dp)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = modifier.weight(1F), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Release Date",
                fontFamily = FontFamily(Font(R.font.open_sans_medium)),
                fontSize = 12.sp,
                color = colorResource(id = R.color.dark_grey)
            )

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = data.released?.convertDate() ?: "",
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
            modifier = modifier.weight(1F), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Metascore",
                fontFamily = FontFamily(Font(R.font.open_sans_medium)),
                fontSize = 12.sp,
                color = colorResource(id = R.color.dark_grey)
            )

            Box(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .size(35.dp)
                    .border(
                        width = 1.5.dp, color = LightPurple, shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = (data.metacritic ?: 0).toString(),
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
            modifier = modifier.weight(1F), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Publisher",
                fontFamily = FontFamily(Font(R.font.open_sans_medium)),
                fontSize = 12.sp,
                color = colorResource(id = R.color.dark_grey)
            )

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = publisher ?: "",
                fontFamily = FontFamily(Font(R.font.open_sans_semi_bold)),
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun ImageScreenshot(
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

@Composable
fun SimilarGames(data: DetailGame, modifier: Modifier = Modifier, isPreview: Boolean = false) {
    if (isPreview) {
        Column(modifier = modifier) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.similar_games), fontFamily = FontFamily(
                        Font(R.font.open_sans_bold)
                    ), fontSize = 16.sp
                )

                Text(
                    text = stringResource(id = R.string.see_all), fontFamily = FontFamily(
                        Font(R.font.open_sans_semi_bold)
                    ), color = Purple, fontSize = 14.sp
                )
            }

            LazyRow(
                modifier = Modifier.padding(vertical = 14.dp),
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(10) {
                    GameItemHorizontal(
                        image = "",
                        title = "",
                        onItemClicked = { }
                    )
                }
            }
        }
    } else {
        if (!data.gameSeries?.second.isNullOrEmpty()) {
            Column(modifier = modifier) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.similar_games), fontFamily = FontFamily(
                            Font(R.font.open_sans_bold)
                        ), fontSize = 16.sp
                    )

                    if ((data.gameSeries?.first ?: 0) > 6) {
                        Text(
                            text = stringResource(id = R.string.see_all), fontFamily = FontFamily(
                                Font(R.font.open_sans_semi_bold)
                            ), color = Purple, fontSize = 14.sp
                        )
                    }
                }

                LazyRow(
                    modifier = Modifier.padding(vertical = 14.dp),
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(
                        data.gameSeries?.second ?: listOf(),
                        key = { data -> data.id ?: 0 }) { gameSeries ->
                        GameItemHorizontal(image = gameSeries.image ?: "",
                            title = gameSeries.name ?: "",
                            onItemClicked = { })
                    }
                }
            }
        }
    }
}

@Composable
fun Overview(data: DetailGame, modifier: Modifier = Modifier) {
    var isShowMore by remember {
        mutableStateOf(false)
    }

    if (!data.description.isNullOrEmpty()) {
        Column(modifier = modifier) {
            Text(
                text = stringResource(id = R.string.overview), fontFamily = FontFamily(
                    Font(R.font.open_sans_bold)
                ), fontSize = 16.sp
            )

            Text(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .animateContentSize(
                        // add animation expandable
                        animationSpec = tween(
                            durationMillis = 300, easing = LinearOutSlowInEasing
                        )
                    ),
                text = data.description,
                maxLines = if (isShowMore) Int.MAX_VALUE else 3,
                overflow = if (isShowMore) TextOverflow.Clip else TextOverflow.Ellipsis,
                fontFamily = FontFamily(
                    Font(R.font.open_sans_regular)
                ),
                color = colorResource(id = R.color.dark_grey),
                fontSize = 12.sp,
                lineHeight = 20.sp
            )

            Text(modifier = Modifier
                .clickable(
                    // remove ripple click
                    interactionSource = remember {
                        MutableInteractionSource()
                    }, indication = null
                ) {
                    isShowMore = !isShowMore
                }
                .padding(vertical = 4.dp),
                text = if (isShowMore) stringResource(id = R.string.show_less) else stringResource(
                    id = R.string.read_more
                ),
                fontFamily = FontFamily(
                    Font(R.font.open_sans_medium)
                ),
                color = colorResource(id = R.color.purple),
                fontSize = 12.sp)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4_XL)
@Composable
fun DetailScreenSuccessPreview() {
    val detail = DetailGame(
        name = "The Witcher",
        imageBackground = "",
        backgroundImageAdditional = "",
        genres = listOf(
            "Action", "Adventure", "RPG"
        ),
        rating = 4.5,
        description = "This is Description",
        images = listOf(),
        gameSeries = Pair(1, listOf()),
        metacritic = 93,
        publishers = listOf("CD PROJEKT RED"),
        released = "2015-05-18"
    )

    GameXTheme {
        DetailContent(
            uiState = UiState.Success(detail),
            onImageUrl = {},
            onImageBackClick = { },
            isPreview = true
        )
    }
}
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
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
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
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.games.gamex.R
import com.games.gamex.domain.model.DetailGame
import com.games.gamex.domain.model.ListResultItem
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

@Composable
fun DetailScreen(
    gameId: String,
    onColorPalette: (Map<String, String>) -> Unit,
    onImageBackClicked: () -> Unit,
    onSeeAllClicked: (String) -> Unit,
    onSimilarGameClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    var isFetchData by rememberSaveable {
        mutableStateOf(false)
    }

    var imageUrl by remember {
        mutableStateOf("")
    }

    val getDetailGamePair by viewModel.getDetailGame.collectAsStateWithLifecycle(
        initialValue = Pair(
            UiState.Loading, MutableStateFlow(PagingData.empty())
        ),
    )

    val getDetailGameState = getDetailGamePair.first

    val getGameSeriesPagingItems = getDetailGamePair.second.collectAsLazyPagingItems()

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

    if (!isFetchData) {
        LaunchedEffect(key1 = true) {
            isFetchData = true
            viewModel.setDataGameSeries(gameId, false)
        }
    }

    DetailContentState(
        uiState = getDetailGameState,
        gameSeriesPagingItems = getGameSeriesPagingItems,
        onImageUrl = { url ->
            imageUrl = url
        },
        onImageBackClick = onImageBackClicked,
        onSeeAllClicked = {
            onSeeAllClicked(gameId)
        },
        onSimilarGameClicked = onSimilarGameClicked,
        modifier = modifier
    )
}


@Composable
fun DetailContentState(
    uiState: UiState<DetailGame>,
    gameSeriesPagingItems: LazyPagingItems<ListResultItem>,
    onImageUrl: (String) -> Unit,
    onImageBackClick: () -> Unit,
    onSeeAllClicked: () -> Unit,
    onSimilarGameClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
    isPreview: Boolean = false,
) {
    when (uiState) {
        is UiState.Loading -> ShimmerAnimation(shimmer = Shimmer.GAME_DETAIL_PLACEHOLDER)

        is UiState.Success -> DetailContent(
            data = uiState.data,
            gameSeriesPagingItems = gameSeriesPagingItems,
            onImageUrl = onImageUrl,
            onImageBackClick = onImageBackClick,
            onSeeAllClicked = onSeeAllClicked,
            onSimilarGameClicked = onSimilarGameClicked,
            isPreview = isPreview,
            modifier = modifier
        )

        is UiState.Error -> {}
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailContent(
    data: DetailGame,
    gameSeriesPagingItems: LazyPagingItems<ListResultItem>,
    onImageUrl: (String) -> Unit,
    onImageBackClick: () -> Unit,
    onSeeAllClicked: () -> Unit,
    onSimilarGameClicked: (Int) -> Unit,
    isPreview: Boolean,
    modifier: Modifier = Modifier,
) {

    onImageUrl(data.backgroundImageAdditional ?: "")

    val configuration = LocalConfiguration.current

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        // set initial bottom sheet
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )

    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = bottomSheetScaffoldState,
        sheetElevation = 0.dp,
        sheetBackgroundColor = Color.Transparent,
        sheetContentColor = Color.Transparent,
        sheetContent = {
            DetailInformation(
                data = data,
                gameSeriesPagingItems = gameSeriesPagingItems,
                onSeeAllClicked = onSeeAllClicked,
                onSimilarGameClicked = onSimilarGameClicked,
                isPreview = isPreview
            )
        },
        // set initial bottom sheet height
        sheetPeekHeight = configuration.screenHeightDp.dp - 250.dp
    ) {
        Box {
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
                    placeholder = ColorPainter(GreyPlaceholder),
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
        }
    }
}

@Composable
fun DetailInformation(
    data: DetailGame,
    gameSeriesPagingItems: LazyPagingItems<ListResultItem>,
    onSeeAllClicked: () -> Unit,
    onSimilarGameClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
    isPreview: Boolean = false,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp), color = Color.White
            )
            .padding(top = 20.dp)
    ) {
        DetailInformationHeader(
            genres = data.genres,
            imageBackground = data.imageBackground,
            name = data.name,
            rating = data.rating,
            isPreview = isPreview
        )

        DetailInformationSubHeader(
            publishers = data.publishers, released = data.released, metacritic = data.metacritic
        )

        DetailImageScreenshot(images = data.images, isPreview = isPreview)

        DetailGameOverview(
            description = data.description,
            modifier = Modifier.padding(20.dp),
        )

        DetailSimilarGames(
            gameSeriesPagingItems = gameSeriesPagingItems,
            onSeeAllClicked = onSeeAllClicked,
            onSimilarGameClicked = onSimilarGameClicked,
            isPreview = isPreview
        )
    }
}

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

@Composable
fun DetailInformationSubHeader(
    publishers: List<String>?, released: String?, metacritic: Int?, modifier: Modifier = Modifier
) {
    val publisher = publishers?.joinToString(", ")

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
                text = released?.convertDate() ?: "-",
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
                    text = (metacritic ?: 0).toString(),
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
                text = if (!publisher.isNullOrEmpty()) publisher else "-",
                fontFamily = FontFamily(Font(R.font.open_sans_semi_bold)),
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

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

@Composable
fun DetailGameOverview(description: String?, modifier: Modifier = Modifier) {
    var isShowMore by remember {
        mutableStateOf(false)
    }

    if (!description.isNullOrEmpty()) {
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
                text = description,
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

@Composable
fun DetailSimilarGames(
    gameSeriesPagingItems: LazyPagingItems<ListResultItem>,
    onSeeAllClicked: () -> Unit,
    onSimilarGameClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
    isPreview: Boolean = false
) {
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
                    GameItemHorizontal(image = "", title = "", onItemClicked = { })
                }
            }
        }
    } else {
        if (gameSeriesPagingItems.itemCount > 0) {
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

                    if (gameSeriesPagingItems.itemCount > 5) {
                        Text(
                            modifier = Modifier.clickable(
                                // remove ripple click
                                interactionSource = remember {
                                    MutableInteractionSource()
                                }, indication = null
                            ) { onSeeAllClicked() },
                            text = stringResource(id = R.string.see_all),
                            fontFamily = FontFamily(
                                Font(R.font.open_sans_semi_bold)
                            ),
                            color = Purple,
                            fontSize = 14.sp
                        )
                    }
                }

                LazyRow(
                    modifier = Modifier.padding(vertical = 14.dp),
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(count = if (gameSeriesPagingItems.itemCount >= 5) 5 else gameSeriesPagingItems.itemCount,
                        key = gameSeriesPagingItems.itemKey { data ->
                            data.id ?: 0
                        }) { index ->
                        val game = gameSeriesPagingItems[index]
                        GameItemHorizontal(image = game?.image ?: "",
                            title = game?.name ?: "",
                            onItemClicked = {
                                onSimilarGameClicked(game?.id ?: 0)
                            })
                    }
                }
            }
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
        metacritic = 93,
        publishers = listOf("CD PROJEKT RED"),
        released = "2015-05-18"
    )
    val listResultPagingItems =
        flowOf(PagingData.empty<ListResultItem>()).collectAsLazyPagingItems()

    GameXTheme {
        DetailContentState(
            uiState = UiState.Success(detail),
            gameSeriesPagingItems = listResultPagingItems,
            onImageUrl = {},
            onImageBackClick = {},
            onSeeAllClicked = {},
            onSimilarGameClicked = {},
            isPreview = true
        )
    }
}
package com.games.gamex.presentation.detail.view

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
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
import com.games.gamex.presentation.detail.viewmodel.DetailViewModel
import com.games.gamex.presentation.ui.theme.GameXTheme
import com.games.gamex.presentation.ui.theme.GreyPlaceholder
import com.games.gamex.presentation.ui.theme.Purple
import com.games.gamex.presentation.ui.theme.WhiteTransparent
import com.games.gamex.utils.PaletteGenerator.convertImageUrlToBitmap
import com.games.gamex.utils.PaletteGenerator.extractColorsFromBitmap
import com.games.gamex.utils.UiState

@Composable
fun DetailScreen(
    gameId: String,
    onColorPalette: (Map<String, String>) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    var imageUrl by remember {
        mutableStateOf("")
    }

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

    val getDetailGameState by viewModel.getDetailGame.collectAsStateWithLifecycle(initialValue = UiState.Loading)

    DetailContent(
        uiState = getDetailGameState, onImageUrl = { url -> imageUrl = url }, modifier = modifier
    )
}

@Composable
fun DetailContent(
    uiState: UiState<DetailGame>, onImageUrl: (String) -> Unit, modifier: Modifier = Modifier
) {
    when (uiState) {
        is UiState.Loading -> {}

        is UiState.Success -> {
            val data = uiState.data

            val genres = data.genres?.map {
                it.name
            }?.joinToString(", ")

            onImageUrl(data.backgroundImageAdditional ?: "")

            Box(modifier = modifier) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .align(Alignment.TopCenter),
                    model = data.backgroundImageAdditional,
                    contentDescription = "Image Background",
                    contentScale = ContentScale.Crop
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

                Card(
                    modifier = Modifier
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
                        Row(Modifier.padding(horizontal = 20.dp)) {
                            AsyncImage(
                                modifier = Modifier
                                    .size(height = 80.dp, width = 80.dp)
                                    .clip(RoundedCornerShape(10.dp)),
                                model = data.imageBackground,
                                contentDescription = "Image Game",
                                contentScale = ContentScale.Crop
                            )
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
                                    modifier = Modifier.padding(top = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        modifier = Modifier.size(20.dp),
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Image Star",
                                        tint = colorResource(
                                            id = R.color.orange
                                        )
                                    )
                                    Text(
                                        modifier = Modifier.padding(start = 2.dp),
                                        text = data.rating?.toString() ?: "",
                                        fontFamily = FontFamily(
                                            Font(R.font.open_sans_semi_bold)
                                        ),
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                        LazyRow(
                            modifier = Modifier.padding(top = 20.dp),
                            contentPadding = PaddingValues(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(data.images ?: listOf(),
                                key = { data -> data.first }) { screenshot ->
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
                        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                            Text(
                                modifier = Modifier.padding(top = 20.dp),
                                text = stringResource(id = R.string.overview),
                                fontFamily = FontFamily(
                                    Font(R.font.open_sans_bold)
                                ),
                                fontSize = 16.sp
                            )
                            ShowMoreLess(
                                modifier = Modifier.padding(top = 4.dp),
                                data = data,
                            )
                            if (!data.gameSeries.isNullOrEmpty()) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        modifier = Modifier.padding(top = 20.dp),
                                        text = stringResource(id = R.string.similar_games),
                                        fontFamily = FontFamily(
                                            Font(R.font.open_sans_bold)
                                        ),
                                        fontSize = 16.sp
                                    )
                                    if ((data.gameSeriesCount ?: 0) > 6) {
                                        Text(
                                            modifier = Modifier.padding(top = 20.dp),
                                            text = stringResource(id = R.string.see_all),
                                            fontFamily = FontFamily(
                                                Font(R.font.open_sans_semi_bold)
                                            ),
                                            color = Purple,
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }
                        }
                        LazyRow(
                            modifier = Modifier.padding(top = 10.dp),
                            contentPadding = PaddingValues(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(data.gameSeries ?: listOf(),
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

        is UiState.Error -> {}
    }
}

@Composable
fun ShowMoreLess(data: DetailGame, modifier: Modifier = Modifier) {
    var isShowMore by remember {
        mutableStateOf(false)
    }

    Column(modifier = modifier) {
        Text(
            modifier = Modifier.animateContentSize(
                // add animation expandable
                animationSpec = tween(
                    durationMillis = 300, easing = LinearOutSlowInEasing
                )
            ),
            text = data.description ?: "",
            maxLines = if (isShowMore) Int.MAX_VALUE else 3,
            overflow = if (isShowMore) TextOverflow.Clip else TextOverflow.Ellipsis,
            fontFamily = FontFamily(
                Font(R.font.open_sans_regular)
            ),
            color = colorResource(id = R.color.dark_grey),
            fontSize = 12.sp
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
            text = if (isShowMore) stringResource(id = R.string.show_less) else stringResource(id = R.string.read_more),
            fontFamily = FontFamily(
                Font(R.font.open_sans_medium)
            ),
            color = colorResource(id = R.color.purple),
            fontSize = 12.sp)
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4_XL)
@Composable
fun DetailScreenPreview() {
    GameXTheme {
        DetailScreen(gameId = "1", onColorPalette = {})
    }
}
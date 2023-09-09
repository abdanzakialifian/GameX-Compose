package com.games.gamex.presentation.detail.view

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.games.gamex.domain.model.DetailGame
import com.games.gamex.domain.model.ListResultItem
import com.games.gamex.presentation.detail.viewmodel.DetailViewModel
import com.games.gamex.presentation.ui.theme.GameXTheme
import com.games.gamex.utils.PaletteGenerator.convertImageUrlToBitmap
import com.games.gamex.utils.PaletteGenerator.extractColorsFromBitmap
import com.games.gamex.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

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

    val getDetailGamePair by viewModel.getDetailGame.collectAsStateWithLifecycle(
        initialValue = Pair(
            UiState.Loading, MutableStateFlow(PagingData.empty())
        )
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

    LaunchedEffect(key1 = Unit) {
        viewModel.setDataGameSeries(gameId, true)
    }

    DetailContent(
        uiState = getDetailGameState,
        gameSeriesPagingItems = getGameSeriesPagingItems,
        onImageUrl = { url ->
            imageUrl = url
        },
        onImageBackClick = onImageBackClick,
        modifier = modifier
    )
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
        DetailContent(
            uiState = UiState.Success(detail),
            gameSeriesPagingItems = listResultPagingItems,
            onImageUrl = {},
            onImageBackClick = { },
            isPreview = true
        )
    }
}
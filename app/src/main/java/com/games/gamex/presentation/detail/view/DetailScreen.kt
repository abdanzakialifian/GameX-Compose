package com.games.gamex.presentation.detail.view

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.games.gamex.domain.model.DetailGame
import com.games.gamex.presentation.detail.viewmodel.DetailViewModel
import com.games.gamex.presentation.ui.theme.GameXTheme
import com.games.gamex.utils.UiState

@Composable
fun DetailScreen(
    gameId: String,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel()
) {
    viewModel.setGameId(gameId)
    val getDetailGameState by viewModel.getDetailGame.collectAsStateWithLifecycle(initialValue = UiState.Loading)

    DetailContent(uiState = getDetailGameState, modifier = modifier)
}

@Composable
fun DetailContent(uiState: UiState<DetailGame>, modifier: Modifier = Modifier) {
    when (uiState) {
        is UiState.Loading -> {}
        is UiState.Success -> {
            val data = uiState.data
            AsyncImage(
                modifier = Modifier.size(100.dp),
                model = data.imageBackground,
                contentDescription = ""
            )
        }
        is UiState.Error -> {}
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4_XL)
@Composable
fun DetailScreenPreview() {
    GameXTheme {
        DetailScreen(gameId = "1")
    }
}
package com.games.gamex.presentation.gameslist.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.games.gamex.R
import com.games.gamex.domain.model.ListResultItem
import com.games.gamex.presentation.component.GamesPaging
import com.games.gamex.presentation.gameslist.viewmodel.GamesListViewModel
import com.games.gamex.presentation.ui.theme.GameXTheme
import com.games.gamex.utils.NAVIGATE_FROM_GAMES
import com.games.gamex.utils.NAVIGATE_FROM_PLATFORMS
import com.games.gamex.utils.NAVIGATE_FROM_SIMILAR_GAMES
import kotlinx.coroutines.flow.flowOf

@Composable
fun GamesListScreen(
    gameId: String,
    navigateFrom: String,
    onArrowBackClicked: () -> Unit,
    onSimilarGameClicked: (Int) -> Unit,
    viewModel: GamesListViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    val getAllGames = when (navigateFrom) {
        NAVIGATE_FROM_SIMILAR_GAMES -> viewModel.getAllGamesSeries.collectAsLazyPagingItems()
        NAVIGATE_FROM_GAMES -> viewModel.getAllGames.collectAsLazyPagingItems()
        NAVIGATE_FROM_PLATFORMS -> viewModel.getAllGamePlatforms.collectAsLazyPagingItems()
        else -> viewModel.getAllGameGenres.collectAsLazyPagingItems()
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.setGameId(gameId)
    }

    GamesListContent(
        scaffoldState = scaffoldState,
        games = getAllGames,
        navigateFrom = navigateFrom,
        onArrowBackClicked = onArrowBackClicked,
        onSimilarGameClicked = onSimilarGameClicked
    )
}

@Composable
fun GamesListContent(
    scaffoldState: ScaffoldState,
    games: LazyPagingItems<ListResultItem>,
    navigateFrom: String,
    onArrowBackClicked: () -> Unit,
    onSimilarGameClicked: (Int) -> Unit,
) {
    val title = when (navigateFrom) {
        NAVIGATE_FROM_SIMILAR_GAMES -> stringResource(id = R.string.similar_games)
        NAVIGATE_FROM_GAMES -> stringResource(id = R.string.games)
        NAVIGATE_FROM_PLATFORMS -> stringResource(id = R.string.platforms)
        else -> stringResource(id = R.string.genres)
    }
    Scaffold(
        backgroundColor = Color.White,
        topBar = {
            TopAppBar(title = {
                Text(
                    modifier = Modifier.offset(x = (-10).dp),
                    text = title,
                    fontFamily = FontFamily(
                        Font(R.font.open_sans_bold)
                    )
                )
            }, navigationIcon = {
                IconButton(onClick = onArrowBackClicked) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, contentDescription = "Icon Back"
                    )
                }
            }, backgroundColor = Color.White, elevation = 0.dp
            )
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(paddingValues)
        ) {
            GamesPaging(
                gamesPagingItems = games,
                scaffoldState = scaffoldState,
                onGamePagingItemsClicked = onSimilarGameClicked
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4_XL)
@Composable
fun GameListScreenPreview() {
    GameXTheme {
        val scaffoldState = rememberScaffoldState()
        val listResultPagingItems =
            flowOf(PagingData.empty<ListResultItem>()).collectAsLazyPagingItems()

        GamesListContent(
            scaffoldState = scaffoldState,
            games = listResultPagingItems,
            navigateFrom = NAVIGATE_FROM_SIMILAR_GAMES,
            onArrowBackClicked = {},
            onSimilarGameClicked = {}
        )
    }
}
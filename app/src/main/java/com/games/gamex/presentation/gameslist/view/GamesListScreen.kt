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
import kotlinx.coroutines.flow.flowOf

@Composable
fun GamesListScreen(
    gameId: String,
    isPaging: Boolean,
    onArrowBackClicked: () -> Unit,
    onSimilarGameClicked: (Int) -> Unit,
    viewModel: GamesListViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    val getAllGamesSeries = viewModel.getAllGamesSeries.collectAsLazyPagingItems()

    LaunchedEffect(key1 = Unit) {
        viewModel.setGameId(gameId)
        viewModel.setIsPaging(isPaging)
    }

    GamesListContent(
        scaffoldState = scaffoldState,
        gamesSeries = getAllGamesSeries,
        onArrowBackClicked = onArrowBackClicked,
        onSimilarGameClicked = onSimilarGameClicked
    )
}

@Composable
fun GamesListContent(
    scaffoldState: ScaffoldState,
    gamesSeries: LazyPagingItems<ListResultItem>,
    onArrowBackClicked: () -> Unit,
    onSimilarGameClicked: (Int) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.offset(x = (-10).dp),
                        text = "Similar Games",
                        fontFamily = FontFamily(
                            Font(R.font.open_sans_bold)
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onArrowBackClicked) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Icon Back"
                        )
                    }
                },
                backgroundColor = Color.White,
                elevation = 0.dp
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(paddingValues)
        ) {
            GamesPaging(
                gamesPagingItems = gamesSeries,
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
            gamesSeries = listResultPagingItems,
            onArrowBackClicked = { },
            onSimilarGameClicked = {}
        )
    }
}
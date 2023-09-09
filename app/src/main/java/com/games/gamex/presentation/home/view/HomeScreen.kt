package com.games.gamex.presentation.home.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.games.gamex.domain.model.ListResultItem
import com.games.gamex.presentation.home.viewmodel.HomeViewModel
import com.games.gamex.presentation.ui.theme.GameXTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeScreen(
    onGenreClicked: () -> Unit,
    onGameHorizontalClicked: (gameId: Int) -> Unit,
    onPlatformClicked: () -> Unit,
    onGameVerticalClicked: (gameId: Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {

    var searchQuery by rememberSaveable {
        mutableStateOf("")
    }

    val getAllGamesHorizontalState = viewModel.getAllGames.collectAsLazyPagingItems()
    val getAllGameGenresState = viewModel.getAllGameGenres.collectAsLazyPagingItems()
    val getAllGamePlatformsState = viewModel.getAllGamePlatforms.collectAsLazyPagingItems()
    val getAllGamesVerticalState = if (searchQuery.isNotEmpty()) {
        viewModel.searchAllGames.collectAsLazyPagingItems()
    } else {
        viewModel.emptyFlow.collectAsLazyPagingItems()
    }

    HomeContent(
        genresPaging = getAllGameGenresState,
        gamesHorizontalPaging = getAllGamesHorizontalState,
        platformsPaging = getAllGamePlatformsState,
        gamesVerticalPaging = getAllGamesVerticalState,
        searchQuery = searchQuery,
        onValueChange = { value ->
            searchQuery = value
            viewModel.setSearchQuery(searchQuery = value)
        },
        onGenreClicked = onGenreClicked,
        onGameHorizontalClicked = onGameHorizontalClicked,
        onPlatformClicked = onPlatformClicked,
        onGameVerticalClicked = onGameVerticalClicked,
        modifier = modifier,
    )
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4_XL)
@Composable
fun HomeScreenPreview() {
    GameXTheme {
        val listResultPagingItems =
            flowOf(PagingData.empty<ListResultItem>()).collectAsLazyPagingItems()
        HomeContent(
            genresPaging = listResultPagingItems,
            gamesHorizontalPaging = listResultPagingItems,
            platformsPaging = listResultPagingItems,
            gamesVerticalPaging = listResultPagingItems,
            searchQuery = "",
            onValueChange = {},
            onGenreClicked = { },
            onGameHorizontalClicked = {},
            onPlatformClicked = { },
            onGameVerticalClicked = { }
        )
    }
}
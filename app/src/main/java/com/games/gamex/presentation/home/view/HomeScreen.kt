package com.games.gamex.presentation.home.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.games.gamex.R
import com.games.gamex.domain.model.ListResultItem
import com.games.gamex.presentation.component.CustomSearch
import com.games.gamex.presentation.component.GamesPaging
import com.games.gamex.presentation.home.viewmodel.HomeViewModel
import com.games.gamex.presentation.ui.theme.GameXTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeScreen(
    onGenreClicked: (genreId: Int) -> Unit,
    onGameClicked: (gameId: Int) -> Unit,
    onPlatformClicked: (Int) -> Unit,
    onGamePagingItemsClicked: (gameId: Int) -> Unit,
    onSeeAllGamesClicked: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {

    var searchQuery by rememberSaveable {
        mutableStateOf("")
    }

    val getAllGamesHorizontalPagingItems = viewModel.getAllGames.collectAsLazyPagingItems()
    val getAllGameGenresPagingItems = viewModel.getAllGameGenres.collectAsLazyPagingItems()
    val getAllGamePlatformsPagingItems = viewModel.getAllGamePlatforms.collectAsLazyPagingItems()
    val getAllSearchGamesPagingItems = if (searchQuery.isNotEmpty()) {
        viewModel.searchAllGames.collectAsLazyPagingItems()
    } else {
        viewModel.emptyFlow.collectAsLazyPagingItems()
    }

    HomeContent(
        genresPagingItems = getAllGameGenresPagingItems,
        gamesHorizontalPagingItems = getAllGamesHorizontalPagingItems,
        platformsPagingItems = getAllGamePlatformsPagingItems,
        searchGamePagingItems = getAllSearchGamesPagingItems,
        searchQuery = searchQuery,
        onValueChange = { value ->
            searchQuery = value
            viewModel.setSearchQuery(searchQuery = value)
        },
        onGenreClicked = onGenreClicked,
        onGameClicked = onGameClicked,
        onPlatformClicked = onPlatformClicked,
        onGamePagingItemsClicked = onGamePagingItemsClicked,
        onSeeAllGamesClicked = onSeeAllGamesClicked,
        modifier = modifier,
    )
}

@Composable
fun HomeContent(
    genresPagingItems: LazyPagingItems<ListResultItem>,
    gamesHorizontalPagingItems: LazyPagingItems<ListResultItem>,
    platformsPagingItems: LazyPagingItems<ListResultItem>,
    searchGamePagingItems: LazyPagingItems<ListResultItem>,
    searchQuery: String,
    onValueChange: (String) -> Unit,
    onGenreClicked: (genreId: Int) -> Unit,
    onGameClicked: (gameId: Int) -> Unit,
    onPlatformClicked: (Int) -> Unit,
    onGamePagingItemsClicked: (gameId: Int) -> Unit,
    onSeeAllGamesClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isErrorCategories by remember {
        mutableStateOf(false)
    }
    var isErrorHorizontalGames by remember {
        mutableStateOf(false)
    }
    var isErrorPlatforms by remember {
        mutableStateOf(false)
    }
    val scaffoldState = rememberScaffoldState()

    Scaffold(scaffoldState = scaffoldState) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 60.dp)) {
                Text(
                    text = stringResource(id = R.string.welcome),
                    color = Color.White,
                    fontFamily = FontFamily(
                        Font(resId = R.font.open_sans_bold)
                    ),
                    fontSize = 24.sp
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = stringResource(id = R.string.welcome_sub_title),
                    color = Color.White,
                    fontFamily = FontFamily(
                        Font(resId = R.font.open_sans_medium)
                    ),
                    fontSize = 16.sp
                )
                CustomSearch(
                    modifier = Modifier.padding(top = 30.dp),
                    value = searchQuery,
                    hint = stringResource(id = R.string.search_game),
                    onValueChange = { value ->
                        onValueChange(value)
                    }
                )
            }
            Card(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
                backgroundColor = Color.White
            ) {
                if (searchQuery.isEmpty()) {
                    if (isErrorCategories && isErrorHorizontalGames && isErrorPlatforms) {
                        HomeErrorSection()
                    } else {
                        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                            CategoriesContent(
                                genresPaging = genresPagingItems,
                                scaffoldState = scaffoldState,
                                onGenreClicked = onGenreClicked,
                                onFetchError = { isError ->
                                    isErrorCategories = isError
                                },
                                modifier = Modifier.padding(top = 20.dp)
                            )
                            GamesHorizontalContent(
                                gamesHorizontalPaging = gamesHorizontalPagingItems,
                                scaffoldState = scaffoldState,
                                onGameClicked =  onGameClicked,
                                onSeeAllGamesClicked = onSeeAllGamesClicked,
                                onFetchError = { isError ->
                                    isErrorHorizontalGames = isError
                                },
                                modifier = Modifier.padding(top = 20.dp)
                            )
                            PlatformsContent(
                                platformsPaging = platformsPagingItems,
                                scaffoldState = scaffoldState,
                                onPlatformClicked = onPlatformClicked,
                                onFetchError = { isError ->
                                    isErrorPlatforms = isError
                                },
                                modifier = Modifier.padding(top = 20.dp)
                            )
                        }
                    }
                } else {
                    GamesPaging(
                        gamesPagingItems = searchGamePagingItems,
                        scaffoldState = scaffoldState,
                        onGamePagingItemsClicked = onGamePagingItemsClicked,
                    )
                }
            }
        }
    }
}

@Composable
fun HomeErrorSection() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error_animation))
    val progress by animateLottieCompositionAsState(
        composition = composition, speed = 2F, restartOnPlay = true
    )

    Box(modifier = Modifier.fillMaxSize()) {
        LottieAnimation(
            modifier = Modifier
                .size(250.dp)
                .align(Alignment.Center),
            composition = composition,
            progress = progress
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4_XL)
@Composable
fun HomeScreenPreview() {
    GameXTheme {
        val listResultPagingItems =
            flowOf(PagingData.empty<ListResultItem>()).collectAsLazyPagingItems()
        HomeContent(
            genresPagingItems = listResultPagingItems,
            gamesHorizontalPagingItems = listResultPagingItems,
            platformsPagingItems = listResultPagingItems,
            searchGamePagingItems = listResultPagingItems,
            searchQuery = "",
            onValueChange = {},
            onGenreClicked = {},
            onGameClicked = {},
            onPlatformClicked = {},
            onGamePagingItemsClicked = {},
            onSeeAllGamesClicked = {}
        )
    }
}
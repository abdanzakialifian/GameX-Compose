package com.games.gamex.presentation.home.view

import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.games.gamex.R
import com.games.gamex.domain.model.ListResultItem
import com.games.gamex.presentation.component.CategoriesItem
import com.games.gamex.presentation.component.CustomSearch
import com.games.gamex.presentation.component.GameItem
import com.games.gamex.presentation.component.GameItemSecond
import com.games.gamex.presentation.component.PlatformItem
import com.games.gamex.presentation.component.ShimmerAnimation
import com.games.gamex.presentation.home.viewmodel.HomeViewModel
import com.games.gamex.presentation.ui.theme.GameXTheme
import com.games.gamex.utils.Shimmer
import com.games.gamex.utils.isScrollToEnd
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    onAllGenresClicked: () -> Unit,
    onAllGamesClicked: (gameId: Int) -> Unit,
    onAllPlatformsClicked: () -> Unit,
    onSearchAllGamesClicked: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel()
) {

    val getAllGamesState = viewModel.getAllGames.collectAsLazyPagingItems()
    val getAllGenresState = viewModel.getAllGenres.collectAsLazyPagingItems()
    val getAllPlatformsState = viewModel.getAllPlatforms.collectAsLazyPagingItems()
    val searchAllGamesState = viewModel.searchAllGames.collectAsLazyPagingItems()

    HomeContent(
        onValueChange = { viewModel.setSearchQuery(searchQuery = it) },
        allGames = getAllGamesState,
        allGenres = getAllGenresState,
        allPlatforms = getAllPlatformsState,
        searchAllGames = searchAllGamesState,
        modifier = modifier,
        onAllGenresClicked = onAllGenresClicked,
        onAllGamesClicked = onAllGamesClicked,
        onAllPlatformsClicked = onAllPlatformsClicked,
        onSearchAllGamesClicked = onSearchAllGamesClicked
    )
}

@Composable
fun HomeContent(
    onValueChange: (String) -> Unit,
    onAllGenresClicked: () -> Unit,
    onAllGamesClicked: (gameId: Int) -> Unit,
    onAllPlatformsClicked: () -> Unit,
    onSearchAllGamesClicked: () -> Unit,
    allGenres: LazyPagingItems<ListResultItem>,
    allGames: LazyPagingItems<ListResultItem>,
    allPlatforms: LazyPagingItems<ListResultItem>,
    searchAllGames: LazyPagingItems<ListResultItem>,
    modifier: Modifier = Modifier
) {
    var searchValue by remember {
        mutableStateOf("")
    }

    var isErrorCategories by remember {
        mutableStateOf(false)
    }

    var isErrorAllGames by remember {
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
                CustomSearch(modifier = Modifier.padding(top = 30.dp),
                    value = searchValue,
                    hint = stringResource(id = R.string.search_game),
                    onValueChange = {
                        searchValue = it
                        onValueChange(it)
                    })
            }
            Card(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
                backgroundColor = Color.White
            ) {
                if (searchValue.isEmpty()) {
                    if (isErrorCategories && isErrorAllGames && isErrorPlatforms) {
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
                    } else {
                        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                            CategoriesContent(
                                onAllGenresClicked = onAllGenresClicked,
                                allGenres = allGenres,
                                onError = { isErrorCategories = it },
                                scaffoldState = scaffoldState
                            )
                            AllGamesHorizontalContent(
                                onAllGamesClicked = onAllGamesClicked,
                                allGames = allGames,
                                onError = { isErrorAllGames = it },
                                scaffoldState = scaffoldState
                            )
                            PlatformsContent(
                                onAllPlatformsClicked = onAllPlatformsClicked,
                                allPlatforms = allPlatforms,
                                onError = { isErrorPlatforms = it },
                                scaffoldState = scaffoldState
                            )
                        }
                    }
                } else {
                    AllGamesVerticalContent(
                        onSearchAllGamesClicked = onSearchAllGamesClicked,
                        searchAllGames = searchAllGames,
                        scaffoldState = scaffoldState
                    )
                }
            }
        }
    }
}

@Composable
fun CategoriesContent(
    onAllGenresClicked: () -> Unit,
    onError: (Boolean) -> Unit,
    allGenres: LazyPagingItems<ListResultItem>,
    scaffoldState: ScaffoldState
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()

    // observer when reached end of list
    val endOfListReached by remember {
        derivedStateOf {
            scrollState.isScrollToEnd()
        }
    }
    // act when end of list reached
    LaunchedEffect(endOfListReached) {
        allGenres.retry()
    }

    // handle error data while load more
    if (allGenres.loadState.append is LoadState.Error) {
        LaunchedEffect(Unit) {
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(message = (allGenres.loadState.append as LoadState.Error).error.message.toString())
            }
        }
    }

    // initial load
    when (allGenres.loadState.refresh) {
        is LoadState.Loading -> {
            Text(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
                text = stringResource(id = R.string.categories),
                color = Color.Black,
                fontFamily = FontFamily(Font(resId = R.font.open_sans_bold)),
                fontSize = 18.sp
            )

            Row(
                modifier = Modifier
                    .horizontalScroll(
                        rememberScrollState()
                    )
                    .padding(paddingValues = PaddingValues(horizontal = 20.dp)),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                repeat(times = 10) {
                    ShimmerAnimation(shimmer = Shimmer.CATEGORIES_ITEM_PLACEHOLDER)
                }
            }
        }
        is LoadState.NotLoading -> {
            Text(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
                text = stringResource(id = R.string.categories),
                color = Color.Black,
                fontFamily = FontFamily(Font(resId = R.font.open_sans_bold)),
                fontSize = 18.sp
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                state = scrollState
            ) {
                items(allGenres, key = { it.id ?: 0 }) {
                    CategoriesItem(
                        category = it?.name ?: "",
                        image = it?.image ?: "",
                        onItemClicked = onAllGenresClicked
                    )
                }

                // load more (pagination)
                if (allGenres.loadState.append == LoadState.Loading) {
                    item {
                        ShimmerAnimation(shimmer = Shimmer.CATEGORIES_ITEM_PLACEHOLDER)
                    }
                }
            }
        }
        is LoadState.Error -> onError(true)
    }
}

@Composable
fun AllGamesHorizontalContent(
    onAllGamesClicked: (gameId: Int) -> Unit,
    allGames: LazyPagingItems<ListResultItem>,
    onError: (Boolean) -> Unit,
    scaffoldState: ScaffoldState
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()

    // observer when reached end of list
    val endOfListReached by remember {
        derivedStateOf {
            scrollState.isScrollToEnd()
        }
    }
    // act when end of list reached
    LaunchedEffect(endOfListReached) {
        allGames.retry()
    }

    // handle error data while load more
    if (allGames.loadState.append is LoadState.Error) {
        LaunchedEffect(Unit) {
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(message = (allGames.loadState.append as LoadState.Error).error.message.toString())
            }
        }
    }

    // initial load
    when (allGames.loadState.refresh) {
        is LoadState.Loading -> {
            Text(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
                text = stringResource(id = R.string.all_games),
                color = Color.Black,
                fontFamily = FontFamily(Font(resId = R.font.open_sans_bold)),
                fontSize = 18.sp
            )
            Row(
                modifier = Modifier
                    .horizontalScroll(
                        rememberScrollState()
                    )
                    .padding(paddingValues = PaddingValues(horizontal = 20.dp)),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                repeat(times = 10) {
                    ShimmerAnimation(Shimmer.GAME_ITEM_PLACEHOLDER)
                }
            }
        }
        is LoadState.NotLoading -> {
            Text(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
                text = stringResource(id = R.string.all_games),
                color = Color.Black,
                fontFamily = FontFamily(Font(resId = R.font.open_sans_bold)),
                fontSize = 18.sp
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                state = scrollState
            ) {
                items(items = allGames, key = { it.id ?: 0 }) {
                    GameItem(
                        image = it?.image ?: "",
                        title = it?.name ?: "",
                        onItemClicked = { onAllGamesClicked(it?.id ?: 0) },
                    )
                }

                // load more (pagination)
                if (allGames.loadState.append == LoadState.Loading) {
                    item {
                        Column(
                            modifier = Modifier
                                .height(190.dp)
                                .padding(horizontal = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(30.dp), color = colorResource(
                                    id = R.color.dark_grey
                                )
                            )
                        }
                    }
                }
            }
        }
        is LoadState.Error -> onError(true)
    }
}

@Composable
fun PlatformsContent(
    onAllPlatformsClicked: () -> Unit,
    allPlatforms: LazyPagingItems<ListResultItem>,
    onError: (Boolean) -> Unit,
    scaffoldState: ScaffoldState
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()

    // observer when reached end of list
    val endOfListReached by remember {
        derivedStateOf {
            scrollState.isScrollToEnd()
        }
    }
    // act when end of list reached
    LaunchedEffect(endOfListReached) {
        allPlatforms.retry()
    }

    // handle error data while load more
    if (allPlatforms.loadState.append is LoadState.Error) {
        LaunchedEffect(Unit) {
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(message = (allPlatforms.loadState.append as LoadState.Error).error.message.toString())
            }
        }
    }

    // initial load
    when (allPlatforms.loadState.refresh) {
        is LoadState.Loading -> {
            Text(
                modifier = Modifier.padding(vertical = 20.dp, horizontal = 20.dp),
                text = stringResource(id = R.string.platforms),
                color = Color.Black,
                fontFamily = FontFamily(Font(resId = R.font.open_sans_bold)),
                fontSize = 18.sp
            )
            Row(
                modifier = Modifier
                    .horizontalScroll(
                        rememberScrollState()
                    )
                    .padding(paddingValues = PaddingValues(horizontal = 20.dp)),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                repeat(times = 10) {
                    ShimmerAnimation(shimmer = Shimmer.PLATFORM_ITEM_PLACEHOLDER)
                }
            }
        }
        is LoadState.NotLoading -> {
            Text(
                modifier = Modifier.padding(vertical = 20.dp, horizontal = 20.dp),
                text = stringResource(id = R.string.platforms),
                color = Color.Black,
                fontFamily = FontFamily(Font(resId = R.font.open_sans_bold)),
                fontSize = 18.sp
            )
            LazyRow(
                modifier = Modifier.padding(bottom = 40.dp),
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                state = scrollState
            ) {
                items(allPlatforms, key = { it.id ?: 0 }) {
                    PlatformItem(
                        image = it?.image ?: "",
                        name = it?.name ?: "",
                        totalGames = it?.gamesCount ?: 0,
                        onItemClicked = onAllPlatformsClicked
                    )
                }

                // load more (pagination)
                if (allPlatforms.loadState.append == LoadState.Loading) {
                    item {
                        Column(
                            modifier = Modifier
                                .height(210.dp)
                                .padding(horizontal = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(30.dp), color = colorResource(
                                    id = R.color.dark_grey
                                )
                            )
                        }
                    }
                }
            }
        }
        is LoadState.Error -> onError(true)
    }
}

@Composable
fun AllGamesVerticalContent(
    onSearchAllGamesClicked: () -> Unit,
    searchAllGames: LazyPagingItems<ListResultItem>,
    scaffoldState: ScaffoldState
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()

    // observer when reached end of list
    val endOfListReached by remember {
        derivedStateOf {
            scrollState.isScrollToEnd()
        }
    }
    // act when end of list reached
    LaunchedEffect(endOfListReached) {
        searchAllGames.retry()
    }

    // handle error data while load more
    if (searchAllGames.loadState.append is LoadState.Error) {
        LaunchedEffect(Unit) {
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(message = (searchAllGames.loadState.append as LoadState.Error).error.message.toString())
            }
        }
    }

    // initial load
    when (searchAllGames.loadState.refresh) {
        is LoadState.Loading -> Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(paddingValues = PaddingValues(vertical = 20.dp))
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            repeat(times = 15) {
                ShimmerAnimation(shimmer = Shimmer.GAME_ITEM_SECOND_PLACEHOLDER)
            }
        }
        is LoadState.NotLoading -> {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 20.dp),
                contentPadding = PaddingValues(vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                state = scrollState
            ) {
                items(searchAllGames, key = { it.id ?: 0 }) {
                    GameItemSecond(
                        image = it?.image ?: "",
                        name = it?.name ?: "",
                        date = it?.released ?: "",
                        rating = it?.rating ?: 0.0F,
                        onItemClicked = onSearchAllGamesClicked
                    )
                }

                // load more (pagination)
                if (searchAllGames.loadState.append == LoadState.Loading) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(30.dp), color = colorResource(
                                    id = R.color.dark_grey
                                )
                            )
                        }
                    }
                }
            }
        }
        is LoadState.Error -> {}
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4_XL)
@Composable
fun HomeScreenPreview() {
    GameXTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background,
        ) {
            HomeScreen(
                onAllGenresClicked = {},
                onAllGamesClicked = {},
                onAllPlatformsClicked = {},
                onSearchAllGamesClicked = {},
            )
        }
    }
}
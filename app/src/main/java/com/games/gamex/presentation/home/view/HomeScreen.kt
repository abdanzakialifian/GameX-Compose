package com.games.gamex.presentation.home.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import com.games.gamex.R
import com.games.gamex.domain.model.GamesResultItem
import com.games.gamex.domain.model.GenresResultItem
import com.games.gamex.domain.model.PlatformsResultItem
import com.games.gamex.presentation.component.*
import com.games.gamex.presentation.home.viewmodel.HomeViewModel
import com.games.gamex.presentation.ui.theme.GameXTheme
import com.games.gamex.utils.Shimmer

@Composable
fun HomeScreen(
    onAllGenresClicked: () -> Unit,
    onAllGamesClicked: () -> Unit,
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
    onAllGamesClicked: () -> Unit,
    onAllPlatformsClicked: () -> Unit,
    onSearchAllGamesClicked: () -> Unit,
    allGenres: LazyPagingItems<GenresResultItem>,
    allGames: LazyPagingItems<GamesResultItem>,
    allPlatforms: LazyPagingItems<PlatformsResultItem>,
    searchAllGames: LazyPagingItems<GamesResultItem>,
    modifier: Modifier = Modifier
) {
    var searchValue by remember {
        mutableStateOf("")
    }

    Column(modifier = modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.padding(
                start = 20.dp, top = 20.dp, end = 20.dp, bottom = 4.dp
            ),
            text = stringResource(id = R.string.welcome),
            color = Color.White,
            fontFamily = FontFamily(
                Font(resId = R.font.open_sans_bold)
            ),
            fontSize = 24.sp
        )
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = stringResource(id = R.string.welcome_sub_title),
            color = Color.White,
            fontFamily = FontFamily(
                Font(resId = R.font.open_sans_medium)
            ),
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(30.dp))
        CustomSearch(
            modifier = Modifier.padding(horizontal = 20.dp),
            value = searchValue,
            hint = stringResource(id = R.string.search_game),
            onValueChange = {
                searchValue = it
                onValueChange(it)
            }
        )
        Spacer(modifier = Modifier.height(50.dp))
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
            backgroundColor = Color.White
        ) {
            Column(modifier = if (searchValue.isEmpty()) Modifier.verticalScroll(rememberScrollState()) else Modifier.fillMaxHeight()) {
                if (searchValue.isEmpty()) {
                    Categories(onAllGenresClicked = onAllGenresClicked, allGenres = allGenres)
                    AllGamesHorizontal(onAllGamesClicked = onAllGamesClicked, allGames = allGames)
                    Platforms(
                        onAllPlatformsClicked = onAllPlatformsClicked,
                        allPlatforms = allPlatforms
                    )
                } else {
                    AllGamesVertical(
                        onSearchAllGamesClicked = onSearchAllGamesClicked,
                        searchAllGames = searchAllGames
                    )
                }
            }
        }
    }
}

@Composable
fun Categories(onAllGenresClicked: () -> Unit, allGenres: LazyPagingItems<GenresResultItem>) {
    Text(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
        text = stringResource(id = R.string.categories),
        color = Color.Black,
        fontFamily = FontFamily(Font(resId = R.font.open_sans_bold)),
        fontSize = 18.sp
    )
    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // initial load
        when (allGenres.loadState.refresh) {
            is LoadState.Loading -> items(count = 10) {
                ShimmerAnimation(shimmer = Shimmer.CATEGORIES_ITEM_PLACEHOLDER)
            }
            is LoadState.Error -> {}
            else -> items(allGenres, key = { it.id ?: 0 }) {
                CategoriesItem(
                    category = it?.name ?: "",
                    image = it?.image ?: "",
                    onItemClicked = onAllGenresClicked
                )
            }
        }

        // load more (pagination)
        when (allGenres.loadState.append) {
            is LoadState.Loading -> item {
                ShimmerAnimation(shimmer = Shimmer.CATEGORIES_ITEM_PLACEHOLDER)
            }
            is LoadState.Error -> {}
            else -> {}
        }
    }
}

@Composable
fun AllGamesHorizontal(onAllGamesClicked: () -> Unit, allGames: LazyPagingItems<GamesResultItem>) {
    Text(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
        text = stringResource(id = R.string.all_games),
        color = Color.Black,
        fontFamily = FontFamily(Font(resId = R.font.open_sans_bold)),
        fontSize = 18.sp
    )
    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // initial load
        when (allGames.loadState.refresh) {
            is LoadState.Loading -> items(count = 10) {
                ShimmerAnimation(Shimmer.GAME_ITEM_PLACEHOLDER)
            }
            is LoadState.Error -> {}
            else -> items(items = allGames, key = { it.id ?: 0 }) {
                GameItem(
                    image = it?.image ?: "",
                    title = it?.name ?: "",
                    onItemClicked = onAllGamesClicked,
                )
            }
        }

        // load more (pagination)
        when (allGames.loadState.append) {
            is LoadState.Loading -> item {
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
            is LoadState.Error -> {}
            else -> {}
        }
    }
}

@Composable
fun Platforms(
    onAllPlatformsClicked: () -> Unit,
    allPlatforms: LazyPagingItems<PlatformsResultItem>
) {
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
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // initial load
        when (allPlatforms.loadState.refresh) {
            is LoadState.Loading -> items(count = 10) {
                ShimmerAnimation(shimmer = Shimmer.PLATFORM_ITEM_PLACEHOLDER)
            }
            is LoadState.Error -> {}
            else -> items(allPlatforms, key = { it.id ?: 0 }) {
                PlatformItem(
                    image = it?.image ?: "",
                    name = it?.name ?: "",
                    totalGames = it?.gamesCount ?: 0,
                    onItemClicked = onAllPlatformsClicked
                )
            }
        }

        // load more (pagination)
        when (allPlatforms.loadState.append) {
            is LoadState.Loading -> item {
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
            is LoadState.Error -> {}
            else -> {}
        }
    }
}

@Composable
fun AllGamesVertical(
    onSearchAllGamesClicked: () -> Unit,
    searchAllGames: LazyPagingItems<GamesResultItem>
) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 20.dp),
        contentPadding = PaddingValues(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // initial load
        when (searchAllGames.loadState.refresh) {
            is LoadState.Loading -> items(15) {
                ShimmerAnimation(shimmer = Shimmer.GAME_ITEM_SECOND_PLACEHOLDER)
            }
            is LoadState.Error -> {}
            else -> items(searchAllGames, key = { it.id ?: 0 }) {
                GameItemSecond(
                    image = it?.image ?: "",
                    name = it?.name ?: "",
                    date = it?.released ?: "",
                    rating = it?.rating ?: 0.0F,
                    onItemClicked = onSearchAllGamesClicked
                )
            }
        }

        // load more (pagination)
        when (searchAllGames.loadState.append) {
            is LoadState.Loading -> item {
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
            is LoadState.Error -> {}
            else -> {}
        }
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
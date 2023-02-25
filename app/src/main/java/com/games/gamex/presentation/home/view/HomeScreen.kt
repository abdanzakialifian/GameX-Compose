package com.games.gamex.presentation.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import com.games.gamex.presentation.component.CategoriesItem
import com.games.gamex.presentation.component.CustomSearch
import com.games.gamex.presentation.component.GameItem
import com.games.gamex.presentation.component.ShimmerAnimation
import com.games.gamex.presentation.home.viewmodel.HomeViewModel
import com.games.gamex.presentation.ui.theme.GameXTheme
import com.games.gamex.utils.Shimmer

@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel = viewModel()) {

    val getAllGenresState = viewModel.getAllGenres().collectAsLazyPagingItems()
    val getAllGamesState = viewModel.getAllGames().collectAsLazyPagingItems()

    HomeContent(allGames = getAllGamesState, allGenres = getAllGenresState, modifier = modifier)
}

@Composable
fun HomeContent(
    allGames: LazyPagingItems<GamesResultItem>,
    allGenres: LazyPagingItems<GenresResultItem>,
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
        CustomSearch(modifier = Modifier.padding(horizontal = 20.dp),
            value = searchValue,
            hint = stringResource(id = R.string.search_game),
            onValueChange = { searchValue = it })
        Spacer(modifier = Modifier.height(50.dp))
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                )
        ) {
            Column {
                Text(
                    modifier = Modifier.padding(
                        start = 20.dp, top = 20.dp, end = 20.dp, bottom = 10.dp
                    ),
                    text = stringResource(id = R.string.categories),
                    color = Color.Black,
                    fontFamily = FontFamily(Font(resId = R.font.open_sans_bold)),
                    fontSize = 18.sp
                )
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(allGenres, key = { it.id ?: 0 }) {
                        CategoriesItem(category = it?.name ?: "", onNavigate = {})
                    }
                }
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
                            ShimmerAnimation(Shimmer.GAME_ITEM_SHIMMER)
                        }
                        is LoadState.NotLoading -> {
                            items(items = allGames, key = { it.id ?: 0 }) {
                                GameItem(image = it?.image ?: "",
                                    title = it?.name ?: "",
                                    onNavigate = { })
                            }
                        }
                        is LoadState.Error -> {}
                    }

                    // load more (pagination)
                    when (allGames.loadState.append) {
                        is LoadState.Loading -> {
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
                        is LoadState.NotLoading -> {
                            items(items = allGames, key = { it.id ?: 0 }) {
                                GameItem(image = it?.image ?: "",
                                    title = it?.name ?: "",
                                    onNavigate = { })
                            }
                        }
                        is LoadState.Error -> {}
                    }
                }
            }
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
            HomeScreen()
        }
    }
}
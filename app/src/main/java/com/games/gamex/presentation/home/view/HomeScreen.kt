package com.games.gamex.presentation.home.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.games.gamex.presentation.component.GameItem
import com.games.gamex.presentation.ui.theme.GameXTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.games.gamex.data.source.remote.response.GamesResultItem
import com.games.gamex.presentation.home.viewmodel.HomeViewModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel = viewModel()) {
    val getAllGamesState = viewModel.getAllGames().collectAsLazyPagingItems()
    HomeContent(modifier = modifier, allGames = getAllGamesState)
}

@Composable
fun HomeContent(modifier: Modifier = Modifier, allGames: LazyPagingItems<GamesResultItem>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items = allGames, key = { it.id ?: 0 }) {
            GameItem(
                image = it?.backgroundImage ?: "",
                title = it?.name ?: "",
                onNavigate = { }
            )
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
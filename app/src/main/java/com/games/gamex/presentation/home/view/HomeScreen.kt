package com.games.gamex.presentation.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.games.gamex.R
import com.games.gamex.data.source.remote.response.GamesResultItem
import com.games.gamex.presentation.component.GameItem
import com.games.gamex.presentation.home.viewmodel.HomeViewModel
import com.games.gamex.presentation.ui.theme.GameXTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel = viewModel()) {
    val getAllGamesState = viewModel.getAllGames().collectAsLazyPagingItems()
    HomeContent(modifier = modifier, allGames = getAllGamesState)
}

@Composable
fun HomeContent(modifier: Modifier = Modifier, allGames: LazyPagingItems<GamesResultItem>) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.padding(
                start = 20.dp,
                top = 20.dp,
                end = 20.dp,
                bottom = 4.dp
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
        Spacer(modifier = Modifier.height(100.dp))
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                )
        ) {
            LazyRow(
                modifier = Modifier.padding(top = 10.dp),
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
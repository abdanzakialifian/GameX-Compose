package com.games.gamex.presentation.home.view

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.games.gamex.R
import com.games.gamex.domain.model.ListResultItem
import com.games.gamex.presentation.component.GameItemHorizontal
import com.games.gamex.presentation.component.ShimmerAnimation
import com.games.gamex.utils.Shimmer
import com.games.gamex.utils.isScrollToEnd
import kotlinx.coroutines.launch

@Composable
fun GamesHorizontalContent(
    gamesHorizontalPaging: LazyPagingItems<ListResultItem>,
    scaffoldState: ScaffoldState,
    onGameHorizontalClicked: (gameId: Int) -> Unit,
    onFetchError: (Boolean) -> Unit,
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
        gamesHorizontalPaging.retry()
    }

    // handle error data while load more
    if (gamesHorizontalPaging.loadState.append is LoadState.Error) {
        LaunchedEffect(Unit) {
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(message = (gamesHorizontalPaging.loadState.append as LoadState.Error).error.message.toString())
            }
        }
    }

    // initial load
    when (gamesHorizontalPaging.loadState.refresh) {
        is LoadState.Loading -> GamesHorizontalSectionPlaceholder()


        is LoadState.NotLoading -> GamesHorizontalSection(
            scrollState = scrollState,
            gamesHorizontalPaging = gamesHorizontalPaging,
            onGameHorizontalClicked = onGameHorizontalClicked
        )

        is LoadState.Error -> onFetchError(true)
    }
}

@Composable
fun GamesHorizontalSection(
    scrollState: LazyListState,
    gamesHorizontalPaging: LazyPagingItems<ListResultItem>,
    onGameHorizontalClicked: (gameId: Int) -> Unit
) {
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
        items(items = gamesHorizontalPaging, key = { data -> data.id ?: 0 }) { game ->
            GameItemHorizontal(
                image = game?.image ?: "",
                title = game?.name ?: "",
                onItemClicked = { onGameHorizontalClicked(game?.id ?: 0) },
            )
        }

        // load more (pagination)
        if (gamesHorizontalPaging.loadState.append == LoadState.Loading) {
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

@Composable
fun GamesHorizontalSectionPlaceholder() {
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
package com.games.gamex.presentation.home.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.games.gamex.R
import com.games.gamex.domain.model.ListResultItem
import com.games.gamex.presentation.component.GameItemVertical
import com.games.gamex.presentation.component.ShimmerAnimation
import com.games.gamex.presentation.ui.theme.GameXTheme
import com.games.gamex.utils.Shimmer
import com.games.gamex.utils.isScrollToEnd
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@Composable
fun GamesVerticalContent(
    gamesVerticalPaging: LazyPagingItems<ListResultItem>,
    scaffoldState: ScaffoldState,
    onGameVerticalClicked: (gameId: Int) -> Unit,
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
        gamesVerticalPaging.retry()
    }

    // handle error data while load more
    if (gamesVerticalPaging.loadState.append is LoadState.Error) {
        LaunchedEffect(Unit) {
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(message = (gamesVerticalPaging.loadState.append as LoadState.Error).error.message.toString())
            }
        }
    }

    // initial load
    when (gamesVerticalPaging.loadState.refresh) {
        is LoadState.Loading -> GamesVerticalSectionPlaceholder()

        is LoadState.NotLoading -> GamesVerticalSection(
            scrollState = scrollState,
            gamesVerticalPaging = gamesVerticalPaging,
            onGameVerticalClicked = onGameVerticalClicked
        )

        is LoadState.Error -> {}
    }
}

@Composable
fun GamesVerticalSection(
    scrollState: LazyListState,
    gamesVerticalPaging: LazyPagingItems<ListResultItem>,
    onGameVerticalClicked: (gameId: Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 20.dp),
        contentPadding = PaddingValues(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        state = scrollState
    ) {
        items(
            count = gamesVerticalPaging.itemCount,
            key = gamesVerticalPaging.itemKey { data -> data.id ?: 0 }) { index ->
            val game = gamesVerticalPaging[index]
            GameItemVertical(
                image = game?.image ?: "",
                name = game?.name ?: "",
                date = game?.released ?: "",
                rating = game?.rating ?: 0.0F,
                onItemClicked = {
                    onGameVerticalClicked(game?.id ?: 0)
                }
            )
        }

        // load more (pagination)
        if (gamesVerticalPaging.loadState.append == LoadState.Loading) {
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

@Composable
fun GamesVerticalSectionPlaceholder() {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        items(count = 15) {
            ShimmerAnimation(shimmer = Shimmer.GAME_ITEM_VERTICAL_PLACEHOLDER)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GamesVerticalContentPreview() {
    val listResultPagingItems =
        flowOf(PagingData.empty<ListResultItem>()).collectAsLazyPagingItems()
    val scaffoldState = rememberScaffoldState()

    GameXTheme {
        GamesVerticalContent(
            gamesVerticalPaging = listResultPagingItems,
            scaffoldState = scaffoldState,
            onGameVerticalClicked = { },
        )
    }
}
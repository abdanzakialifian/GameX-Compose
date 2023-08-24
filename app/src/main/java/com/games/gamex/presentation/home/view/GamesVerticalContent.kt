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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.games.gamex.R
import com.games.gamex.domain.model.ListResultItem
import com.games.gamex.presentation.component.GameItemVertical
import com.games.gamex.presentation.component.ShimmerAnimation
import com.games.gamex.utils.Shimmer
import com.games.gamex.utils.isScrollToEnd
import kotlinx.coroutines.launch

@Composable
fun GamesVerticalContent(
    gamesVerticalPaging: LazyPagingItems<ListResultItem>,
    scaffoldState: ScaffoldState,
    onGameVerticalClicked: () -> Unit,
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
fun GamesVerticalSectionPlaceholder() {
    Column(
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
}

@Composable
fun GamesVerticalSection(
    scrollState: LazyListState,
    gamesVerticalPaging: LazyPagingItems<ListResultItem>,
    onGameVerticalClicked: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 20.dp),
        contentPadding = PaddingValues(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        state = scrollState
    ) {
        items(gamesVerticalPaging, key = { data -> data.id ?: 0 }) { game ->
            GameItemVertical(
                image = game?.image ?: "",
                name = game?.name ?: "",
                date = game?.released ?: "",
                rating = game?.rating ?: 0.0F,
                onItemClicked = onGameVerticalClicked
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
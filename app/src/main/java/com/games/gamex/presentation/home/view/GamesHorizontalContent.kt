package com.games.gamex.presentation.home.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.games.gamex.R
import com.games.gamex.domain.model.ListResultItem
import com.games.gamex.presentation.component.GameItemHorizontal
import com.games.gamex.presentation.component.ShimmerAnimation
import com.games.gamex.presentation.ui.theme.GameXTheme
import com.games.gamex.presentation.ui.theme.Purple
import com.games.gamex.utils.Shimmer
import com.games.gamex.utils.isScrollToEnd
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@Composable
fun GamesHorizontalContent(
    gamesHorizontalPaging: LazyPagingItems<ListResultItem>,
    scaffoldState: ScaffoldState,
    onGameClicked: (gameId: Int) -> Unit,
    onSeeAllGamesClicked: () -> Unit,
    onFetchError: (Boolean) -> Unit,
    modifier: Modifier = Modifier
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
        is LoadState.Loading -> GamesHorizontalSectionPlaceholder(modifier = modifier)


        is LoadState.NotLoading -> GamesHorizontalSection(
            scrollState = scrollState,
            gamesHorizontalPaging = gamesHorizontalPaging,
            onGameClicked =  onGameClicked,
            onSeeAllGamesClicked = onSeeAllGamesClicked,
            modifier = modifier
        )

        is LoadState.Error -> onFetchError(true)
    }
}

@Composable
fun GamesHorizontalSection(
    scrollState: LazyListState,
    gamesHorizontalPaging: LazyPagingItems<ListResultItem>,
    onGameClicked: (gameId: Int) -> Unit,
    onSeeAllGamesClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.all_games),
                color = Color.Black,
                fontFamily = FontFamily(Font(resId = R.font.open_sans_bold)),
                fontSize = 16.sp
            )

            Text(
                modifier = Modifier.clickable(
                    // remove ripple click
                    interactionSource = remember {
                        MutableInteractionSource()
                    }, indication = null
                ) {
                    onSeeAllGamesClicked()
                },
                text = stringResource(id = R.string.see_all),
                color = Purple,
                fontFamily = FontFamily(Font(resId = R.font.open_sans_semi_bold)),
                fontSize = 14.sp
            )
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            state = scrollState,
            modifier = Modifier.padding(top = 14.dp)
        ) {
            items(count = 10,
                key = gamesHorizontalPaging.itemKey { data -> data.id ?: 0 }) { index ->
                val game = gamesHorizontalPaging[index]
                GameItemHorizontal(
                    image = game?.image ?: "",
                    title = game?.name ?: "",
                    onItemClicked = {  onGameClicked(game?.id ?: 0) },
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
}

@Composable
fun GamesHorizontalSectionPlaceholder(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.all_games),
                color = Color.Black,
                fontFamily = FontFamily(Font(resId = R.font.open_sans_bold)),
                fontSize = 16.sp
            )

            Text(
                text = stringResource(id = R.string.see_all),
                color = Purple,
                fontFamily = FontFamily(Font(resId = R.font.open_sans_semi_bold)),
                fontSize = 14.sp
            )
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(top = 20.dp)
        ) {
            items(count = 10) {
                ShimmerAnimation(shimmer = Shimmer.GAME_ITEM_HORIZONTAL_PLACEHOLDER)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GamesHorizontalContentPreview() {
    val listResultPagingItems =
        flowOf(PagingData.empty<ListResultItem>()).collectAsLazyPagingItems()
    val scaffoldState = rememberScaffoldState()

    GameXTheme {
        GamesHorizontalContent(gamesHorizontalPaging = listResultPagingItems,
            scaffoldState = scaffoldState,
            onGameClicked = { },
            onSeeAllGamesClicked = {},
            onFetchError = { }
        )
    }
}
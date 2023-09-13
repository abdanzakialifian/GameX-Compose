package com.games.gamex.presentation.home.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.games.gamex.presentation.component.PlatformItem
import com.games.gamex.presentation.component.ShimmerAnimation
import com.games.gamex.presentation.ui.theme.GameXTheme
import com.games.gamex.utils.Shimmer
import com.games.gamex.utils.isScrollToEnd
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@Composable
fun PlatformsContent(
    platformsPaging: LazyPagingItems<ListResultItem>,
    scaffoldState: ScaffoldState,
    onPlatformClicked: () -> Unit,
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
        platformsPaging.retry()
    }

    // handle error data while load more
    if (platformsPaging.loadState.append is LoadState.Error) {
        LaunchedEffect(Unit) {
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(message = (platformsPaging.loadState.append as LoadState.Error).error.message.toString())
            }
        }
    }

    // initial load
    when (platformsPaging.loadState.refresh) {
        is LoadState.Loading -> PlatformsSectionPlaceholder(modifier = modifier)

        is LoadState.NotLoading -> PlatformsSection(
            scrollState = scrollState,
            platformsPaging = platformsPaging,
            onPlatformClicked = onPlatformClicked,
            modifier = modifier
        )

        is LoadState.Error -> onFetchError(true)
    }
}

@Composable
fun PlatformsSection(
    scrollState: LazyListState,
    platformsPaging: LazyPagingItems<ListResultItem>,
    onPlatformClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = stringResource(id = R.string.platforms),
            color = Color.Black,
            fontFamily = FontFamily(Font(resId = R.font.open_sans_bold)),
            fontSize = 16.sp
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            state = scrollState,
            modifier = Modifier.padding(top = 14.dp, bottom = 40.dp)
        ) {
            items(
                count = platformsPaging.itemCount,
                key = platformsPaging.itemKey { data -> data.id ?: 0 }) { index ->
                val platform = platformsPaging[index]
                PlatformItem(
                    image = platform?.image ?: "",
                    name = platform?.name ?: "",
                    totalGames = platform?.gamesCount ?: 0,
                    onItemClicked = onPlatformClicked
                )
            }

            // load more (pagination)
            if (platformsPaging.loadState.append == LoadState.Loading) {
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
}

@Composable
fun PlatformsSectionPlaceholder(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = stringResource(id = R.string.platforms),
            color = Color.Black,
            fontFamily = FontFamily(Font(resId = R.font.open_sans_bold)),
            fontSize = 16.sp
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(top = 14.dp, bottom = 40.dp)
        ) {
            items(count = 10) {
                ShimmerAnimation(shimmer = Shimmer.PLATFORM_ITEM_PLACEHOLDER)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlatformsContentPreview() {
    val listResultPagingItems =
        flowOf(PagingData.empty<ListResultItem>()).collectAsLazyPagingItems()
    val scaffoldState = rememberScaffoldState()

    GameXTheme {
        PlatformsContent(
            platformsPaging = listResultPagingItems,
            scaffoldState = scaffoldState,
            onPlatformClicked = { },
            onFetchError = { }
        )
    }
}
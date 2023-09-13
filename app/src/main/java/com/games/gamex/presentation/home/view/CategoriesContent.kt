package com.games.gamex.presentation.home.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.games.gamex.presentation.component.CategoriesItem
import com.games.gamex.presentation.component.ShimmerAnimation
import com.games.gamex.presentation.ui.theme.GameXTheme
import com.games.gamex.utils.Shimmer
import com.games.gamex.utils.isScrollToEnd
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@Composable
fun CategoriesContent(
    genresPaging: LazyPagingItems<ListResultItem>,
    scaffoldState: ScaffoldState,
    onGenreClicked: () -> Unit,
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
        genresPaging.retry()
    }

    // handle error data while load more
    if (genresPaging.loadState.append is LoadState.Error) {
        LaunchedEffect(Unit) {
            coroutineScope.launch {
                val errorMessage =
                    (genresPaging.loadState.append as LoadState.Error).error.message.toString()
                scaffoldState.snackbarHostState.showSnackbar(message = errorMessage)
            }
        }
    }

    // initial load
    when (genresPaging.loadState.refresh) {
        is LoadState.Loading -> CategoriesSectionPlaceholder(modifier = modifier)

        is LoadState.NotLoading -> CategoriesSection(
            scrollState = scrollState,
            genresPaging = genresPaging,
            onGenreClicked = onGenreClicked,
            modifier = modifier
        )

        is LoadState.Error -> onFetchError(true)
    }
}

@Composable
fun CategoriesSection(
    scrollState: LazyListState,
    genresPaging: LazyPagingItems<ListResultItem>,
    onGenreClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = stringResource(id = R.string.categories),
            color = Color.Black,
            fontFamily = FontFamily(Font(resId = R.font.open_sans_bold)),
            fontSize = 16.sp
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            state = scrollState,
            modifier = Modifier.padding(top = 14.dp)
        ) {
            items(
                count = genresPaging.itemCount,
                key = genresPaging.itemKey { data -> data.id ?: 0 }) { index ->
                val genre = genresPaging[index]
                CategoriesItem(
                    category = genre?.name ?: "",
                    image = genre?.image ?: "",
                    onItemClicked = onGenreClicked
                )
            }

            // load more (pagination)
            if (genresPaging.loadState.append == LoadState.Loading) {
                item {
                    ShimmerAnimation(shimmer = Shimmer.CATEGORIES_ITEM_PLACEHOLDER)
                }
            }
        }
    }
}

@Composable
fun CategoriesSectionPlaceholder(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = stringResource(id = R.string.categories),
            color = Color.Black,
            fontFamily = FontFamily(Font(resId = R.font.open_sans_bold)),
            fontSize = 16.sp
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(top = 14.dp)
        ) {
            items(count = 10) {
                ShimmerAnimation(shimmer = Shimmer.CATEGORIES_ITEM_PLACEHOLDER)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoriesContentPreview() {
    val listResultPagingItems =
        flowOf(PagingData.empty<ListResultItem>()).collectAsLazyPagingItems()
    val scaffoldState = rememberScaffoldState()

    GameXTheme {
        CategoriesContent(
            genresPaging = listResultPagingItems,
            scaffoldState = scaffoldState,
            onGenreClicked = { },
            onFetchError = { }
        )
    }
}

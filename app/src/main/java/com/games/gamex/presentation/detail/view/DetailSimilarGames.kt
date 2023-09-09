package com.games.gamex.presentation.detail.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.games.gamex.R
import com.games.gamex.domain.model.ListResultItem
import com.games.gamex.presentation.component.GameItemHorizontal
import com.games.gamex.presentation.ui.theme.Purple

@Composable
fun DetailSimilarGames(
    gameSeriesPagingItems: LazyPagingItems<ListResultItem>,
    modifier: Modifier = Modifier,
    isPreview: Boolean = false
) {
    if (isPreview) {
        Column(modifier = modifier) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.similar_games), fontFamily = FontFamily(
                        Font(R.font.open_sans_bold)
                    ), fontSize = 16.sp
                )

                Text(
                    text = stringResource(id = R.string.see_all), fontFamily = FontFamily(
                        Font(R.font.open_sans_semi_bold)
                    ), color = Purple, fontSize = 14.sp
                )
            }

            LazyRow(
                modifier = Modifier.padding(vertical = 14.dp),
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(10) {
                    GameItemHorizontal(image = "", title = "", onItemClicked = { })
                }
            }
        }
    } else {
        if (gameSeriesPagingItems.itemCount > 0) {
            Column(modifier = modifier) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.similar_games), fontFamily = FontFamily(
                            Font(R.font.open_sans_bold)
                        ), fontSize = 16.sp
                    )

                    if (gameSeriesPagingItems.itemCount > 10) {
                        Text(
                            text = stringResource(id = R.string.see_all), fontFamily = FontFamily(
                                Font(R.font.open_sans_semi_bold)
                            ), color = Purple, fontSize = 14.sp
                        )
                    }
                }

                LazyRow(
                    modifier = Modifier.padding(vertical = 14.dp),
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(count = gameSeriesPagingItems.itemCount,
                        key = gameSeriesPagingItems.itemKey { data -> data.id ?: 0 }) { index ->
                        val game = gameSeriesPagingItems[index]
                        GameItemHorizontal(
                            image = game?.image ?: "",
                            title = game?.name ?: "",
                            onItemClicked = { })
                    }
                }
            }
        }
    }
}
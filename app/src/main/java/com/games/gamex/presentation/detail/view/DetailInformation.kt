package com.games.gamex.presentation.detail.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.games.gamex.domain.model.DetailGame
import com.games.gamex.domain.model.ListResultItem

@Composable
fun DetailInformation(
    data: DetailGame,
    gameSeriesPagingItems: LazyPagingItems<ListResultItem>,
    modifier: Modifier = Modifier,
    isPreview: Boolean = false,
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 250.dp),
        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(top = 20.dp)
        ) {
            DetailInformationHeader(
                genres = data.genres,
                imageBackground = data.imageBackground,
                name = data.name,
                rating = data.rating,
                isPreview = isPreview
            )

            DetailInformationSubHeader(
                publishers = data.publishers, released = data.released, metacritic = data.metacritic
            )

            DetailImageScreenshot(images = data.images, isPreview = isPreview)

            DetailGameOverview(
                description = data.description,
                modifier = Modifier.padding(20.dp),
            )

            DetailSimilarGames(
                gameSeriesPagingItems = gameSeriesPagingItems, isPreview = isPreview
            )
        }
    }
}
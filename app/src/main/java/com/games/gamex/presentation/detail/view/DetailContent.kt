package com.games.gamex.presentation.detail.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.games.gamex.domain.model.DetailGame
import com.games.gamex.domain.model.ListResultItem
import com.games.gamex.presentation.component.ShimmerAnimation
import com.games.gamex.presentation.ui.theme.GreyPlaceholder
import com.games.gamex.presentation.ui.theme.WhiteTransparent
import com.games.gamex.utils.Shimmer
import com.games.gamex.utils.UiState

@Composable
fun DetailContent(
    uiState: UiState<DetailGame>,
    gameSeriesPagingItems: LazyPagingItems<ListResultItem>,
    onImageUrl: (String) -> Unit,
    onImageBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    isPreview: Boolean = false,
) {
    when (uiState) {
        is UiState.Loading -> ShimmerAnimation(shimmer = Shimmer.GAME_DETAIL_PLACEHOLDER)

        is UiState.Success -> {
            val data = uiState.data

            onImageUrl(data.backgroundImageAdditional ?: "")

            Box(modifier = modifier) {
                if (isPreview) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .align(Alignment.TopCenter)
                            .background(color = Color.LightGray)
                    )
                } else {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .align(Alignment.TopCenter),
                        model = data.backgroundImageAdditional,
                        placeholder = ColorPainter(GreyPlaceholder),
                        contentDescription = "Image Background",
                        contentScale = ContentScale.Crop
                    )
                }

                Box(
                    Modifier
                        .padding(start = 10.dp, top = 10.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(WhiteTransparent)
                        .align(Alignment.TopStart)
                        .clickable { onImageBackClick() }) {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(20.dp),
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back Button"
                    )
                }

                DetailInformation(
                    data = data,
                    gameSeriesPagingItems = gameSeriesPagingItems,
                    isPreview = isPreview
                )
            }
        }

        is UiState.Error -> {}
    }
}
package com.games.gamex.presentation.detail.view

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.games.gamex.R

@Composable
fun DetailGameOverview(description: String?, modifier: Modifier = Modifier) {
    var isShowMore by remember {
        mutableStateOf(false)
    }

    if (!description.isNullOrEmpty()) {
        Column(modifier = modifier) {
            Text(
                text = stringResource(id = R.string.overview), fontFamily = FontFamily(
                    Font(R.font.open_sans_bold)
                ), fontSize = 16.sp
            )

            Text(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .animateContentSize(
                        // add animation expandable
                        animationSpec = tween(
                            durationMillis = 300, easing = LinearOutSlowInEasing
                        )
                    ),
                text = description,
                maxLines = if (isShowMore) Int.MAX_VALUE else 3,
                overflow = if (isShowMore) TextOverflow.Clip else TextOverflow.Ellipsis,
                fontFamily = FontFamily(
                    Font(R.font.open_sans_regular)
                ),
                color = colorResource(id = R.color.dark_grey),
                fontSize = 12.sp,
                lineHeight = 20.sp
            )

            Text(modifier = Modifier
                .clickable(
                    // remove ripple click
                    interactionSource = remember {
                        MutableInteractionSource()
                    }, indication = null
                ) {
                    isShowMore = !isShowMore
                }
                .padding(vertical = 4.dp),
                text = if (isShowMore) stringResource(id = R.string.show_less) else stringResource(
                    id = R.string.read_more
                ),
                fontFamily = FontFamily(
                    Font(R.font.open_sans_medium)
                ),
                color = colorResource(id = R.color.purple),
                fontSize = 12.sp)
        }
    }
}
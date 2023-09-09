package com.games.gamex.presentation.detail.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.games.gamex.R
import com.games.gamex.presentation.ui.theme.LightPurple
import com.games.gamex.presentation.ui.theme.Purple
import com.games.gamex.presentation.ui.theme.WhiteHeavy
import com.games.gamex.utils.convertDate

@Composable
fun DetailInformationSubHeader(
    publishers: List<String>?, released: String?, metacritic: Int?, modifier: Modifier = Modifier
) {
    val publisher = publishers?.joinToString(", ")

    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .padding(30.dp)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = modifier.weight(1F), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Release Date",
                fontFamily = FontFamily(Font(R.font.open_sans_medium)),
                fontSize = 12.sp,
                color = colorResource(id = R.color.dark_grey)
            )

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = released?.convertDate() ?: "-",
                fontFamily = FontFamily(Font(R.font.open_sans_semi_bold)),
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Divider(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxHeight()
                .width(1.dp),
            color = WhiteHeavy,
        )

        Column(
            modifier = modifier.weight(1F), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Metascore",
                fontFamily = FontFamily(Font(R.font.open_sans_medium)),
                fontSize = 12.sp,
                color = colorResource(id = R.color.dark_grey)
            )

            Box(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .size(35.dp)
                    .border(
                        width = 1.5.dp, color = LightPurple, shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = (metacritic ?: 0).toString(),
                    color = Purple,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.open_sans_bold))
                )
            }
        }

        Divider(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxHeight()
                .width(1.dp),
            color = WhiteHeavy,
        )

        Column(
            modifier = modifier.weight(1F), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Publisher",
                fontFamily = FontFamily(Font(R.font.open_sans_medium)),
                fontSize = 12.sp,
                color = colorResource(id = R.color.dark_grey)
            )

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = if (!publisher.isNullOrEmpty()) publisher else "-",
                fontFamily = FontFamily(Font(R.font.open_sans_semi_bold)),
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
package com.games.gamex.presentation.component

import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.games.gamex.R
import com.games.gamex.presentation.ui.theme.GameXTheme
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun GameItemSecond(
    image: String,
    name: String,
    date: String,
    onItemClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color.White,
        elevation = 0.dp,
    ) {
        Row(modifier = Modifier.clickable { onItemClicked() }) {
            AsyncImage(
                modifier = Modifier
                    .size(height = 100.dp, width = 100.dp)
                    .padding(vertical = 10.dp, horizontal = 10.dp)
                    .clip(RoundedCornerShape(10.dp)),
                model = image,
                contentDescription = "Image Game",
                contentScale = ContentScale.Crop
            )
            Text(
                text = name,
                fontFamily = FontFamily(Font(resId = R.font.open_sans_semi_bold)),
                fontSize = 16.sp,
                color = Color.Black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = convertDate(date) ?: "",
                fontFamily = FontFamily(Font(R.font.open_sans_medium)),
                fontSize = 12.sp,
                color = Color.LightGray
            )
        }
    }
}

fun convertDate(date: String): String? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val localDateTime = ZonedDateTime.parse(date)
        val dateTimeFormatter = DateTimeFormatter.ofPattern("dd MM yyyy")
        dateTimeFormatter.format(localDateTime)
    } else {
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val simpleDateFormat = SimpleDateFormat("dd MM yyyy", Locale.getDefault())
        simpleDateFormat.format(parser.parse(date) ?: "")
    }
}

@Preview(showBackground = true)
@Composable
fun GameItemSecondPreview() {
    GameXTheme {
        GameItemSecond(image = "", name = "GTA-V", date = "2011-04-18", onItemClicked = {})
    }
}
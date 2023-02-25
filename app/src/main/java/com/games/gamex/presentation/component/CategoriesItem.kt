package com.games.gamex.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.games.gamex.R
import com.games.gamex.presentation.ui.theme.GameXTheme

@Composable
fun CategoriesItem(category: String, onNavigate: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        elevation = 4.dp
    ) {
        Text(
            modifier = Modifier
                .clickable { onNavigate() }
                .padding(horizontal = 14.dp, vertical = 6.dp),
            text = category,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(resId = R.font.open_sans_medium)),
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoriesItemPreview() {
    GameXTheme {
        CategoriesItem("Adventure", onNavigate = {})
    }
}
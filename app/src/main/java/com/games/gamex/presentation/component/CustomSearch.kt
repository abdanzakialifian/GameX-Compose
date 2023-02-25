package com.games.gamex.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.games.gamex.R
import com.games.gamex.presentation.ui.theme.GameXTheme

@Composable
fun CustomSearch(
    value: String,
    hint: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        placeholder = {
            Text(
                text = hint,
                fontFamily = FontFamily(Font(resId = R.font.open_sans_medium)),
                fontSize = 16.sp
            )
        },
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Search,contentDescription = "Icon Search")
        },
        shape = RoundedCornerShape(20.dp),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            backgroundColor = Color.White,
            placeholderColor = Color.Gray,
            textColor = Color.Black,
            cursorColor = Color.Black
        ),
        textStyle = TextStyle(
            fontFamily = FontFamily(Font(resId = R.font.open_sans_medium)),
            fontSize = 16.sp,
        )
    )
}

@Preview(showBackground = true)
@Composable
fun CustomSearchPreview() {
    GameXTheme {
        CustomSearch(value = "", hint = "Search game", onValueChange = {})
    }
}
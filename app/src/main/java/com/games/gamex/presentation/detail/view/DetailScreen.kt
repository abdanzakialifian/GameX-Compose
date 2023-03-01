package com.games.gamex.presentation.detail.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.games.gamex.presentation.ui.theme.GameXTheme

@Composable
fun DetailScreen(modifier: Modifier = Modifier) {
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4_XL)
@Composable
fun DetailScreenPreview() {
    GameXTheme {
        DetailScreen()
    }
}
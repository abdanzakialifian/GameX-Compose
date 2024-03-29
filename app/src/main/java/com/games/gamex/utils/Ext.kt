package com.games.gamex.utils

import android.os.Build
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String.convertDate(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
        val localDateTime = LocalDate.parse(this)
        localDateTime.format(dateTimeFormatter)
    } else {
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val simpleDateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        simpleDateFormat.format(parser.parse(this) ?: "")
    }
}

fun LazyListState.isScrollToEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

fun String.fromHex() = Color(android.graphics.Color.parseColor(this))
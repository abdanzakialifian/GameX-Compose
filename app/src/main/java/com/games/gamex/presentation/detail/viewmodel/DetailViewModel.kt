package com.games.gamex.presentation.detail.viewmodel

import androidx.lifecycle.ViewModel
import com.games.gamex.domain.model.DetailGame
import com.games.gamex.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class DetailViewModel : ViewModel() {
    val gameId = MutableStateFlow("")

    fun setGameId(gameId: String) {
        this.gameId.value = gameId
    }

    abstract val getDetailGame: StateFlow<UiState<DetailGame>>
}
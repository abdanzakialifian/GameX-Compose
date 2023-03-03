package com.games.gamex.presentation.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.games.gamex.domain.interfaces.GameXUseCase
import com.games.gamex.domain.model.DetailGame
import com.games.gamex.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val gameXUseCase: GameXUseCase) : ViewModel() {
    private val gameId = MutableStateFlow("")

    fun setGameId(gameId: String) {
        this.gameId.value = gameId
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val getDetailGame: Flow<UiState<DetailGame>> = gameId.flatMapLatest { id ->
        gameXUseCase.getDetailGame(id).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = UiState.Loading
        )
    }
}
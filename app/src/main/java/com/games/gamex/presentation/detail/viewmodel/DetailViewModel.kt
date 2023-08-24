package com.games.gamex.presentation.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.games.gamex.domain.model.DetailGame
import com.games.gamex.domain.usecase.GetDetailGame
import com.games.gamex.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val getDetailGameUseCase: GetDetailGame) :
    ViewModel() {
    private val gameId = MutableStateFlow("")

    fun setGameId(gameId: String) {
        this.gameId.value = gameId
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val getDetailGame: Flow<UiState<DetailGame>> = gameId.flatMapLatest { id ->
        getDetailGameUseCase(id).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = UiState.Loading
        )
    }
}
package com.games.gamex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.games.gamex.domain.interfaces.GameXRepository
import com.games.gamex.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GameXViewModel @Inject constructor(private val gameXRepository: GameXRepository) :
    ViewModel() {
    val getGames = gameXRepository.getGames().stateIn(
        initialValue = UiState.Loading,
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000L)
    )
}
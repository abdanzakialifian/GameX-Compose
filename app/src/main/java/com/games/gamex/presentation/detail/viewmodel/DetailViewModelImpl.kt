package com.games.gamex.presentation.detail.viewmodel

import androidx.lifecycle.viewModelScope
import com.games.gamex.domain.model.DetailGame
import com.games.gamex.domain.usecase.GetDetailGame
import com.games.gamex.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DetailViewModelImpl @Inject constructor(private val getDetailGameUseCase: GetDetailGame) :
    DetailViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    override
    val getDetailGame: StateFlow<UiState<DetailGame>> = gameId.flatMapLatest { id ->
        getDetailGameUseCase(id)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = UiState.Loading
    )
}
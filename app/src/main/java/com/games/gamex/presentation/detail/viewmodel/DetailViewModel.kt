package com.games.gamex.presentation.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.games.gamex.domain.model.DetailGame
import com.games.gamex.domain.model.ListResultItem
import com.games.gamex.domain.usecase.GetDetailGame
import com.games.gamex.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val getDetailGameUseCase: GetDetailGame) :
    ViewModel() {

    private val _gameId = MutableStateFlow("")
    private val _isPaging = MutableStateFlow(false)

    fun setDataGameSeries(gameId: String, isPaging: Boolean) {
        _gameId.value = gameId
        _isPaging.value = isPaging
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val getDetailGame: StateFlow<Pair<UiState<DetailGame>, StateFlow<PagingData<ListResultItem>>>> =
        combine(
            _gameId,
            _isPaging
        ) { gameId, isPaging ->
            Pair(gameId, isPaging)
        }.flatMapLatest { pair ->
            val gameId = pair.first
            val isPaging = pair.second
            getDetailGameUseCase(gameId, isPaging, viewModelScope)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = Pair(UiState.Loading, MutableStateFlow(PagingData.empty()))
        )
}
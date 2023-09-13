package com.games.gamex.presentation.gameslist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.games.gamex.domain.model.ListResultItem
import com.games.gamex.domain.usecase.gameslist.GamesListWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class GamesListViewModel @Inject constructor(private val gamesListWrapper: GamesListWrapper) :
    ViewModel() {
    private val _gameId = MutableStateFlow("")
    private val _isPaging = MutableStateFlow(false)

    fun setGameId(gameId: String) {
        _gameId.value = gameId
    }

    fun setIsPaging(isPaging: Boolean) {
        _isPaging.value = isPaging
    }

    val getAllGamesSeries: StateFlow<PagingData<ListResultItem>> =
        combine(_gameId, _isPaging) { gameId, isPaging ->
            Pair(gameId, isPaging)
        }.flatMapLatest { pair ->
            val gameId = pair.first
            val isPaging = pair.second
            gamesListWrapper.getAllGamesSeries(gameId, isPaging)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )
}
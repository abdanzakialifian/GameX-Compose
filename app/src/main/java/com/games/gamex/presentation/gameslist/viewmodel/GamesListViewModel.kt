package com.games.gamex.presentation.gameslist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.games.gamex.domain.model.ListResultItem
import com.games.gamex.domain.usecase.gameslist.GamesListWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class GamesListViewModel @Inject constructor(private val gamesListWrapper: GamesListWrapper) :
    ViewModel() {
    private val _gameId = MutableStateFlow("")

    fun setGameId(gameId: String) {
        _gameId.value = gameId
    }

    val getAllGamesSeries: StateFlow<PagingData<ListResultItem>> =
        _gameId.flatMapLatest { gameId ->
            gamesListWrapper.getAllGamesSeries(gameId, true)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )

    val getAllGames: StateFlow<PagingData<ListResultItem>> =
        gamesListWrapper.getAllGames("", true).cachedIn(viewModelScope).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )
}
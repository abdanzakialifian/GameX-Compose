package com.games.gamex.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.games.gamex.domain.model.GamesResultItem
import com.games.gamex.domain.model.GenresResultItem
import com.games.gamex.domain.model.PlatformsResultItem
import com.games.gamex.domain.usecase.GameXInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val gameXInteractor: GameXInteractor) :
    ViewModel() {

    private val searchQuery = MutableStateFlow("")

    fun setSearchQuery(searchQuery: String) {
        this.searchQuery.value = searchQuery
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val getAllGames: Flow<PagingData<GamesResultItem>> = searchQuery.flatMapLatest { searchQuery ->
        gameXInteractor.getAllGames(searchQuery).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )
    }

    val getAllGenres: StateFlow<PagingData<GenresResultItem>> =
        gameXInteractor.getAllGenres().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )

    val getAllPlatforms: StateFlow<PagingData<PlatformsResultItem>> =
        gameXInteractor.getAllPlatforms().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )
}
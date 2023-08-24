package com.games.gamex.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.games.gamex.domain.model.ListResultItem
import com.games.gamex.domain.usecase.home.HomeUseCaseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeUseCaseWrapper: HomeUseCaseWrapper) :
    ViewModel() {

    private val searchQuery = MutableStateFlow("")

    fun setSearchQuery(searchQuery: String) {
        this.searchQuery.value = searchQuery
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchAllGames: StateFlow<PagingData<ListResultItem>> =
        searchQuery.flatMapLatest { searchQuery ->
            homeUseCaseWrapper.getAllGames(searchQuery).stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = PagingData.empty()
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )

    val getAllGames: StateFlow<PagingData<ListResultItem>> =
        homeUseCaseWrapper.getAllGames("").stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )

    val getAllGameGenres: StateFlow<PagingData<ListResultItem>> =
        homeUseCaseWrapper.getAllGameGenres().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )

    val getAllGamePlatforms: StateFlow<PagingData<ListResultItem>> =
        homeUseCaseWrapper.getAllGamePlatforms().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )
}
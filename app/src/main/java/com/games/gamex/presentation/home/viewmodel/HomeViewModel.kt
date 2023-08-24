package com.games.gamex.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.games.gamex.domain.model.ListResultItem
import com.games.gamex.domain.usecase.home.HomeUseCaseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeUseCaseWrapper: HomeUseCaseWrapper) :
    ViewModel() {

    private val searchQuery = MutableStateFlow("")

    fun setSearchQuery(searchQuery: String) {
        this.searchQuery.value = searchQuery
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val searchAllGames: StateFlow<PagingData<ListResultItem>> =
        searchQuery.debounce(500L).flatMapLatest { searchQuery ->
            homeUseCaseWrapper.getAllGames(searchQuery)
        }.cachedIn(viewModelScope).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )

    val getAllGames: StateFlow<PagingData<ListResultItem>> =
        homeUseCaseWrapper.getAllGames("").cachedIn(viewModelScope).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )

    val getAllGameGenres: StateFlow<PagingData<ListResultItem>> =
        homeUseCaseWrapper.getAllGameGenres().cachedIn(viewModelScope).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )

    val getAllGamePlatforms: StateFlow<PagingData<ListResultItem>> =
        homeUseCaseWrapper.getAllGamePlatforms().cachedIn(viewModelScope).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )

    val emptyFlow: StateFlow<PagingData<ListResultItem>> =
        flowOf(PagingData.empty<ListResultItem>()).cachedIn(viewModelScope).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )
}
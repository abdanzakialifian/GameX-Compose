package com.games.gamex.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.games.gamex.data.source.remote.response.GamesResultItem
import com.games.gamex.domain.usecase.GameXInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val gameXInteractor: GameXInteractor) :
    ViewModel() {
    fun getAllGames(): Flow<PagingData<GamesResultItem>> = gameXInteractor.getAllGames().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000L),
        initialValue = PagingData.empty()
    )
}
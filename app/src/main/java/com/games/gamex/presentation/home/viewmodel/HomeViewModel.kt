package com.games.gamex.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.games.gamex.domain.model.GamesResultItem
import com.games.gamex.domain.model.GenresResultItem
import com.games.gamex.domain.usecase.GameXInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
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

    fun getAllGenres(): Flow<PagingData<GenresResultItem>> = gameXInteractor.getAllGenres().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000L),
        initialValue = PagingData.empty()
    )
}
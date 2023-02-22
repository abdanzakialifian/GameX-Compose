package com.games.gamex.domain.di

import com.games.gamex.domain.interfaces.GameXUseCase
import com.games.gamex.domain.usecase.GameXInteractor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class GameXUseCaseModule {
    @Binds
    @ViewModelScoped
    abstract fun provideGameXUseCase(gameXInteractor: GameXInteractor): GameXUseCase
}
package com.games.gamex.data.di

import com.games.gamex.data.repository.GameXRepositoryImpl
import com.games.gamex.domain.interfaces.GameXRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GameXRepositoryModule {
    @Binds
    @Singleton
    abstract fun provideGameXRepository(gameXRepositoryImpl: GameXRepositoryImpl): GameXRepository
}
package com.games.gamex.domain.usecase

import com.games.gamex.domain.interfaces.GameXRepository
import com.games.gamex.domain.interfaces.GameXUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameXInteractor @Inject constructor(private val gameXRepository: GameXRepository) :
    GameXUseCase {
}
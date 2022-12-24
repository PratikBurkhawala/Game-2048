package com.pratik.game2048.domain.usecase

import com.pratik.game2048.domain.model.GameStats
import com.pratik.game2048.domain.repository.GameRepository

class GetCurrentGame(
    private val repository: GameRepository
) {
    suspend operator fun invoke(): GameStats? {
        return repository.getCurrentGame()
    }
}
package com.pratik.game2048.domain.usecase

import com.pratik.game2048.domain.model.GameStats
import com.pratik.game2048.domain.repository.GameRepository
import com.pratik.game2048.models.GameData

class SaveGameStats(
    private val repository: GameRepository
) {
    suspend fun saveNewGame(gameStats: GameStats) {
        repository.saveGame(gameStats)
    }

    suspend fun saveExistingGame(
        id: String,
        gameData: GameData,
        highestNumber: Int,
        moves: Int,
        time: Int
    ) {
        repository.saveExistingGame(id, gameData, highestNumber, moves, time)
    }
}
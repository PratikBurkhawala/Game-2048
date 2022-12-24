package com.pratik.game2048.domain.repository

import com.pratik.game2048.domain.model.GameStats
import com.pratik.game2048.models.GameData

interface GameRepository {
    suspend fun saveGame(gameStats: GameStats)
    suspend fun getCurrentGame(): GameStats?
    suspend fun closeGameById(id: String)
    suspend fun saveExistingGame(
        id: String,
        gameData: GameData,
        highestScore: Int,
        moves: Int,
        time: Int
    )

    suspend fun getAllGames(): List<GameStats>
}
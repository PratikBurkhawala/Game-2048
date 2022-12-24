package com.pratik.game2048.data.repository

import com.pratik.game2048.data.db.GameStatsDao
import com.pratik.game2048.domain.model.GameStats
import com.pratik.game2048.domain.repository.GameRepository
import com.pratik.game2048.models.GameData

class GameRepositoryImpl(
    private val gameStatsDao: GameStatsDao
) : GameRepository {
    override suspend fun saveGame(gameStats: GameStats) {
        gameStatsDao.insertGameStats(gameStats)
    }

    override suspend fun getCurrentGame(): GameStats? {
        return gameStatsDao.getCurrentGame()
    }

    override suspend fun closeGameById(id: String) {
        gameStatsDao.closeGameById(id)
    }

    override suspend fun saveExistingGame(
        id: String,
        gameData: GameData,
        highestScore: Int,
        moves: Int,
        time: Int
    ) {
        gameStatsDao.saveExistingGame(id, gameData, highestScore, moves, time)
    }

    override suspend fun getAllGames(): List<GameStats> {
        return gameStatsDao.getAllGames()
    }
}
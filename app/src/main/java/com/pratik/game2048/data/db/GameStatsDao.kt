package com.pratik.game2048.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pratik.game2048.domain.model.GameStats
import com.pratik.game2048.models.GameData

@Dao
abstract class GameStatsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertGameStats(gameStats: GameStats)

    @Query("SELECT * from GameStats where isCurrent = 1")
    abstract suspend fun getCurrentGame(): GameStats?

    @Query("UPDATE GameStats SET isCurrent = 0 where uid = :id")
    abstract suspend fun closeGameById(id: String)

    @Query("UPDATE GameStats SET highestScore = :highestScore, gameData = :gameData, moves = :moves, time = :time where uid = :id")
    abstract suspend fun saveExistingGame(
        id: String,
        gameData: GameData,
        highestScore: Int,
        moves: Int,
        time: Int
    )

    @Query("SELECT * FROM GameStats ORDER BY highestScore DESC")
    abstract suspend fun getAllGames(): List<GameStats>

}
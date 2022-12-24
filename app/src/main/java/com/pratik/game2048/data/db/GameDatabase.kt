package com.pratik.game2048.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pratik.game2048.domain.model.GameStats

@Database(
    entities = [
        GameStats::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(GameDataConverter::class)
abstract class GameDatabase : RoomDatabase() {
    abstract fun gameStatsDao(): GameStatsDao
}
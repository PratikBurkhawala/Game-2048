package com.pratik.game2048.data.db

import androidx.room.TypeConverter
import com.pratik.game2048.models.GameData
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class GameDataConverter {

    @TypeConverter
    fun gameDataToString(gameData: GameData): String {
        return Json.encodeToString(gameData)
    }

    @TypeConverter
    fun stringToGameData(string: String): GameData {
        return Json.decodeFromString(string)
    }

}
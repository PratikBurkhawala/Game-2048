package com.pratik.game2048.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pratik.game2048.models.GameData
import java.util.*

@Entity
data class GameStats(
    @PrimaryKey val uid: String = UUID.randomUUID().toString(),
    val name: String = "",
    val gameSize: Int = 0,
    val highestScore: Int = 0,
    val gameData: GameData,
    val winningNumber: Int = 0,
    val moves: Int = 0,
    val time: Int = 0,
    val isCurrent: Boolean = false
)
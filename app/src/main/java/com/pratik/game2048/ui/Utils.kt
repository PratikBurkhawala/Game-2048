package com.pratik.game2048.ui

import com.pratik.game2048.R
import com.pratik.game2048.models.Block2
import com.pratik.game2048.models.Block4
import com.pratik.game2048.models.BlockType
import com.pratik.game2048.models.GameData

fun Int.getFormatTime(): String {
    val hours = this / 3600
    val minutes = this / 60
    val seconds = this % 60
    return if (hours > 0) {
        "${hours.toString().padStart(2, '0')}:${
            minutes.toString().padStart(2, '0')
        }:${seconds.toString().padStart(2, '0')}"
    } else {
        "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
    }
}

fun getBlock2or4AtRandom(): BlockType {
    return if ((0..10).random() % 2 == 0) {
        Block4()
    } else {
        Block2()
    }
}

fun addElementAtRandom(gameData: GameData, gameSize: Int): GameData? {
    val gameDataIn1D = gameData.get1DListFrom2D()
    val availableIndex = mutableListOf<Int>()
    gameDataIn1D.forEachIndexed { index, blockType ->
        if (blockType.resourceId == R.string.common_string_empty) {
            availableIndex.add(index)
        }
    }
    return if (availableIndex.isEmpty()) {
        null
    } else {
        val randomIndex = availableIndex.random()
        val indexIn2D = randomIndex.convert1DIndexTo2DIndex(gameSize)
        gameData.data[indexIn2D.first][indexIn2D.second] = getBlock2or4AtRandom()
        gameData
    }
}

fun Int.convert1DIndexTo2DIndex(gameSize: Int): Pair<Int, Int> {
    val row = this / gameSize
    val colum = this % gameSize
    return Pair(row, colum)
}
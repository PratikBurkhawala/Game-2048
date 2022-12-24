package com.pratik.game2048.models

import android.util.Log
import androidx.compose.runtime.Immutable
import com.pratik.game2048.R
import com.pratik.game2048.ui.getBlock2or4AtRandom
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class GameData(
    val data: MutableList<MutableList<BlockType>>
) {

    fun get1DListFrom2D(): List<BlockType> {
        return data.flatMap { it.asIterable() }
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    fun getHighestNumber(): Int {
        val mutableList = mutableListOf<Int>()
        data.forEach { row ->
            mutableList.add(row.maxByOrNull { it.value }?.value ?: 0)
        }
        return mutableList.max()
    }

    fun canGameMoveFurther(): Boolean {
        val leftSwipe = getSwipePossibilities(data, SwipeType.LEFT)
        val rightSwipe = getSwipePossibilities(data, SwipeType.RIGHT)
        val upSwipe = getSwipePossibilities(data, SwipeType.UP)
        val downSwipe = getSwipePossibilities(data, SwipeType.DOWN)
        return leftSwipe || rightSwipe || upSwipe || downSwipe
    }

    private fun getSwipePossibilities(
        data: MutableList<MutableList<BlockType>>,
        swipeType: SwipeType
    ): Boolean {
        var canAnyTileMove = false
        data.forEachIndexed { rowIndex, rowBlockTypes ->
            rowBlockTypes.forEachIndexed { columnIndex, block ->
                if (block.resourceId != R.string.common_string_empty) {
                    var indexAfterSwipeAction =
                        getPreviousPositionIndex(
                            Pair(rowIndex, columnIndex),
                            swipeType,
                            rowBlockTypes.size
                        )
                    while (indexAfterSwipeAction != null) {
                        val previousBlock =
                            data[indexAfterSwipeAction.first][indexAfterSwipeAction.second]
                        when (previousBlock.resourceId) {
                            R.string.common_string_empty -> {
                                canAnyTileMove = true
                            }
                            block.resourceId -> {
                                val nextBlock = block.getNextBlock()
                                if (nextBlock != null) {
                                    canAnyTileMove = true
                                }
                            }
                            else -> {
                                break
                            }
                        }
                        indexAfterSwipeAction =
                            getPreviousPositionIndex(
                                Pair(
                                    indexAfterSwipeAction.first,
                                    indexAfterSwipeAction.second
                                ),
                                swipeType,
                                rowBlockTypes.size
                            )
                    }
                }
            }
        }
        return canAnyTileMove
    }

    override fun toString(): String {
        var string = "########################"
        data.forEach { r ->
            string += "\n"
            r.forEach { c ->
                string += " ${c.value} "
            }
        }
        string += "\n########################"
        Log.d("CurrentGameData", string)
        return string
    }

    companion object {
        fun getGameDataUsingGameSize(gameSize: GameSize): GameData {
            val gameData = GameData(
                data = MutableList(gameSize.gameType) {
                    MutableList(gameSize.gameType) { BlockEmpty() }
                }
            )
            val range = 0 until gameSize.gameType * gameSize.gameType
            val randomBlock1 = range.random()
            var randomBlock2 = range.random()
            while (randomBlock1 == randomBlock2) {
                randomBlock2 = range.random()
            }

            val row1 = randomBlock1 / gameSize.gameType
            val colum1 = randomBlock1 % gameSize.gameType

            val row2 = randomBlock2 / gameSize.gameType
            val colum2 = randomBlock2 % gameSize.gameType

            gameData.data[row1][colum1] = getBlock2or4AtRandom()
            gameData.data[row2][colum2] = getBlock2or4AtRandom()

            return gameData
        }

        fun getUpdatedGameDataFromSwipeUpAction(gameData: GameData): Pair<GameData, Boolean> {
            val newGame = gameData.copy()
            val newUpdate = makeNewGameFromSwipeAction(newGame.data, SwipeType.UP)
            return Pair(GameData(newUpdate.first), newUpdate.second)
        }

        fun getUpdatedGameDataFromSwipeLeftAction(gameData: GameData): Pair<GameData, Boolean> {
            val newGame = gameData.copy()
            val newUpdate = makeNewGameFromSwipeAction(newGame.data, SwipeType.LEFT)
            return Pair(GameData(newUpdate.first), newUpdate.second)
        }

        fun getUpdatedGameDataFromSwipeRightAction(gameData: GameData): Pair<GameData, Boolean> {
            val newGame = gameData.copy()
            val newUpdate = makeNewGameFromSwipeAction(newGame.data, SwipeType.RIGHT)
            return Pair(GameData(newUpdate.first), newUpdate.second)
        }

        fun getUpdatedGameDataFromSwipeDownAction(gameData: GameData): Pair<GameData, Boolean> {
            val newGame = gameData.copy()
            val newUpdate = makeNewGameFromSwipeAction(newGame.data, SwipeType.DOWN)
            return Pair(GameData(newUpdate.first), newUpdate.second)
        }

        private fun makeNewGameFromSwipeAction(
            newGame: MutableList<MutableList<BlockType>>,
            swipeType: SwipeType
        ): Pair<MutableList<MutableList<BlockType>>, Boolean> {
            val updatedSwipeAction = when (swipeType) {
                SwipeType.RIGHT, SwipeType.DOWN -> SwipeType.UP
                else -> swipeType
            }
            val array = reverse2DArray(newGame, swipeType)

            val listOfPairUpdatedInThisSwipe = mutableListOf<Pair<Int, Int>>()
            var hasAnyTileMoved = false
            array.forEachIndexed { rowIndex, rowBlockTypes ->
                rowBlockTypes.forEachIndexed { columnIndex, block ->
                    if (block.resourceId != R.string.common_string_empty) {
                        var indexAfterSwipeAction =
                            getPreviousPositionIndex(
                                Pair(rowIndex, columnIndex),
                                updatedSwipeAction,
                                rowBlockTypes.size
                            )
                        var currentRowIndex = rowIndex
                        var currentColumnIndex = columnIndex
                        while (indexAfterSwipeAction != null) {
                            val previousBlock =
                                array[indexAfterSwipeAction.first][indexAfterSwipeAction.second]
                            when (previousBlock.resourceId) {
                                R.string.common_string_empty -> {
                                    array[indexAfterSwipeAction.first][indexAfterSwipeAction.second] =
                                        block
                                    array[currentRowIndex][currentColumnIndex] =
                                        BlockEmpty()
                                    hasAnyTileMoved = true
                                }
                                block.resourceId -> {
                                    val nextBlock = block.getNextBlock()
                                    if (nextBlock != null && !listOfPairUpdatedInThisSwipe.contains(
                                            Pair(
                                                indexAfterSwipeAction.first,
                                                indexAfterSwipeAction.second
                                            )
                                        )
                                    ) {
                                        listOfPairUpdatedInThisSwipe.add(
                                            Pair(
                                                indexAfterSwipeAction.first,
                                                indexAfterSwipeAction.second
                                            )
                                        )
                                        array[indexAfterSwipeAction.first][indexAfterSwipeAction.second] =
                                            nextBlock
                                        array[currentRowIndex][currentColumnIndex] =
                                            BlockEmpty()
                                        hasAnyTileMoved = true
                                    }
                                }
                                else -> {
                                    break
                                }
                            }
                            currentRowIndex = indexAfterSwipeAction.first
                            currentColumnIndex = indexAfterSwipeAction.second
                            indexAfterSwipeAction =
                                getPreviousPositionIndex(
                                    Pair(
                                        indexAfterSwipeAction.first,
                                        indexAfterSwipeAction.second
                                    ),
                                    updatedSwipeAction,
                                    rowBlockTypes.size
                                )
                        }
                    }
                }
            }
            val newArray = reverseBack2DArray(array, swipeType)
            return Pair(newArray, hasAnyTileMoved)
        }

        private fun reverse2DArray(
            data: MutableList<MutableList<BlockType>>,
            swipeType: SwipeType
        ): MutableList<MutableList<BlockType>> {
            when (swipeType) {
                SwipeType.DOWN -> {
                    return data.asReversed()
                }
                SwipeType.RIGHT -> {
                    for (i in 0 until (data.size / 2)) {
                        for (j in i until data.size - i - 1) {
                            val temp = data[i][j]
                            data[i][j] = data[j][data.size - 1 - i]
                            data[j][data.size - 1 - i] = data[data.size - 1 - i][data.size - 1 - j]
                            data[data.size - 1 - i][data.size - 1 - j] = data[data.size - 1 - j][i]
                            data[data.size - 1 - j][i] = temp
                        }
                    }
                    return data
                }
                else -> return data
            }
        }

        private fun reverseBack2DArray(
            data: MutableList<MutableList<BlockType>>,
            swipeType: SwipeType
        ): MutableList<MutableList<BlockType>> {
            when (swipeType) {
                SwipeType.DOWN -> return data.asReversed()
                SwipeType.RIGHT -> {
                    for (i in 0 until (data.size / 2)) {
                        for (j in i until data.size - i - 1) {
                            val temp = data[i][j]
                            data[i][j] = data[data.size - 1 - j][i]
                            data[data.size - 1 - j][i] = data[data.size - 1 - i][data.size - 1 - j]
                            data[data.size - 1 - i][data.size - 1 - j] = data[j][data.size - 1 - i]
                            data[j][data.size - 1 - i] = temp
                        }
                    }
                    return data
                }
                else -> return data
            }
        }

        private fun getPreviousPositionIndex(
            currentIndex: Pair<Int, Int>,
            swipeType: SwipeType,
            gameSize: Int
        ): Pair<Int, Int>? {
            val updatedGameSize = gameSize - 1
            when (swipeType) {
                SwipeType.LEFT -> {
                    return if (currentIndex.second == 0) {
                        null
                    } else {
                        Pair(currentIndex.first, currentIndex.second - 1)
                    }
                }
                SwipeType.RIGHT -> {
                    return if (currentIndex.second == updatedGameSize) {
                        null
                    } else {
                        Pair(currentIndex.first, currentIndex.second + 1)
                    }
                }
                SwipeType.UP -> {
                    return if (currentIndex.first == 0) {
                        null
                    } else {
                        Pair(currentIndex.first - 1, currentIndex.second)
                    }
                }
                SwipeType.DOWN -> {
                    return if (currentIndex.first == updatedGameSize) {
                        null
                    } else {
                        Pair(currentIndex.first + 1, currentIndex.second)
                    }
                }
            }
        }
    }
}
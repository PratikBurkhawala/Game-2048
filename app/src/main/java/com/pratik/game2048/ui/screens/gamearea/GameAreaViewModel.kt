package com.pratik.game2048.ui.screens.gamearea

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pratik.game2048.domain.model.GameStats
import com.pratik.game2048.domain.usecase.CloseGameById
import com.pratik.game2048.domain.usecase.GetCurrentGame
import com.pratik.game2048.domain.usecase.SaveGameStats
import com.pratik.game2048.models.*
import com.pratik.game2048.ui.addElementAtRandom
import com.pratik.game2048.ui.getFormatTime
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.onEach

class GameAreaViewModel(
    private val saveGameStats: SaveGameStats,
    private val getCurrentGame: GetCurrentGame,
    private val closeGameById: CloseGameById
) : ViewModel() {

    private val _userName = mutableStateOf("NAME")
    val userName = _userName

    private val _currentGameSize: MutableState<GameSize?> = mutableStateOf(null)

    private val _currentWinningNumber: MutableState<WinningNumber?> =
        mutableStateOf(null)
    val currentWinningNumber = _currentWinningNumber

    private val _timer = mutableStateOf("HH:MM:SS")
    val timer = _timer

    private val _moves = mutableStateOf(0)
    val moves = _moves

    private val _highestScore = mutableStateOf(0)
    val highestScore = _highestScore

    private val _counter = mutableStateOf(0)

    private var currentGameId = ""

    private var job: Job? = null

    private var _showNewGameDialog = mutableStateOf(false)
    val showNewGameDialog = _showNewGameDialog

    private var _gameData: MutableState<GameData?> =
        mutableStateOf(null)
    val gameData = _gameData

    private var _gameStatus = mutableStateOf(GameStatus.GAME_NOT_STARTED)
    val gameStatus = _gameStatus

    init {
        viewModelScope.launch {
            loadExistingGameOrStartNewOne()
        }
    }

    fun onEvent(event: GameAreaEvent) {
        when (event) {
            is GameAreaEvent.StartGame -> {
                viewModelScope.launch {
                    showNewGameDialog(false)
                    if (currentGameId != "") {
                        closeGameById.invoke(currentGameId)
                        currentGameId = ""
                    }
                    saveNewGame(
                        event.name,
                        event.gameSize,
                        event.winningNumber.value
                    )
                    val newGame = getCurrentGame()
                    if (newGame != null) {
                        updateCurrentGameStats(newGame)
                        updateGameFromStatus()
                    }
                }
            }
            is GameAreaEvent.PauseGame -> {
                viewModelScope.launch {
                    saveExistingGame()
                }
            }
            is GameAreaEvent.ResumeGame -> {
                viewModelScope.launch {
                    loadExistingGameOrStartNewOne()
                    updateGameFromStatus()
                }
            }
            is GameAreaEvent.NewGameButtonClick -> {
                viewModelScope.launch {
                    saveExistingGame()
                    showNewGameDialog(true)
                }
            }
            is GameAreaEvent.NewGameDialogDismissedWithoutAction -> {
                viewModelScope.launch {
                    showNewGameDialog(false)
                    loadExistingGameOrStartNewOne()
                }
            }
            is GameAreaEvent.OnLeftSwipeAction -> {
                viewModelScope.launch {
                    updateGameWithSwipe(SwipeType.LEFT)
                    updateGameFromStatus()
                }
            }
            is GameAreaEvent.OnRightSwipeAction -> {
                viewModelScope.launch {
                    updateGameWithSwipe(SwipeType.RIGHT)
                    updateGameFromStatus()
                }
            }
            is GameAreaEvent.OnUpSwipeAction -> {
                viewModelScope.launch {
                    updateGameWithSwipe(SwipeType.UP)
                    updateGameFromStatus()
                }
            }
            is GameAreaEvent.OnDownSwipeAction -> {
                viewModelScope.launch {
                    updateGameWithSwipe(SwipeType.DOWN)
                    updateGameFromStatus()
                }
            }
        }
    }

    private fun updateGameWithSwipe(swipeType: SwipeType) {
        val currentGame = gameData.value
        _gameData.value = null
        if (currentGame != null) {
            val newGame = when (swipeType) {
                SwipeType.UP -> {
                    GameData.getUpdatedGameDataFromSwipeUpAction(currentGame)
                }
                SwipeType.DOWN -> {
                    GameData.getUpdatedGameDataFromSwipeDownAction(currentGame)
                }
                SwipeType.LEFT -> {
                    GameData.getUpdatedGameDataFromSwipeLeftAction(currentGame)
                }
                SwipeType.RIGHT -> {
                    GameData.getUpdatedGameDataFromSwipeRightAction(currentGame)
                }
            }
            if (!newGame.second) {
                // no need to add new
                _gameData.value = currentGame
            } else {
                val newGameWithAddedElement =
                    _currentGameSize.value?.gameType?.let { addElementAtRandom(newGame.first, it) }
                if (newGameWithAddedElement == null) {
                    // no free space to add new element
                    _gameData.value = currentGame
                } else {
                    _moves.value = moves.value + 1
                    _gameData.value = newGameWithAddedElement
                    _highestScore.value = gameData.value?.getHighestNumber() ?: 0
                }
            }
        }
    }

    private suspend fun loadExistingGameOrStartNewOne() = withContext(Dispatchers.IO) {
        val anyExistingGame = getCurrentGame.invoke()
        if (anyExistingGame == null) {
            showNewGameDialog(true)
        } else {
            updateCurrentGameStats(anyExistingGame)
            updateGameFromStatus()
        }
    }

    private suspend fun showNewGameDialog(show: Boolean) = withContext(Dispatchers.Main) {
        _showNewGameDialog.value = show
    }

    private suspend fun updateCurrentGameStats(gameStats: GameStats) =
        withContext(Dispatchers.Main) {
            currentGameId = gameStats.uid
            _userName.value = gameStats.name
            _currentGameSize.value =
                GameSize.values().find { it.gameType == gameStats.gameSize }
            _gameData.value = gameStats.gameData
            _currentWinningNumber.value =
                WinningNumber.values().find { it.value == gameStats.winningNumber }
            _counter.value = gameStats.time
            _timer.value = _counter.value.getFormatTime()
            _moves.value = gameStats.moves
            _highestScore.value = gameStats.highestScore
            _gameStatus.value = GameStatus.GAME_ONGOING
            startTimer(_counter.value)
        }

    private suspend fun updateGameFromStatus() = withContext(Dispatchers.IO) {
        _gameStatus.value = checkGameStatus()
        gameData.value?.toString()
        when (gameStatus.value) {
            GameStatus.GAME_FINISHED -> {
                saveExistingGame()
            }
            GameStatus.GAME_OVER -> {
                saveExistingGame()
            }
            else -> {}
        }
    }

    private fun startTimer(initialTime: Int = 0) {
        stopTime()
        job = viewModelScope.launch {
            val timer = (0..Int.MAX_VALUE)
                .asSequence()
                .asFlow()
                .onEach { delay(1_000) }
            timer.collect {
                _counter.value = initialTime + it
                _timer.value = _counter.value.getFormatTime()
            }
        }
    }

    private fun stopTime() {
        if (job?.isActive == true) {
            job?.cancel()
            job = null
        }
    }

    private suspend fun saveNewGame(name: String, gameSize: GameSize, winningNumber: Int) =
        withContext(Dispatchers.IO) {
            val newGame = GameData.getGameDataUsingGameSize(gameSize)
            val gameStats = GameStats(
                name = name,
                gameSize = gameSize.gameType,
                highestScore = newGame.getHighestNumber(),
                winningNumber = winningNumber,
                gameData = newGame,
                moves = 0,
                time = 0,
                isCurrent = true
            )
            saveGameStats.saveNewGame(gameStats)
        }

    private suspend fun saveExistingGame() =
        withContext(Dispatchers.IO) {
            if (currentGameId != "") {
                stopTime()
                gameData.value?.let {
                    saveGameStats.saveExistingGame(
                        currentGameId,
                        it,
                        highestScore.value,
                        moves.value,
                        _counter.value
                    )
                }
            }
        }

    private suspend fun getCurrentGame() = withContext(Dispatchers.IO) {
        return@withContext getCurrentGame.invoke()
    }

    private suspend fun checkGameStatus() = withContext(Dispatchers.IO) {
        return@withContext if (highestScore.value == currentWinningNumber.value?.value) {
            GameStatus.GAME_FINISHED
        } else if (gameData.value?.canGameMoveFurther() == false) {
            GameStatus.GAME_OVER
        } else {
            GameStatus.GAME_ONGOING
        }
    }
}
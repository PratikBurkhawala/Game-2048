package com.pratik.game2048.ui.screens.gamearea

import com.pratik.game2048.models.GameSize
import com.pratik.game2048.models.WinningNumber

sealed class GameAreaEvent {
    data class StartGame(
        val name: String,
        val gameSize: GameSize,
        val winningNumber: WinningNumber
    ) :
        GameAreaEvent()

    object PauseGame : GameAreaEvent()
    object ResumeGame : GameAreaEvent()
    object NewGameButtonClick : GameAreaEvent()
    object NewGameDialogDismissedWithoutAction : GameAreaEvent()
    object OnLeftSwipeAction : GameAreaEvent()
    object OnRightSwipeAction : GameAreaEvent()
    object OnUpSwipeAction : GameAreaEvent()
    object OnDownSwipeAction : GameAreaEvent()
}
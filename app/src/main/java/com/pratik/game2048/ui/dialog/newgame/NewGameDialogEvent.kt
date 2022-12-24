package com.pratik.game2048.ui.dialog.newgame

sealed class NewGameDialogEvent {
    data class EnteredName(val value: String) : NewGameDialogEvent()
    data class OnGameSizeSelected(val value: Int) : NewGameDialogEvent()
    data class OnWinningNumberSelected(val value: Int) : NewGameDialogEvent()
    object OnStartClick : NewGameDialogEvent()
    object Reset : NewGameDialogEvent()
}
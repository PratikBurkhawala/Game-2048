package com.pratik.game2048.ui.dialog.newgame

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.pratik.game2048.models.GameSize
import com.pratik.game2048.models.WinningNumber

class NewGameDialogViewModel : ViewModel() {

    private val _nameField = mutableStateOf("")
    val nameField = _nameField
    private val _nameFieldError = mutableStateOf(false)
    val nameFieldError = _nameFieldError

    private val _selectedGameSize: MutableState<GameSize?> = mutableStateOf(null)
    val selectedGameSize = _selectedGameSize
    private val _selectedGameSizeError = mutableStateOf(false)
    val selectedGameSizeError = _selectedGameSizeError

    private val _selectedWinningNumber: MutableState<WinningNumber?> = mutableStateOf(null)
    val selectedWinningNumber = _selectedWinningNumber
    private val _selectedWinningNumberError = mutableStateOf(false)
    val selectedWinningNumberError = _selectedWinningNumberError

    private val _startGame = mutableStateOf(false)
    val startGame = _startGame

    fun onEvent(event: NewGameDialogEvent) {
        when (event) {
            is NewGameDialogEvent.EnteredName -> {
                _nameField.value = event.value
            }
            is NewGameDialogEvent.OnGameSizeSelected -> {
                _selectedGameSize.value =
                    GameSize.values().find { it.gameType == event.value } ?: GameSize.GameSize4
            }
            is NewGameDialogEvent.OnWinningNumberSelected -> {
                _selectedWinningNumber.value =
                    WinningNumber.values().find { it.value == event.value }
                        ?: WinningNumber.WinningNumber2048
            }
            is NewGameDialogEvent.OnStartClick -> {
                _nameFieldError.value = nameField.value == ""
                _selectedGameSizeError.value = selectedGameSize.value == null
                _selectedWinningNumberError.value = selectedWinningNumber.value == null

                if (!nameFieldError.value && !selectedGameSizeError.value && !selectedWinningNumberError.value) {
                    _startGame.value = true
                }
            }
            is NewGameDialogEvent.Reset -> {
                _nameField.value = ""
                _selectedGameSize.value = null
                _selectedWinningNumber.value = null
                _nameFieldError.value = false
                _selectedGameSizeError.value = false
                _selectedWinningNumberError.value = false
                _startGame.value = false
            }
        }
    }
}
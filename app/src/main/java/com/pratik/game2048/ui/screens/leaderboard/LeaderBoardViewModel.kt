package com.pratik.game2048.ui.screens.leaderboard

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pratik.game2048.domain.model.GameStats
import com.pratik.game2048.domain.usecase.GetAllGame
import kotlinx.coroutines.launch

class LeaderBoardViewModel(
    private val getAllGame: GetAllGame
) : ViewModel() {

    private val _games = mutableStateOf<List<GameStats>>(emptyList())
    val games = _games

    fun onEvent(event: LeaderBoardEvent) {
        when (event) {
            is LeaderBoardEvent.GetAllGames -> {
                viewModelScope.launch {
                    _games.value = getAllGame.invoke()
                }
            }
        }
    }
}
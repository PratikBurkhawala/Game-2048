package com.pratik.game2048.ui.screens.leaderboard

sealed class LeaderBoardEvent {
    object GetAllGames : LeaderBoardEvent()
}
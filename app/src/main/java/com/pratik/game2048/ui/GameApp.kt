package com.pratik.game2048.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pratik.game2048.ui.screens.gamearea.GameArea
import com.pratik.game2048.ui.screens.leaderboard.Leaderboard

@Composable
fun GameApp(
    appState: GameAppState = rememberGameAppState()
) {
    NavHost(
        navController = appState.navController,
        startDestination = Screen.GameArea.route
    ) {
        composable(Screen.GameArea.route) {
            GameArea(onLeaderBoardButtonClick = {
                appState.navigateToLeaderboardScreen()
            })
        }
        composable(Screen.Leaderboard.route) {
            Leaderboard()
        }
    }
}
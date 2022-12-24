package com.pratik.game2048.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

sealed class Screen(val route: String) {
    object GameArea : Screen("gameArea")
    object Leaderboard : Screen("leaderboard")
}

@Composable
fun rememberGameAppState(
    navController: NavHostController = rememberNavController()
) = remember(navController) {
    GameAppState(navController)
}

class GameAppState(
    val navController: NavHostController
) {

    fun navigateToLeaderboardScreen() {
        navController.navigate(Screen.Leaderboard.route)
    }

    fun navigateBack() {
        navController.popBackStack()
    }

}
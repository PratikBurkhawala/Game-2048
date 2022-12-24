package com.pratik.game2048.ui.screens.gamearea

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import com.pratik.game2048.R
import com.pratik.game2048.constants.Block2048Color
import com.pratik.game2048.models.GameStatus
import com.pratik.game2048.ui.components.*
import com.pratik.game2048.ui.dialog.newgame.NewGameDialog
import com.pratik.game2048.ui.theme.AppBackgroundColor
import com.pratik.game2048.ui.theme.GameAreaColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun GameArea(
    viewModel: GameAreaViewModel = koinViewModel(),
    onLeaderBoardButtonClick: () -> Unit
) {

    val currentWinningNumber = viewModel.currentWinningNumber.value
    val userName = viewModel.userName.value
    val timer = viewModel.timer.value
    val moves = viewModel.moves.value
    val highestScore = viewModel.highestScore.value
    val newGameDialog = viewModel.showNewGameDialog.value
    val gameData = viewModel.gameData.value
    val gameStatus = viewModel.gameStatus.value

    ComposableLifecycle(onEvent = { _, event ->
        if (event == Lifecycle.Event.ON_PAUSE) {
            viewModel.onEvent(GameAreaEvent.PauseGame)
        }

        if (event == Lifecycle.Event.ON_RESUME) {
            viewModel.onEvent(GameAreaEvent.ResumeGame)
        }
    })

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackgroundColor)
            .padding(16.dp),
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppBackgroundColor)
                    .padding(paddingValues)
            ) {
                Row {
                    ColoredBlock(
                        color = Color(Block2048Color),
                        canClick = false,
                        isSelected = false,
                        onSelected = {}
                    ) {
                        Text(
                            text = stringResource(
                                id = currentWinningNumber?.resourceId ?: R.string.common_string_2048
                            ),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(
                                horizontal = 16.dp,
                                vertical = 24.dp
                            )
                        )
                    }

                    Box(modifier = Modifier.padding(16.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(1f)
                    ) {
                        GameButton(
                            title = stringResource(id = R.string.game_area_screen_button_new),
                            onClick = {
                                viewModel.onEvent(GameAreaEvent.NewGameButtonClick)
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                        GameButton(
                            title = stringResource(id = R.string.game_area_screen_button_leaderboard),
                            onClick = onLeaderBoardButtonClick,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Box(modifier = Modifier.padding(vertical = 28.dp))

                SquareBlock(backgroundColor = GameAreaColor, content = {
                    if (gameData != null) {
                        GameMainArea(gameData) {
                            SwipeDetectorBox(
                                swipeEnable = !(gameStatus == GameStatus.GAME_FINISHED || gameStatus == GameStatus.GAME_OVER),
                                onSwipeLeft = { viewModel.onEvent(GameAreaEvent.OnLeftSwipeAction) },
                                onSwipeRight = { viewModel.onEvent(GameAreaEvent.OnRightSwipeAction) },
                                onSwipeUp = { viewModel.onEvent(GameAreaEvent.OnUpSwipeAction) },
                                onSwipeDown = { viewModel.onEvent(GameAreaEvent.OnDownSwipeAction) }
                            )
                        }
                    }
                })

                Box(modifier = Modifier.padding(vertical = 8.dp))

                Row(modifier = Modifier.padding(horizontal = 8.dp)) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = userName,
                            color = Color.Gray,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = stringResource(
                                id = R.string.game_area_screen_highest_score_placeholder,
                                highestScore.toString()
                            ),
                            color = Color.Gray,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(
                                id = R.string.game_area_screen_moves_placeholder,
                                moves
                            ),
                            color = Color.Gray,
                            fontSize = 14.sp,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = timer,
                            color = Color.Gray,
                            fontSize = 14.sp,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(8.dp))

                if (gameStatus == GameStatus.GAME_OVER || gameStatus == GameStatus.GAME_FINISHED) {
                    val scroll = rememberScrollState(0)
                    Text(
                        text = if (gameStatus == GameStatus.GAME_OVER) stringResource(id = R.string.game_area_screen_game_status_over) else stringResource(
                            id = R.string.game_area_screen_game_status_success
                        ),
                        color = if (gameStatus == GameStatus.GAME_OVER) Color.Red else Color.Blue,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .verticalScroll(scroll)
                    )
                }

            }
        }
    )

    if (newGameDialog) {
        viewModel.onEvent(GameAreaEvent.PauseGame)
        NewGameDialog(onDismissRequest = {
            viewModel.onEvent(GameAreaEvent.NewGameDialogDismissedWithoutAction)
        }, onStartClick = { name, gameSize, winningNumber ->
            viewModel.onEvent(GameAreaEvent.StartGame(name, gameSize, winningNumber))
        })
    }
}

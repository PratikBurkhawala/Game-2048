package com.pratik.game2048.ui.screens.leaderboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.pratik.game2048.constants.Block64Color
import com.pratik.game2048.ui.components.ColoredBlock
import com.pratik.game2048.ui.components.ComposableLifecycle
import com.pratik.game2048.ui.components.GameStatsItem
import com.pratik.game2048.ui.theme.AppBackgroundColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun Leaderboard(
    viewModel: LeaderBoardViewModel = koinViewModel()
) {
    val list = viewModel.games.value

    ComposableLifecycle(onEvent = { _, event ->
        if (event == Lifecycle.Event.ON_RESUME) {
            viewModel.onEvent(LeaderBoardEvent.GetAllGames)
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
                    .fillMaxWidth()
                    .padding(paddingValues)
            ) {
                ColoredBlock(
                    color = Color(Block64Color),
                    canClick = false,
                    onSelected = {},
                ) {
                    Text(
                        text = stringResource(id = R.string.game_area_screen_button_leaderboard),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    )
                }

                Spacer(modifier = Modifier.padding(16.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(
                        items = list,
                        key = { game ->
                            game.uid
                        }
                    ) { game ->
                        GameStatsItem(game = game, modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
    )
}
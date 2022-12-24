package com.pratik.game2048.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pratik.game2048.R
import com.pratik.game2048.constants.Block16Color
import com.pratik.game2048.domain.model.GameStats
import com.pratik.game2048.models.GameSize
import com.pratik.game2048.models.WinningNumber
import com.pratik.game2048.ui.getFormatTime

@Composable
fun GameStatsItem(
    game: GameStats,
    modifier: Modifier = Modifier
) {

    val gameSizeResourceId = GameSize.values().find { it.gameType == game.gameSize }?.resourceId
        ?: R.string.common_string_game_size_4
    val winningNumberResourceId =
        WinningNumber.values().find { it.value == game.winningNumber }?.resourceId
            ?: R.string.common_string_2048
    val timeTaken = game.time.getFormatTime()

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp,
        backgroundColor = Color(Block16Color),
        contentColor = Color.Blue
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = game.name,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(id = gameSizeResourceId),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "${game.highestScore} / ${stringResource(id = winningNumberResourceId)}",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = timeTaken,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(
                        id = R.string.game_area_screen_moves_placeholder,
                        game.moves
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
package com.pratik.game2048.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.pratik.game2048.R
import com.pratik.game2048.models.GameData
import com.pratik.game2048.models.GameSize

@Composable
fun GameMainArea(
    gameData: GameData,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier.clickable(
            enabled = false,
            indication = null,
            interactionSource = MutableInteractionSource(),
            onClick = {}
        )
    ) {
        LazyVerticalGrid(
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center,
            columns = GridCells.Fixed(gameData.data.size),
        ) {
            items(gameData.get1DListFrom2D()) { item ->
                SquareBlock(content = {
                    if (gameData.data.size == GameSize.GameSize4.gameType) {
                        Text(
                            text = stringResource(item.resourceId),
                            fontSize = 30.sp,
                            color = Color(item.textColor),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    } else {
                        val fontSize =
                            if (item.resourceId == R.string.common_string_1024 || item.resourceId == R.string.common_string_2048 || item.resourceId == R.string.common_string_4096) {
                                12.sp
                            } else {
                                16.sp
                            }
                        Text(
                            text = stringResource(item.resourceId),
                            fontSize = fontSize,
                            color = Color(item.textColor),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }
                }, backgroundColor = Color(item.blockBackgroundColor))
            }
        }

        content()
    }
}
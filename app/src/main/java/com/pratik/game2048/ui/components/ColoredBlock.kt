package com.pratik.game2048.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ColoredBlock(
    color: Color,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    canClick: Boolean,
    onSelected: () -> Unit,
    content: @Composable () -> Unit,
) {
    val updatedModifier = if (isSelected) {
        modifier
            .background(color, shape = RoundedCornerShape(8.dp))
            .border(
                width = 2.dp,
                color = Color.Red,
                shape = RoundedCornerShape(5.dp)
            )
    } else {
        modifier
            .background(color, shape = RoundedCornerShape(8.dp))
    }

    Box(
        modifier = updatedModifier.clickable(enabled = canClick, onClick = {
            onSelected()
        })
    ) {
        content()
    }
}
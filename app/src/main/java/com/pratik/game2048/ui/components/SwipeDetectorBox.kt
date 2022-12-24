package com.pratik.game2048.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeDetectorBox(
    swipeEnable: Boolean = true,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit,
    onSwipeUp: () -> Unit,
    onSwipeDown: () -> Unit
) {

    val canProvideUpdatedEvent = remember {
        mutableStateOf(false)
    }

    var sizeHorizontal by remember { mutableStateOf(IntSize(100, 100)) }
    val swipeStateHorizontal = rememberSwipeableState(0)
    val anchorsHorizontal = mapOf(0f to 0, sizeHorizontal.width.toFloat() to 1)

    var sizeVertical by remember { mutableStateOf(IntSize(100, 100)) }
    val swipeStateVertical = rememberSwipeableState(0)
    val anchorsVertical = mapOf(0f to 0, sizeVertical.height.toFloat() to 1)

    LaunchedEffect(true) {
        delay(1_000)
        canProvideUpdatedEvent.value = true
    }

    if (swipeStateVertical.isAnimationRunning) {
        DisposableEffect(Unit) {
            onDispose {
                when (swipeStateVertical.currentValue) {
                    1 -> {
                        if (canProvideUpdatedEvent.value) {
                            onSwipeDown()
                        }
                    }
                    0 -> {
                        if (canProvideUpdatedEvent.value) {
                            onSwipeUp()
                        }
                    }
                    else -> {
                        return@onDispose
                    }
                }
            }
        }
    }

    if (swipeStateHorizontal.isAnimationRunning) {
        DisposableEffect(Unit) {
            onDispose {
                when (swipeStateHorizontal.currentValue) {
                    1 -> {
                        if (canProvideUpdatedEvent.value) {
                            onSwipeRight()
                        }
                    }
                    0 -> {
                        if (canProvideUpdatedEvent.value) {
                            onSwipeLeft()
                        }
                    }
                    else -> {
                        return@onDispose
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .swipeable(
                enabled = swipeEnable,
                state = swipeStateHorizontal,
                anchors = anchorsHorizontal,
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Horizontal
            )
            .onSizeChanged {
                sizeHorizontal = it
            }
            .background(color = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .swipeable(
                    enabled = swipeEnable,
                    state = swipeStateVertical,
                    anchors = anchorsVertical,
                    thresholds = { _, _ -> FractionalThreshold(0.5f) },
                    orientation = Orientation.Vertical
                )
                .onSizeChanged {
                    sizeVertical = it
                }
                .background(color = Color.Transparent)
        )
    }
}
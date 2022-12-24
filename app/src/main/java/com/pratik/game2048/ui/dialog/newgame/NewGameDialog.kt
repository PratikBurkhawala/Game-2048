package com.pratik.game2048.ui.dialog.newgame

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pratik.game2048.R
import com.pratik.game2048.constants.*
import com.pratik.game2048.models.GameSize
import com.pratik.game2048.models.WinningNumber
import com.pratik.game2048.ui.components.ColoredBlock
import com.pratik.game2048.ui.components.DottedShape
import com.pratik.game2048.ui.components.GameButton
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NewGameDialog(
    viewModel: NewGameDialogViewModel = koinViewModel(),
    onDismissRequest: () -> Unit,
    onStartClick: (String, GameSize, WinningNumber) -> Unit
) {

    val name = viewModel.nameField.value
    val nameError = viewModel.nameFieldError.value
    val selectedGameSize = viewModel.selectedGameSize.value
    val selectedGameSizeError = viewModel.selectedGameSizeError.value
    val selectedWinningNumber = viewModel.selectedWinningNumber.value
    val selectedWinningNumberError = viewModel.selectedWinningNumberError.value
    val startGame = viewModel.startGame.value

    val gameSize4 = GameSize.GameSize4
    val gameSize4String = stringResource(id = gameSize4.resourceId)
    val gameSize8 = GameSize.GameSize8
    val gameSize8String = stringResource(id = gameSize8.resourceId)
    val winningNumber2048 = WinningNumber.WinningNumber2048
    val winningNumber2048String = stringResource(id = winningNumber2048.resourceId)
    val winningNumber4096 = WinningNumber.WinningNumber4096
    val winningNumber4096String = stringResource(id = winningNumber4096.resourceId)

    if (startGame) {
        onStartClick(
            name,
            selectedGameSize ?: GameSize.GameSize4,
            selectedWinningNumber ?: WinningNumber.WinningNumber2048
        )
        viewModel.onEvent(NewGameDialogEvent.Reset)
    }

    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = {
            viewModel.onEvent(NewGameDialogEvent.Reset)
            onDismissRequest()
        }) {
        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.new_game_dialog_title),
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )

                TextField(
                    value = name,
                    singleLine = true,
                    onValueChange = { newText ->
                        viewModel.onEvent(NewGameDialogEvent.EnteredName(newText))
                    },
                    label = {
                        Text(text = stringResource(id = R.string.new_game_dialog_name_text_field_label))
                    },
                    isError = nameError,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        focusedIndicatorColor = Color.Black,
                        unfocusedIndicatorColor = Color.Black,
                        disabledIndicatorColor = Color.Black,
                        errorCursorColor = Color.Red,
                        errorIndicatorColor = Color.Red,
                        errorLabelColor = Color.Red,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                if (nameError) {
                    Text(
                        text = stringResource(id = R.string.new_game_dialog_name_text_field_error_label),
                        color = Color.Red
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(id = R.string.new_game_dialog_game_size_label),
                        textAlign = TextAlign.Start,
                        color = Color.Gray,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )

                    Row(modifier = Modifier.fillMaxWidth()) {
                        ColoredBlock(
                            color = Color(Block4Color),
                            canClick = true,
                            onSelected = {
                                viewModel.onEvent(NewGameDialogEvent.OnGameSizeSelected(gameSize4.gameType))
                            },
                            isSelected = selectedGameSize == gameSize4,
                            modifier = Modifier.weight(0.8f)
                        ) {
                            Text(
                                text = gameSize4String,
                                textAlign = TextAlign.Center,
                                color = Color.Black,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .weight(0.2f)
                        )

                        ColoredBlock(
                            color = Color(Block8Color),
                            canClick = true,
                            onSelected = {
                                viewModel.onEvent(NewGameDialogEvent.OnGameSizeSelected(gameSize8.gameType))
                            },
                            isSelected = selectedGameSize == gameSize8,
                            modifier = Modifier.weight(0.8f)
                        ) {
                            Text(
                                text = gameSize8String,
                                textAlign = TextAlign.Center,
                                color = Color.Black,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            )
                        }
                    }
                    if (selectedGameSizeError) {
                        Text(
                            text = stringResource(id = R.string.new_game_dialog_game_size_error_label),
                            color = Color.Red
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )

                Box(
                    Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(Color.Gray, shape = DottedShape(step = 10.dp))
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(id = R.string.new_game_dialog_winning_number_label),
                        textAlign = TextAlign.Start,
                        color = Color.Gray,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )

                    Row(modifier = Modifier.fillMaxWidth()) {
                        ColoredBlock(
                            color = Color(Block2048Color),
                            canClick = true,
                            isSelected = selectedWinningNumber == winningNumber2048,
                            onSelected = {
                                viewModel.onEvent(
                                    NewGameDialogEvent.OnWinningNumberSelected(
                                        winningNumber2048.value
                                    )
                                )
                            },
                            modifier = Modifier.weight(0.8f)
                        ) {
                            Text(
                                text = winningNumber2048String,
                                textAlign = TextAlign.Center,
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .weight(0.2f)
                        )

                        ColoredBlock(
                            color = Color(Block4096Color),
                            canClick = true,
                            isSelected = selectedWinningNumber == winningNumber4096,
                            onSelected = {
                                viewModel.onEvent(
                                    NewGameDialogEvent.OnWinningNumberSelected(
                                        winningNumber4096.value
                                    )
                                )
                            },
                            modifier = Modifier.weight(0.8f)
                        ) {
                            Text(
                                text = winningNumber4096String,
                                textAlign = TextAlign.Center,
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            )
                        }
                    }
                    if (selectedWinningNumberError) {
                        Text(
                            text = stringResource(id = R.string.new_game_dialog_winning_number_error_label),
                            color = Color.Red
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )

                GameButton(
                    title = stringResource(id = R.string.new_game_dialog_start_button_label),
                    onClick = {
                        viewModel.onEvent(NewGameDialogEvent.OnStartClick)
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}
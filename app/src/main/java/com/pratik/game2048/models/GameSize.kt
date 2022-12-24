package com.pratik.game2048.models

import com.pratik.game2048.R

enum class GameSize(val resourceId: Int, val gameType: Int) {
    GameSize4(R.string.common_string_game_size_4, 4),
    GameSize8(R.string.common_string_game_size_8, 8)
}
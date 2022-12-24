package com.pratik.game2048.models

import com.pratik.game2048.R

enum class WinningNumber(val resourceId: Int, val value: Int) {
    WinningNumber2048(R.string.common_string_2048, 2048),
    WinningNumber4096(R.string.common_string_4096, 4096)
}
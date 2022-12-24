package com.pratik.game2048.models

import com.pratik.game2048.R
import com.pratik.game2048.constants.*

@kotlinx.serialization.Serializable
sealed class BlockType(
    val value: Int,
    val resourceId: Int,
    val blockBackgroundColor: Long,
    val textColor: Long
)

fun BlockType.getNextBlock(): BlockType? {
    return when (this.resourceId) {
        R.string.common_string_2 -> Block4()
        R.string.common_string_4 -> Block8()
        R.string.common_string_8 -> Block16()
        R.string.common_string_16 -> Block32()
        R.string.common_string_32 -> Block64()
        R.string.common_string_64 -> Block128()
        R.string.common_string_128 -> Block256()
        R.string.common_string_256 -> Block512()
        R.string.common_string_512 -> Block1024()
        R.string.common_string_1024 -> Block2048()
        R.string.common_string_2048 -> Block4096()
        else -> null
    }
}

@kotlinx.serialization.Serializable
class BlockEmpty : BlockType(
    value = 0,
    resourceId = R.string.common_string_empty,
    blockBackgroundColor = BlockEmptyColor,
    textColor = BlockTextColorDark
)

@kotlinx.serialization.Serializable
class Block2 : BlockType(
    value = 2,
    resourceId = R.string.common_string_2,
    blockBackgroundColor = Block2Color,
    textColor = BlockTextColorDark
)

@kotlinx.serialization.Serializable
class Block4 : BlockType(
    value = 4,
    resourceId = R.string.common_string_4,
    blockBackgroundColor = Block4Color,
    textColor = BlockTextColorDark
)

@kotlinx.serialization.Serializable
class Block8 : BlockType(
    value = 8,
    resourceId = R.string.common_string_8,
    blockBackgroundColor = Block8Color,
    textColor = BlockTextColorLight
)

@kotlinx.serialization.Serializable
class Block16 : BlockType(
    value = 16,
    resourceId = R.string.common_string_16,
    blockBackgroundColor = Block16Color,
    textColor = BlockTextColorLight
)

@kotlinx.serialization.Serializable
class Block32 : BlockType(
    value = 32,
    resourceId = R.string.common_string_32,
    blockBackgroundColor = Block32Color,
    textColor = BlockTextColorLight
)

@kotlinx.serialization.Serializable
class Block64 : BlockType(
    value = 64,
    resourceId = R.string.common_string_64,
    blockBackgroundColor = Block64Color,
    textColor = BlockTextColorLight
)

@kotlinx.serialization.Serializable
class Block128 : BlockType(
    value = 128,
    resourceId = R.string.common_string_128,
    blockBackgroundColor = Block128Color,
    textColor = BlockTextColorLight
)

@kotlinx.serialization.Serializable
class Block256 : BlockType(
    value = 256,
    resourceId = R.string.common_string_256,
    blockBackgroundColor = Block256Color,
    textColor = BlockTextColorLight
)

@kotlinx.serialization.Serializable
class Block512 : BlockType(
    value = 512,
    resourceId = R.string.common_string_512,
    blockBackgroundColor = Block512Color,
    textColor = BlockTextColorLight
)

@kotlinx.serialization.Serializable
class Block1024 : BlockType(
    value = 1024,
    resourceId = R.string.common_string_1024,
    blockBackgroundColor = Block1024Color,
    textColor = BlockTextColorLight
)

@kotlinx.serialization.Serializable
class Block2048 : BlockType(
    value = 2048,
    resourceId = R.string.common_string_2048,
    blockBackgroundColor = Block2048Color,
    textColor = BlockTextColorLight
)

@kotlinx.serialization.Serializable
class Block4096 : BlockType(
    value = 4096,
    resourceId = R.string.common_string_4096,
    blockBackgroundColor = Block4096Color,
    textColor = BlockTextColorLight
)
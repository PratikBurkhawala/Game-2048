package com.pratik.game2048.domain.usecase

import com.pratik.game2048.domain.repository.GameRepository

class CloseGameById(
    private val repository: GameRepository
) {
    suspend operator fun invoke(id: String) {
        repository.closeGameById(id)
    }
}
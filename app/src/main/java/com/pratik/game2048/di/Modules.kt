package com.pratik.game2048.di

import androidx.room.Room
import com.pratik.game2048.data.db.GameDatabase
import com.pratik.game2048.data.repository.GameRepositoryImpl
import com.pratik.game2048.domain.repository.GameRepository
import com.pratik.game2048.domain.usecase.CloseGameById
import com.pratik.game2048.domain.usecase.GetAllGame
import com.pratik.game2048.domain.usecase.GetCurrentGame
import com.pratik.game2048.domain.usecase.SaveGameStats
import com.pratik.game2048.ui.dialog.newgame.NewGameDialogViewModel
import com.pratik.game2048.ui.screens.gamearea.GameAreaViewModel
import com.pratik.game2048.ui.screens.leaderboard.LeaderBoardViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(androidApplication(), GameDatabase::class.java, "Game").build()
    }
}

val daoModule = module {
    single {
        get<GameDatabase>().gameStatsDao()
    }
}

val viewModelModules = module {
    viewModel { NewGameDialogViewModel() }
    viewModel { GameAreaViewModel(get(), get(), get()) }
    viewModel { LeaderBoardViewModel(get()) }
}

val repositoryModules = module {
    single<GameRepository> {
        GameRepositoryImpl(get())
    }
}

val useCaseModules = module {
    single {
        SaveGameStats(get())
    }
    single {
        GetCurrentGame(get())
    }
    single {
        CloseGameById(get())
    }
    single {
        GetAllGame(get())
    }
}

val applicationModules = listOf(
    viewModelModules,
    databaseModule,
    daoModule,
    repositoryModules,
    useCaseModules
)
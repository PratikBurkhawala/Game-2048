package com.pratik.game2048

import android.app.Application
import com.pratik.game2048.di.applicationModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GameApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@GameApplication)
            modules(applicationModules)
        }
    }
}
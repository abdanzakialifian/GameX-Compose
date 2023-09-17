package com.games.gamex

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class GameXApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // timber logging
//        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
//        }
    }
}
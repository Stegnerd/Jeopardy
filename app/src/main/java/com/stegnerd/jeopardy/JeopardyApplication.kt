package com.stegnerd.jeopardy

import android.app.Application
import com.stegnerd.jeopardy.util.TimberCrashTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class JeopardyApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG){
         Timber.plant(Timber.DebugTree())
        }else {
            Timber.plant(TimberCrashTree())
        }
    }
}
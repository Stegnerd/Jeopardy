package com.stegnerd.jeopardy.util

import android.util.Log
import timber.log.Timber

/**
 * Logging class that extends timber for release builds
 */
class TimberCrashTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if(priority == Log.VERBOSE || priority == Log.DEBUG){
            return;
        }

        // TODO implement firebase crashlytics
        if(t != null){
            if(priority == Log.ERROR){
                Timber.e(t)
            }else if (priority == Log.WARN){
                Timber.w(t)
            }
        }
    }

}
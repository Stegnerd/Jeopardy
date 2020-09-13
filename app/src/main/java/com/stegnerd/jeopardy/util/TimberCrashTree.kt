package com.stegnerd.jeopardy.util

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

/**
 * Logging class that extends timber for release builds
 */
class TimberCrashTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.VERBOSE  || priority == Log.DEBUG) {
            return;
        }

        if (priority == Log.ERROR) {
            FirebaseCrashlytics.getInstance().log(message)
            // if there was a throwable record it for logs also
            if (t != null) {
                FirebaseCrashlytics.getInstance().recordException(t)
            }
        } else if (priority == Log.WARN) {
            Timber.w(t)
        }
    }

}
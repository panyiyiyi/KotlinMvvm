package com.even.common.utils

import android.util.Log
import timber.log.Timber

/**
 * A [Timber.Tree] for release builds. Automatically infers the tag from the calling class.
 * extends from [Timber.DebugTree] and limit the log print level
 */
open class ReleaseTree : Timber.DebugTree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority < Log.WARN) return
        super.log(priority, tag, message, t)
    }
}
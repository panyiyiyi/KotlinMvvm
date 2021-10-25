package com.even.common.base

import android.app.Application
import com.even.common.utils.InitUtils
import com.even.common.utils.ReleaseTree
import timber.log.Timber

/**
 *  @author  Even
 *  @date   2021/10/19
 */
open class BaseApplication : Application() {
    fun init(hostUrl: String, isDebug: Boolean) {
        Timber.plant(if (isDebug) Timber.DebugTree() else ReleaseTree())
        InitUtils.instance.initNetwork(hostUrl)
        InitUtils.instance.initRouter(this, isDebug = isDebug)
    }
}
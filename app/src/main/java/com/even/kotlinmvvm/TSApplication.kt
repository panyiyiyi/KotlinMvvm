package com.even.kotlinmvvm

import com.even.common.base.BaseApplication

/**
 *  @author  Even
 *  @date   2021/10/20
 */
class TSApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        init("https://www.wanandroid.com/", BuildConfig.DEBUG)
    }
}
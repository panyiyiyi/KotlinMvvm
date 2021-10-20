package com.even.common.request

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 *  @author  Even
 *  @date   2021/10/20
 */
class OkHttpConfig {
    //设置超时时间
    private var readTimeOut = 20L
    private var writeTimeOut = 20L
    private var connectTimeOut = 20L

    //拦截器
    private var interceptors: Array<out Interceptor>? = null

    private var mBuilder = OkHttpClient.Builder()


    fun setReadTimeOut(readTimeOut: Long): OkHttpConfig {
        this.readTimeOut = readTimeOut
        return this
    }

    fun setWriteTimeOut(writeTimeOut: Long): OkHttpConfig {
        this.writeTimeOut = writeTimeOut
        return this
    }

    fun setConnectTimeOut(connectTimeOut: Long): OkHttpConfig {
        this.connectTimeOut = connectTimeOut
        return this
    }

    fun setInterceptors(vararg interceptors: Interceptor): OkHttpConfig {
        this.interceptors = interceptors
        return this
    }

    fun build(): OkHttpClient.Builder {
        setTimeOut()
        addInterceptors()
        return mBuilder
    }

    private fun addInterceptors() {
        this.interceptors?.forEach { mBuilder.addInterceptor(it) }
    }

    //设置超时时间
    private fun setTimeOut() {
        mBuilder.writeTimeout(this.writeTimeOut, TimeUnit.SECONDS)
        mBuilder.connectTimeout(this.connectTimeOut, TimeUnit.SECONDS)
        mBuilder.readTimeout(this.readTimeOut, TimeUnit.SECONDS)
        mBuilder.retryOnConnectionFailure(true)
    }


}
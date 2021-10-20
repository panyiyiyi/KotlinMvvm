package com.even.common.utils

import android.app.Application
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.even.common.request.Api
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 *  @author  Even
 *  @date   2021/10/19
 */
class InitUtils {
    companion object {
        val instance: InitUtils by lazy { InitUtils() }
    }


    fun initRouter(application: Application, isDebug: Boolean) {
        if (isDebug) {
            ARouter.openLog() // 打印日志
            ARouter.openDebug() // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.printStackTrace(); // 打印日志的时候打印线程堆栈
        }
        ARouter.init(application)
    }


    fun initNetwork(hostUrl: String) {
        //网络超时时间（秒）
        val TIMEOUT = 30L

        val okBuilder = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS) //超时时间
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor { message ->
                Timber.tag("Even").d(message)
            }.also { it.level = HttpLoggingInterceptor.Level.BODY })

        val builder = Retrofit.Builder()
            .baseUrl(hostUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        Api.instance.init(okBuilder, builder)
    }
}
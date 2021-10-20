package com.even.common.request

import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 *  @author  Even
 *  @date   2021/10/19
 */
class Api {
    private lateinit var okHttpBuilder: OkHttpClient.Builder
    private lateinit var retrofitBuilder: Retrofit.Builder

    private val httpClient: OkHttpClient by lazy {
        okHttpBuilder.build()
    }
    val retrofit: Retrofit by lazy {
        retrofitBuilder.client(httpClient).build()
    }

    fun init(okHttpBuilder: OkHttpClient.Builder, retrofitBuilder: Retrofit.Builder) {
        this.okHttpBuilder = okHttpBuilder
        this.retrofitBuilder = retrofitBuilder
    }

    fun cancelAll() {
        httpClient.dispatcher.cancelAll()
    }


    companion object {
        var instance = Api()

        inline fun <reified T : Any> createApi(type: Class<T>): T {
            return instance.retrofit.create(type)
        }
    }

}

inline fun <reified service : Any> getService(): service {
    return Api.createApi(service::class.java)
}
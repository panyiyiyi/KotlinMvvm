package com.even.common.ext

import com.even.common.utils.ToastUtils
import kotlin.contracts.contract

/**
 *  @author  Even
 *  @date   2021/10/19
 */
sealed class BaseResponse<out T> {
    data class Success<out T>(val data: T?) : BaseResponse<T>()
    data class Error(val code: String?, val msg: String?) : BaseResponse<Nothing>()
}

fun BaseResponse.Error.showToast() {
    if (msg.isNullOrEmpty()) {
        //无错误信息
    } else {
        ToastUtils.showShort(msg)
    }
}

inline fun <reified T> BaseResponse<T>.isSuccess(): Boolean {
    contract {
        returns(true) implies (this@isSuccess is BaseResponse.Success<T>)
        returns(false) implies (this@isSuccess is BaseResponse.Error)
    }
    return this is BaseResponse.Success<T>
}

inline fun <reified T> BaseResponse<T>.data(): T? {
    return if (this is BaseResponse.Success) {
        this.data
    } else {
        null
    }
}
package com.even.common.ext

import androidx.lifecycle.MutableLiveData
import com.even.common.R
import com.even.common.base.BaseResponseBean
import com.even.common.base.BaseViewModel
import com.even.common.utils.ToastUtils
import com.even.common.utils.UiUtils
import kotlinx.coroutines.Job
import retrofit2.HttpException

/**
 *  @author  Even
 *  @date   2021/10/19
 *  ViewModel网络请求拓展类
 */
inline fun <reified T : Any> BaseViewModel.coroutineCall(
    liveData: MutableLiveData<T>? = null,
    crossinline blockSuccess: (data: T?) -> Unit = {},
    crossinline blockError: (errorCode: String?, errorMsg: String?) -> Any? = { _, _ -> false },
    loadType: LoadType = LoadType.Normal,
    loadStr: String? = UiUtils.getString(R.string.common_loading),
    crossinline call: suspend () -> BaseResponseBean<T>
): Job {
    return launchOnIO { apiCall(liveData, blockSuccess, blockError, loadType, call) }
}

inline fun <reified T> BaseViewModel.coroutineResultCall(
    liveData: MutableLiveData<T>? = null,
    crossinline blockSuccess: (data: BaseResponseBean<T>?) -> Unit = {},
    crossinline blockError: (errorCode: String?, errorMsg: String?) -> Any? = { _, _ -> false },
    loadType: LoadType = LoadType.Normal,
    loadStr: String? = UiUtils.getString(R.string.common_loading),
    crossinline call: suspend () -> BaseResponseBean<T>
): Job {
    return launchOnUI { apiResultCall(liveData, blockSuccess, blockError, loadType, call = call) }
}

inline fun <reified T> BaseViewModel.coroutineResponseCall(
    liveData: MutableLiveData<BaseResponse<T>>? = null,
    crossinline blockResponse: (errorCode: BaseResponse<T>) -> Unit = {},
    loadType: LoadType = LoadType.Normal,
    loadStr: String? = UiUtils.getString(R.string.common_loading),
    crossinline call: suspend () -> BaseResponseBean<T>,
): Job {
    return launchOnIO {
        var response: BaseResponse<T>
        apiResultCall(null, {
            response = if (it?.code == "0") {
                BaseResponse.Success(it.data)
            } else {
                BaseResponse.Error(it?.code, it?.message)
            }
            liveData?.value = response
            blockResponse(response)
        }, { code, msg ->
            response = BaseResponse.Error(code, msg)
            liveData?.value = response
            blockResponse(response)
            true
        }, loadType, call)
    }
}

suspend inline fun <reified T : Any> BaseViewModel.apiCall(
    liveData: MutableLiveData<T>? = null,
    crossinline blockSuccess: (data: T?) -> Unit = {},
    crossinline blockError: (errorCode: String?, errorMsg: String?) -> Any? = { _, _ -> false },
    loadType: LoadType = LoadType.Background,
    crossinline call: suspend () -> BaseResponseBean<T>,
): BaseResponse<T> {
    val apiResultCall = apiResultCall(
        liveData, { data -> blockSuccess.invoke(data?.data) },
        blockError,
        loadType
    ) { call() }
    return when (apiResultCall) {
        is BaseResponse.Success -> {
            BaseResponse.Success(apiResultCall.data?.data)
        }
        is BaseResponse.Error -> {
            return BaseResponse.Error(apiResultCall.code, apiResultCall.msg)
        }
    }
}

suspend inline fun <reified T> BaseViewModel.apiResultCall(
    liveData: MutableLiveData<T>? = null,
    crossinline blockSuccess: (data: BaseResponseBean<T>?) -> Unit = {},
    crossinline blockError: (errorCode: String?, errorMsg: String?) -> Any? = { _, _ -> false },
    loadType: LoadType = LoadType.Background,
    crossinline call: suspend () -> BaseResponseBean<T>
): BaseResponse<BaseResponseBean<T>> {
    try {
        val response = call.invoke()
        if (response.code == "0") {
            //请求成功
            liveData?.postValue(response.data)
            launchOnUI {
                if (loadType == LoadType.Normal) {
                    uiStatus.value = LoadStatus.Success(response.data, loadType)
                }
                blockSuccess.invoke(response)
            }
            return BaseResponse.Success(response)
        } else {
            return BaseResponse.Error(response.code, response.message)
        }
    } catch (e: Exception) {
        launchOnUI {
            if (loadType == LoadType.Normal) {
                uiStatus.value = LoadStatus.Error(null, e.message, loadType)
            }
            val blocker = blockError.invoke(null, e.message)
            if ((blocker is Boolean && blocker) || loadType != LoadType.Normal) return@launchOnUI
            ToastUtils.showShort(e.message ?: "")//显示错误信息
        }
        return if (e is HttpException) {
            BaseResponse.Error(e.code().toString(), e.message())
        } else {
            BaseResponse.Error(null, e.message)
        }
    }
}
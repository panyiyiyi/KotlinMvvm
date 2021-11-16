package com.even.common.ext

import com.even.common.R
import com.even.common.utils.UiUtils

/**
 *  @author  Even
 *  @date   2021/10/19
 */
sealed class LoadStatus<out T : Any>(val loadType: LoadType = LoadType.Normal) {
    //正在加载
    class Loading(
        loadType: LoadType = LoadType.Normal,
        loadStr: String? = UiUtils.getString(R.string.common_loading)
    ) : LoadStatus<Nothing>(loadType)

    class Success<out T : Any>(
        val data: T?,
        loadType: LoadType = LoadType.Normal
    ) : LoadStatus<T>(loadType)

    class Error(
        val code: String? = null,
        val message: String? = null,
        loadType: LoadType = LoadType.Normal
    ) : LoadStatus<Nothing>(loadType)

    class Empty(
        val message: String?,
        loadType: LoadType = LoadType.Normal
    ) : LoadStatus<Nothing>(loadType)

    fun getStr(): String? {
        return when (this) {
            is Error -> this.message
            is Success -> this.data.toString()
            is Loading -> "Loading"
            is Empty -> this.message
        }
    }

}


enum class LoadType {
    Normal,
    Background,
    Refresh,
    LoadMore;

    companion object {
        fun of(loadType: LoadType?): LoadType {
            if (loadType == null) return Normal
            return loadType
        }
    }
}
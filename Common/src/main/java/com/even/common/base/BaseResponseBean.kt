package com.even.common.base

import com.squareup.moshi.JsonClass

/**
 *  @author  Even
 *  @date   2021/10/19
 */
@JsonClass(generateAdapter = true)
data class BaseResponseBean<T>(
    val code: String?,
    val message: String?,
    val data: T?,
)
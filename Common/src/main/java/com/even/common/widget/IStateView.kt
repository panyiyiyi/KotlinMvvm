package com.even.common.widget

import androidx.annotation.DrawableRes

/**
 *  @author  Even
 *  @date   2021/10/19
 */
interface IStateView {
    /**
     *顯示錯誤信息
     */
    fun showError(
        @DrawableRes res: Int? = null,
        tips: String? = null,
        block: (() -> Unit)? = null
    )

    fun showEmpty()

    fun showContent()

    /**
     * 显示网络一次界面
     *@param block 点击重试触发的逻辑
     */
    fun showNetworkError(block: (() -> Unit)? = null)
}
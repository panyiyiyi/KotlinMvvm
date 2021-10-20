package com.even.common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

/**
 *  @author  Even
 *  @date   2021/10/19
 *  請求狀態擴展類
 */
class StateWrapLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStateAttr: Int = 0,
    detStateRes: Int = 0,
    content: View? = null
) : FrameLayout(context, attrs), IStateView {
    init {
//        initView()
    }

    private fun initView(content: View?) {

    }


    override fun showError(res: Int?, tips: String?, block: (() -> Unit)?) {
        TODO("Not yet implemented")
    }

    override fun showEmpty() {
        TODO("Not yet implemented")
    }

    override fun showContent() {
        TODO("Not yet implemented")
    }

    override fun showNetworkError(block: (() -> Unit)?) {
        TODO("Not yet implemented")
    }
}
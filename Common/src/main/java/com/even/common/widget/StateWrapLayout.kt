package com.even.common.widget

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.even.common.utils.BaseConfig

/**
 *  @author  Even
 *  @date   2021/10/19
 *  页面显示状态拓展类
 */
class StateWrapLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStateAttr: Int = 0,
    detStateRes: Int = 0,
    content: View? = null,
    stateLayout: IStateView? = null
) : FrameLayout(context, attrs), IStateView {
    init {
//        initView()
    }

    var content: View? = null

    private val defaultStateView: IStateView by lazy { BaseConfig.defaultStateView(context) }

    private fun initView(content: View?) {

        background = ColorDrawable(Color.TRANSPARENT)
        isClickable = true
        this.content = if (content != null) {
            val layoutParams = content.layoutParams
            val viewGroup = content.parent as? ViewGroup
            viewGroup?.let {
                val indexOfChild = it.indexOfChild(content)
                it.removeView(content)
                addView(content, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
                it.addView(this, indexOfChild, layoutParams)
            } ?: addView(content, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            content
        } else {
            getChildAt(0)
        }

        if (childCount == 1) {

        }


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
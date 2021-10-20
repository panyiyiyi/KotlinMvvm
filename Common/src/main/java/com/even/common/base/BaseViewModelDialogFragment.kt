package com.even.common.base

import android.os.Bundle
import android.view.*
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.even.common.R
import java.lang.reflect.ParameterizedType

/**
 *  @author  Even
 *  @date   2021/10/20
 */
abstract class BaseViewModelDialogFragment<VM : BaseViewModel, T : ViewDataBinding> :
    DialogFragment() {

    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = createViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val window = dialog?.window
        //去掉dialog 默认的padding
        window?.decorView?.setPadding(
            0,
            window.decorView.paddingTop,
            0,
            window.decorView.paddingBottom
        )
        val layoutParams = window?.attributes
        layoutParams?.width = getWidthRate()
        layoutParams?.height = getHeightRate()
        layoutParams?.gravity = getGravity()
        if (getWindowAnimation() != -1) {
            layoutParams?.windowAnimations = getWindowAnimation()
        }
        window?.attributes = layoutParams
//        window.setBackgroundDrawable(ColorDrawable())
        window?.setBackgroundDrawableResource(getLayoutBgDrawable())
        this.isCancelable = isCancelDialogOnOutSize()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun createViewModel(): VM {
        val genericSuperclass = javaClass.genericSuperclass
        val parameterizedType = genericSuperclass as ParameterizedType
        val actualTypeArguments = parameterizedType.actualTypeArguments
        val viewModelClass: Class<VM> = actualTypeArguments[0] as Class<VM>
        return ViewModelProvider(this)[viewModelClass]
    }

    abstract fun getLayoutBgDrawable(): Int

    /**
     * 点击外部区域能否取消
     */
    open fun isCancelDialogOnOutSize() = true

    /**
     * 弹窗默认动画
     * 当为-1的时候不显示动画
     */
    open fun getWindowAnimation(): Int = R.style.dialog_fragment_anim

    /**
     * 对其方式
     */
    open fun getGravity() = Gravity.BOTTOM

    /**
     * 设置弹窗高度比例，默认是自适应，如果要设置为屏幕比例则修改这个比例
     */
    open fun getHeightRate(): Int = WindowManager.LayoutParams.WRAP_CONTENT

    /**
     * 设置弹窗宽度比例，默认是全屏
     */
    open fun getWidthRate(): Int = WindowManager.LayoutParams.MATCH_PARENT
}
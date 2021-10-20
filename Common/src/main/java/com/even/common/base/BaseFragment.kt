package com.even.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

/**
 *  @author  Even
 *  @date   2021/10/20
 *  Fragment基类
 */
abstract class BaseFragment<VM : BaseViewModel, T : ViewDataBinding>(
    @LayoutRes private val layoutId: Int,
    private val variableId: Int
) : Fragment() {
    lateinit var viewModel: VM
    lateinit var dataBinding: T
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = createViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = DataBindingUtil.inflate(layoutInflater, layoutId, container, false)
        dataBinding.lifecycleOwner = this
        dataBinding.setVariable(variableId, viewModel)
        initView()
        initData()
        return dataBinding.root
    }

    fun createViewModel(): VM {
        val genericSuperclass = javaClass.genericSuperclass
        val parameterizedType = genericSuperclass as ParameterizedType
        val actualTypeArguments = parameterizedType.actualTypeArguments
        val viewModelClass: Class<VM> = actualTypeArguments[0] as Class<VM>
        return ViewModelProvider(this)[viewModelClass]
    }

    //init layout
    abstract fun initView()

    //init data
    open fun initData() {}


}
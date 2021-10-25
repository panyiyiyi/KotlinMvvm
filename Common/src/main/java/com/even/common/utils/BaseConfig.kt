package com.even.common.utils

import android.content.Context
import com.even.common.widget.IStateView

/**
 *  @author  Even
 *  @date   2021/10/23
 */
class BaseConfig {
    private lateinit var stateViewCreator: (context: Context) -> IStateView


    companion object {
        private lateinit var instance: BaseConfig
        val defaultStateView
            get() = getInstance().stateViewCreator


        private fun getInstance(): BaseConfig {
            check(this::instance.isInitialized) { "please call build()" }
            return instance
        }
    }


    fun setDefaultStateView(stateViewCreator: (Context) -> IStateView): BaseConfig {
        this.stateViewCreator = stateViewCreator
        return this
    }


    fun build(): BaseConfig {
        instance = BaseConfig()
        return this
    }
}
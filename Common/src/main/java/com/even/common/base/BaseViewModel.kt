package com.even.common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.even.common.ext.LoadStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 *  @author  Even
 *  @date   2021/10/19
 */
open class BaseViewModel : ViewModel() {

    val uiStatus = MutableLiveData<LoadStatus<Any>>()

    fun launchOnUI(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch { block() }
    }

    fun launchOnIO(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(Dispatchers.IO) {
            block()
        }
    }
}
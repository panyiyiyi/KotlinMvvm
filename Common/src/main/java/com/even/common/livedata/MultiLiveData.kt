package com.even.common.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 *  @author  Even
 *  @date   2021/10/20
 *  liveData数据合并类
 */
object MultiLiveData {
    /**
     * observer merge tow livedata and not null
     */
    fun <S, T, Y> ofNotNull(
        _first: LiveData<S>,
        _second: LiveData<T>,
        mapFun: (S, T) -> Y
    ): LiveData<Y> {
        val mediator: MediatorLiveData<Y> = MediatorLiveData()
        mediator.addSource(_first) { first ->
            val second = _second.value
            if (first != null && second != null) {
                mediator.value = mapFun.invoke(first, second)
            }
        }
        mediator.addSource(_second) { second ->
            val first = _first.value
            if (second != null && first != null) {
                mediator.value = mapFun.invoke(first, second)
            }
        }
        return mediator
    }

    /**
     * observer merge tow livedata
     */
    fun <S, T, Y> of(
        _first: LiveData<S>,
        _second: LiveData<T>,
        mapFun: (S?, T?) -> Y
    ): LiveData<Y> {
        val mediator: MediatorLiveData<Y> = MediatorLiveData()
        mediator.addSource(_first) { first ->
            val second = _second.value
            mediator.value = mapFun.invoke(first, second)
        }
        mediator.addSource(_second) { second ->
            val first = _first.value
            mediator.value = mapFun.invoke(first, second)
        }
        return mediator
    }

    /**
     * observer merge tow livedata and not null
     */
    fun <S, T, U, Y> ofNotNull(
        _first: LiveData<S>,
        _second: LiveData<T>,
        _third: LiveData<U>,
        mapFun: (S, T, U) -> Y
    ): LiveData<Y> {
        val mediator: MediatorLiveData<Y> = MediatorLiveData()
        mediator.addSource(_first) { first ->
            val second = _second.value
            val third = _third.value
            if (first != null && second != null && third != null) {
                mediator.value = mapFun.invoke(first, second, third)
            }
        }
        mediator.addSource(_second) { second ->
            val first = _first.value
            val third = _third.value
            if (first != null && second != null && third != null) {
                mediator.value = mapFun.invoke(first, second, third)
            }
        }
        mediator.addSource(_third) { third ->
            val first = _first.value
            val second = _second.value
            if (first != null && second != null && third != null) {
                mediator.value = mapFun.invoke(first, second, third)
            }
        }
        return mediator
    }

    /**
     * observer merge tow livedata
     */
    fun <S, T, U, Y> of(
        _first: LiveData<S>,
        _second: LiveData<T>,
        _third: LiveData<U>,
        mapFun: (S?, T?, U?) -> Y
    ): LiveData<Y> {
        val mediator: MediatorLiveData<Y> = MediatorLiveData()
        mediator.addSource(_first) { first ->
            val second = _second.value
            val third = _third.value
            mediator.value = mapFun.invoke(first, second, third)
        }
        mediator.addSource(_second) { second ->
            val first = _first.value
            val third = _third.value
            mediator.value = mapFun.invoke(first, second, third)
        }
        mediator.addSource(_third) { third ->
            val first = _first.value
            val second = _second.value
            mediator.value = mapFun.invoke(first, second, third)
        }
        return mediator
    }

}
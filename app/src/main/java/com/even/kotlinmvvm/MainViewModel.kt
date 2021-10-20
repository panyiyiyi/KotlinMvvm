package com.even.kotlinmvvm

import androidx.lifecycle.MutableLiveData
import com.even.common.base.BaseViewModel
import com.even.common.ext.coroutineCall
import com.even.common.request.getService

/**
 *  @author  Even
 *  @date   2021/10/20
 */
class MainViewModel : BaseViewModel() {
    var arcLiveData = MutableLiveData<ArticleListBean>()
    fun req() {
        coroutineCall(arcLiveData) { getService<AppApiService>().getArticleList(10) }
    }
}
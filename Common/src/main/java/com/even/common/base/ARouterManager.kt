package com.even.common.base

import com.alibaba.android.arouter.facade.template.IProvider
import com.alibaba.android.arouter.launcher.ARouter

/**
 *  @author  Even
 *  @date   2021/10/19
 */
object ARouterManager {
    private var mProviderMap = mutableMapOf<String, IProvider>()
    fun <T : IProvider> getProvider(path: String): T? {
        var iProvider = mProviderMap[path]
        if (null == iProvider) {
            iProvider = ARouter.getInstance().build(path).navigation() as IProvider
            mProviderMap[path] = iProvider
        }
        return iProvider as? T
    }
}
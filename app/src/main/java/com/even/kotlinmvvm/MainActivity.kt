package com.even.kotlinmvvm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.even.common.base.BaseActivity
import com.even.kotlinmvvm.databinding.ActivityMainBinding

class MainActivity :
    BaseActivity<MainViewModel, ActivityMainBinding>(R.layout.activity_main, BR.viewModel) {

    override fun getTitleBarView(): View =
        LayoutInflater.from(this).inflate(R.layout.activity_main, null, false)

    override fun initView(view: ViewGroup) {
        dataBinding.btn.setOnClickListener { viewModel.req() }
    }

    override fun useDefaultTitleBar(): Boolean = false
}
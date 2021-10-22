package com.even.common.ext

import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

/**
 *  @author  Even
 *  @date   2021/10/22
 *  View的拓展类
 */

@BindingAdapter("isVisible")
fun View.setVisible(visible: Boolean) {
    this.isVisible = visible
}

@BindingAdapter("isSelect")
fun View.setIsSelect(isSelect: Boolean) {
    this.isSelected = isSelected
}

@BindingAdapter("isEnable")
fun View.setEnable(isEnable: Boolean) {
    this.isEnabled = isEnabled
}

@BindingAdapter("bgColor")
fun View.setBackgroundColor(@ColorRes colorId: Int) {
    this.setBackgroundColor(ContextCompat.getColor(this.context, colorId))
}

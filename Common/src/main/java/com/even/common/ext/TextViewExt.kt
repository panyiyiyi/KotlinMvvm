package com.even.common.ext

import android.graphics.Paint
import android.widget.TextView
import androidx.databinding.BindingAdapter

/**
 *  @author  Even
 *  @date   2021/10/22
 */
//设置删除线
@BindingAdapter("delLine")
fun TextView.setDelLine(isDelete: Boolean) {
    this.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
}

//设置下划线
@BindingAdapter("underLine")
fun TextView.setUnderLine(isUnderLine: Boolean) {
    this.paint.flags = Paint.UNDERLINE_TEXT_FLAG
    this.paint.isAntiAlias = true

}
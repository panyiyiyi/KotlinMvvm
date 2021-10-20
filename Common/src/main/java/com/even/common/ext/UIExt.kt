package com.even.common.ext

import android.content.res.Resources
import kotlin.math.roundToInt

/**
 *  @author  Even
 *  @date   2021/10/20
 */
//sp to px
val Int.sp: Int
    get() = (this * Resources.getSystem().displayMetrics.scaledDensity + 0.5).roundToInt()

//dp to px
val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5).roundToInt()

//px to dp
val Int.px2dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density + 0.5).roundToInt()

//px to sp
val Int.px2sp: Int
    get() = (this / Resources.getSystem().displayMetrics.scaledDensity + 0.5).roundToInt()
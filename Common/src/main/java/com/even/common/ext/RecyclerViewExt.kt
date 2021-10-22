package com.even.common.ext

import androidx.recyclerview.widget.RecyclerView

/**
 *  @author  Even
 *  @date   2021/10/21
 */
abstract class OnHorizontalScrollListener : RecyclerView.OnScrollListener() {
    final override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (!recyclerView.canScrollHorizontally(-1)) {
            onScrolledToLeft()
        } else if (!recyclerView.canScrollHorizontally(1)) {
            onScrolledToRight()
        } else if (dx < 0) {
            onScrolledLeft()
        } else if (dx > 0) {
            onScrolledRight()
        }
    }

    open fun onScrolledLeft() {}

    open fun onScrolledRight() {}

    open fun onScrolledToLeft() {}

    open fun onScrolledToRight() {}
}

abstract class OnVerticalScrollListener : RecyclerView.OnScrollListener() {
    final override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (!recyclerView.canScrollVertically(-1)) {
            onScrolledToTop()
        } else if (!recyclerView.canScrollVertically(1)) {
            onScrolledToBottom()
        }
        if (dy < 0) {
            onScrolledUp(dy)
        } else if (dy > 0) {
            onScrolledDown(dy)
        }
        onScrolled(dy)
    }

    open fun onScrolled(dy: Int) {}

    open fun onScrolledUp(dy: Int) {}

    open fun onScrolledDown(dy: Int) {}

    open fun onScrolledToTop() {}

    open fun onScrolledToBottom() {}
}
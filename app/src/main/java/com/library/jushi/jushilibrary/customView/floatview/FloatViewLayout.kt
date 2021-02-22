package com.library.jushi.jushilibrary.customView.floatview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.jushi.library.R

class FloatViewLayout : RelativeLayout {

    private var screenWidth = 0
    private var screenHeight = 0

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        screenWidth = context.resources.displayMetrics.widthPixels
        screenHeight = context.resources.displayMetrics.heightPixels - getStatusBarHeight()
        initAttribute(attrs)
    }

    private fun initAttribute(attrs: AttributeSet?) {
        val array:TypedArray = context.obtainStyledAttributes(attrs, R.styleable.FloatViewLayout)

    }


    private fun getStatusBarHeight(): Int {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
    }

}
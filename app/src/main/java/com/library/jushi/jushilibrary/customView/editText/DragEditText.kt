package com.library.jushi.jushilibrary.customView.editText

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.support.v7.widget.AppCompatEditText
import android.util.AttributeSet
import android.util.TypedValue
import android.view.*
import android.widget.FrameLayout
import com.library.jushi.jushilibrary.R

class DragEditText : FrameLayout {
    private var lastX = 0
    private var lastY = 0
    private var parentWidth: Int = 0
    private var parentHeight: Int = 0

    private lateinit var mEditText: ShareEditText

    private val GRAVITY_LEFT = 0
    private val GRAVITY_RIGHT = 1
    private val GRAVITY_TOP = 2
    private val GRAVITY_BOTTOM = 3
    private val GRAVITY_CENTER = 4
    private val GRAVITY_CENTER_HORIZONTAL = 5
    private val GRAVITY_CENTER_VERTICAL = 6


    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs!!)
    }

    fun init(attrs: AttributeSet) {
        mEditText = ShareEditText(context)
        addView(mEditText)
        initAttribute(attrs)
    }

    private fun initAttribute(attrs: AttributeSet) {
        val array: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.DragEditText)
        setText(array.getString(R.styleable.DragEditText_text)!!)
        setTextSize(array.getDimensionPixelSize(R.styleable.DragEditText_textSize, 0))
        setHint(array.getString(R.styleable.DragEditText_hintText)!!)
        setTextColor(array.getColor(R.styleable.DragEditText_textColor, Color.BLACK))
        setHintTextColor(array.getColor(R.styleable.DragEditText_hintColor, Color.parseColor("#C4C7CC")))
        setBackGroundResource(array.getResourceId(R.styleable.DragEditText_backgroundResource, 0))
        setLines(array.getInt(R.styleable.DragEditText_lines, Int.MAX_VALUE))
        setTextGravity(array.getInt(R.styleable.DragEditText_gravity, GRAVITY_LEFT))


        array.recycle()
    }

    private fun setText(text: String) {
        mEditText.setText(text)
    }

    private fun setTextColor(color: Int) {
        mEditText.setTextColor(color)
    }

    private fun setTextSize(size: Int) {
        mEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, size.toFloat())
    }

    private fun setHint(text: String) {
        mEditText.hint = text
    }

    private fun setHintTextColor(color: Int) {
        mEditText.setHintTextColor(color)
    }

    private fun setBackGroundResource(resId: Int) {
        mEditText.setBackgroundResource(resId)
    }

    fun setLines(lines: Int) {
        mEditText.setLines(lines)
    }

    fun setGravity(gravity: Int) {
        mEditText.gravity = gravity
    }

    @SuppressLint("RtlHardcoded")
    private fun setTextGravity(gravity: Int) {
        when (gravity) {
            GRAVITY_LEFT -> setGravity(Gravity.LEFT)
            GRAVITY_RIGHT -> setGravity(Gravity.RIGHT)
            GRAVITY_TOP -> setGravity(Gravity.TOP)
            GRAVITY_BOTTOM -> setGravity(Gravity.BOTTOM)
            GRAVITY_CENTER -> setGravity(Gravity.CENTER)
            GRAVITY_CENTER_HORIZONTAL -> setGravity(Gravity.CENTER_HORIZONTAL)
            GRAVITY_CENTER_VERTICAL -> setGravity(Gravity.CENTER_VERTICAL)
        }
    }


    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            lastX = ev.rawX.toInt()
            lastY = ev.rawY.toInt()
            return false
        }
        return true
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_MOVE -> {
                var dx: Int = (event.rawX - lastX).toInt()
                var dy: Int = (event.rawY - lastY).toInt()
                if (parentWidth == 0) {
                    val viewGroup: ViewGroup = parent as ViewGroup
                    parentWidth = viewGroup.width
                    parentHeight = viewGroup.height
                }
                if (translationX < -left && dx < 0) {
                    dx = 0
                } else if (translationX > (parentWidth - right) && dx > 0) {
                    dx = 0
                }
                if (translationY < -top && dy < 0) {
                    dy = 0
                } else if (translationY > (parentHeight - bottom) && dy > 0) {
                    dy = 0
                }
                translationX += dx
                translationY += dy
                lastX = event.rawX.toInt()
                lastY = event.rawY.toInt()
            }
        }
        return true
    }

    inner class ShareEditText : AppCompatEditText {
        private var downX: Int = 0
        private var downY: Int = 0

        constructor(context: Context) : super(context)

        constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

        constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

        override fun dispatchTouchEvent(event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    downX = event.rawX.toInt()
                    downY = event.rawY.toInt()
                    parent.requestDisallowInterceptTouchEvent(true)
                }
                MotionEvent.ACTION_MOVE -> {
                    var upX = event.rawX.toInt() - downX
                    var upY = event.rawY.toInt() - downY
                    if (Math.abs(upX) <= ViewConfiguration.get(context).scaledTouchSlop && Math.abs(upY) <= ViewConfiguration.get(context).scaledTouchSlop) {
                        parent.requestDisallowInterceptTouchEvent(true)
                    } else {
                        parent.requestDisallowInterceptTouchEvent(false)
                    }
                }
            }
            return super.dispatchTouchEvent(event)
        }

        override fun onSelectionChanged(selStart: Int, selEnd: Int) {
            super.onSelectionChanged(selStart, selEnd)
            if (selStart == selEnd) {
                setSelection(text!!.length)
            }
        }

    }
}
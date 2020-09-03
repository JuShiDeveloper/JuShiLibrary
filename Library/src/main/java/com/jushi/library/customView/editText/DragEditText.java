package com.jushi.library.customView.editText;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jushi.library.R;

/**
 * 可拖动的文字输入框
 * create by wyf on 2017/05/06
 */
public class DragEditText extends FrameLayout {
    private int lastX, lastY;
    private int parentWidth, parentHeight;
    private ShareEditText mEditText;
    private final int GRAVITY_LEFT = 0;
    private final int GRAVITY_RIGHT = 1;
    private final int GRAVITY_TOP = 2;
    private final int GRAVITY_BOTTOM = 3;
    private final int GRAVITY_CENTER = 4;
    private final int GRAVITY_CENTER_HORIZONTAL = 5;
    private final int GRAVITY_CENTER_VERTICAL = 6;


    public DragEditText(Context context) {
        this(context, null);
    }

    public DragEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        mEditText = new ShareEditText(getContext());
        addView(mEditText);
        initAttribute(attrs);
    }

    private void initAttribute(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.DragEditText);
        setText(array.getString(R.styleable.DragEditText_text));
        setTextSize(array.getDimensionPixelSize(R.styleable.DragEditText_textSize, 0));
        setHint(array.getString(R.styleable.DragEditText_hintText));
        setTextColor(array.getColor(R.styleable.DragEditText_textColor, Color.BLACK));
        setHintTextColor(array.getColor(R.styleable.DragEditText_hintColor, Color.parseColor("#C4C7CC")));
        setEditTextDigits(array.getString(R.styleable.DragEditText_editDigits));
        setBackgroundResource(array.getResourceId(R.styleable.DragEditText_backgroundResource, 0));
        setLines(array.getInt(R.styleable.DragEditText_lines, Integer.MAX_VALUE));
        setMaxLength(array.getInt(R.styleable.DragEditText_editMaxLength, Integer.MAX_VALUE));
        setTextGravity(array.getInt(R.styleable.DragEditText_gravity, GRAVITY_LEFT));
        array.recycle();
    }

    public void setTextColor(int color) {
        mEditText.setTextColor(color);
    }

    public void setText(String text) {
        mEditText.setText(text);
    }

    public void setTextSize(int size) {
        mEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    public void setHint(String text) {
        mEditText.setHint(text);
    }

    public void setHintTextColor(int color) {
        mEditText.setHintTextColor(color);
    }

    public void setBackgroundResource(int resId) {
        mEditText.setBackgroundResource(resId);
    }

    /**
     * 设置输入框可输入的字符
     *
     * @param digits
     */
    public void setEditTextDigits(String digits) {
        if (digits == null || digits.equals("")) return;
        mEditText.setKeyListener(DigitsKeyListener.getInstance(digits));
    }

    public void setMaxLength(int length) {
        mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
    }

    public void setLines(int lines) {
        mEditText.setLines(lines);
    }

    public void setGravity(int gravity) {
        mEditText.setGravity(gravity);
    }

    private void setTextGravity(int gravity) {
        switch (gravity) {
            case GRAVITY_LEFT:
                setGravity(Gravity.LEFT);
                break;
            case GRAVITY_RIGHT:
                setGravity(Gravity.RIGHT);
                break;
            case GRAVITY_TOP:
                setGravity(Gravity.TOP);
                break;
            case GRAVITY_BOTTOM:
                setGravity(Gravity.BOTTOM);
                break;
            case GRAVITY_CENTER:
                setGravity(Gravity.CENTER);
                break;
            case GRAVITY_CENTER_HORIZONTAL:
                setGravity(Gravity.CENTER_HORIZONTAL);
                break;
            case GRAVITY_CENTER_VERTICAL:
                setGravity(Gravity.CENTER_VERTICAL);
                break;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            lastX = (int) ev.getRawX();
            lastY = (int) ev.getRawY();
            return false;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int deltaX = (int) event.getRawX() - lastX;
                int deltaY = (int) event.getRawY() - lastY;
                if (parentWidth == 0) {
                    ViewGroup mViewGroup = (ViewGroup) getParent();
                    parentWidth = mViewGroup.getWidth();
                    parentHeight = mViewGroup.getHeight();
                }
                if (getTranslationX() < -getLeft() && deltaX < 0) {
                    deltaX = 0;
                } else if (getTranslationX() > (parentWidth - getRight()) && deltaX > 0) {
                    deltaX = 0;
                }
                if (getTranslationY() < -getTop() && deltaY < 0) {
                    deltaY = 0;
                } else if (getTranslationY() > (parentHeight - getBottom()) && deltaY > 0) {
                    deltaY = 0;
                }
                setTranslationX(getTranslationX() + deltaX);
                setTranslationY(getTranslationY() + deltaY);
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    private class ShareEditText extends android.support.v7.widget.AppCompatEditText {

        private int downX, downY;

        public ShareEditText(Context context) {
            super(context);
        }

        public ShareEditText(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public ShareEditText(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = (int) event.getRawX();
                    downY = (int) event.getRawY();
                    getParent().requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_MOVE:
                    int upX = (int) event.getRawX() - downX;
                    int upY = (int) event.getRawY() - downY;
                    if (Math.abs(upX) <= ViewConfiguration.get(getContext()).getScaledTouchSlop() && Math.abs(upY) <= ViewConfiguration.get(getContext()).getScaledTouchSlop()) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                    break;
            }
            return super.dispatchTouchEvent(event);
        }

        @Override
        protected void onSelectionChanged(int selStart, int selEnd) {
            super.onSelectionChanged(selStart, selEnd);
            //保证光标始终在最后面
            if (selStart == selEnd) {//防止不能多选
                setSelection(getText().length());
            }
        }
    }
}

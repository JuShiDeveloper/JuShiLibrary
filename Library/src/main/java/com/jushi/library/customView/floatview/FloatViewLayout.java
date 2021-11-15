package com.jushi.library.customView.floatview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;

import com.jushi.library.R;


/**
 * 可拖动、自动吸边的布局容器；
 * 可用于悬浮视图等
 * create by jushi on 2020/09/01
 */
public class FloatViewLayout extends RelativeLayout implements View.OnTouchListener {
    private View.OnClickListener onClickListener;
    private boolean needCallOnClick = false;
    private int screenWidth;
    private int screenHeight;
    private float moveX;
    private float moveY;
    private float lastX;
    private float lastY;
    private float touchX;
    private float touchY;
    private int width;
    private int height;
    private boolean isCacheXY;
    private boolean autoCalculateWelt;
    private int animatorDuration = 250;
    private SharedPreferences sp;


    public FloatViewLayout(Context context) {
        super(context);
        init(null);
    }

    public FloatViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public FloatViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels - getStatusBarHeight();
        initAttribute(attrs);
        setOnTouchListener(this);
        initCacheXY();
    }

    private void initAttribute(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.FloatViewLayout);
        isCacheXY = typedArray.getBoolean(R.styleable.FloatViewLayout_cacheXY, true);
        autoCalculateWelt = typedArray.getBoolean(R.styleable.FloatViewLayout_autoCalculateWelt, true);
        animatorDuration = typedArray.getInt(R.styleable.FloatViewLayout_animatorDuration, 250);
        typedArray.recycle();
    }

    private void initCacheXY() { //获取上一次拖动贴边之后缓存的坐标
        sp = getContext().getSharedPreferences("FloatViewLayout", Context.MODE_PRIVATE);
        //未开启位置保存或未保存上一次的位置
        if (!isCacheXY || !sp.getBoolean("isCache", false)) return;
        float cacheX = sp.getFloat("x", 0);
        float cacheY = sp.getFloat("y", 0);
        setX(cacheX);
        setY(cacheY);
        invalidate();
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (width > 0 && height > 0) return;
        width = r - l;
        height = b - t;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                needCallOnClick = true;
                clearAnimation();
                touchX = event.getX();
                touchY = event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                moveX = Math.abs(event.getRawX() - lastX) + moveX;
                moveY = Math.abs(event.getRawY() - lastY) + moveY;
                int touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
                needCallOnClick = !(moveX > touchSlop) && !(moveY > touchSlop);
                float nowY = event.getRawY() - touchY;
                float nowX = event.getRawX() - touchX;
                nowX = nowX < 0 ? 0 : (nowX + width > screenWidth) ? (screenWidth - width) : nowX;
                nowY = nowY < 0 ? 0 : (nowY + height > screenHeight) ? (screenHeight - height) : nowY;
                setY(nowY);
                setX(nowX);
                invalidate();
                return true;
            case MotionEvent.ACTION_CANCEL:
                needCallOnClick = false;
                return true;
            case MotionEvent.ACTION_UP:
                if (needCallOnClick) {
                    notifyOnClick();
                } else {
                    postDelayed(() -> toCalculateWelt(), 200);
                }
                return true;
        }
        lastX = event.getRawX();
        lastY = event.getRawY();
        return super.onTouchEvent(event);
    }

    /**
     * 触发点击事件
     */
    private void notifyOnClick() {
        if (onClickListener == null) return;
        onClickListener.onClick(this);
    }

    /**
     * 计算自动贴边
     */
    private void toCalculateWelt() {
        if (!autoCalculateWelt) return;//未开启自动贴边
        float centerX = getX() + width / 2f;
        int halfOfScreenWidth = screenWidth / 2;
        int endX = (centerX > halfOfScreenWidth) ? screenWidth - width : 0;
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "translationX", getX(), endX);
        animator.addListener(new MyAnimatorListener());
        animator.setDuration(animatorDuration);
        animator.start();
    }

    //获取状态栏高度
    private int getStatusBarHeight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        return resourceId > 0 ? getResources().getDimensionPixelSize(resourceId) : 0;
    }

    private void saveXY() {
        if (!isCacheXY) return;//未开启位置缓存
        SharedPreferences.Editor edit = sp.edit();
        edit.putFloat("x", getX());
        edit.putFloat("y", getY());
        edit.putBoolean("isCache", true);
        edit.apply();
    }

    private class MyAnimatorListener implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            saveXY();
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }

}

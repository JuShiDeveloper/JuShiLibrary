package com.jushi.library.base;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.FrameLayout;


/**
 * 悬浮窗服务基类
 * 可拖动、自动吸边
 * create by wyf on 2020/09/02
 */
public abstract class BaseFloatWindowService extends Service implements View.OnTouchListener {
    private WindowManager windowManager;
    private WindowManager.LayoutParams wmParams;
    private FrameLayout rootLayout;
    private boolean needCallOnClick = false;
    private float moveX;
    private float moveY;
    private float lastX;
    private float lastY;
    private int screenWidth;

    @Override
    public void onCreate() {
        super.onCreate();
        initWindowManager();
        initialize();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return onIBinder(intent);
    }

    private void initWindowManager() {
        windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        initWindowManagerLayoutParams();
        rootLayout = new FrameLayout(getApplicationContext());
        rootLayout.removeAllViews();
        rootLayout.addView(onWindowView(LayoutInflater.from(getApplicationContext())));
        rootLayout.setOnTouchListener(this);
        windowManager.addView(rootLayout, wmParams);
        screenWidth = getResources().getDisplayMetrics().widthPixels;
    }

    private WindowManager.LayoutParams initWindowManagerLayoutParams() {
        wmParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        //设置可以显示在状态栏上
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        //设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.format = PixelFormat.RGBA_8888;//设置图片格式，效果为背景透明
        // 悬浮窗默认显示以左上角为起始坐标
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        //悬浮窗的开始位置，因为设置的是从左上角开始，所以屏幕左上角是x=0;y=0
        wmParams.x = getResources().getDisplayMetrics().widthPixels - (getResources().getDisplayMetrics().widthPixels / 4);
        wmParams.y = 200;
        onWindowManagerLayoutParams(wmParams);
        return wmParams;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                needCallOnClick = true;
                return false;
            case MotionEvent.ACTION_MOVE:
                moveX = Math.abs(event.getRawX() - lastX) + moveX;
                moveY = Math.abs(event.getRawY() - lastY) + moveY;
                int touchSlop = ViewConfiguration.get(getApplicationContext()).getScaledTouchSlop();
                needCallOnClick = !(moveX > touchSlop) && !(moveY > touchSlop);
                wmParams.x = (int) (event.getRawX() - rootLayout.getMeasuredWidth() / 2);
                wmParams.y = (int) (event.getRawY() - rootLayout.getMeasuredHeight() / 2);
                windowManager.updateViewLayout(rootLayout, wmParams);
                return true;
            case MotionEvent.ACTION_CANCEL:
                needCallOnClick = false;
                return false;
            case MotionEvent.ACTION_UP:
                if (needCallOnClick) {
                    onWindowClickEvent();
                } else {
                    rootLayout.postDelayed(() -> toCalculateWelt(), 200);
                }
                return false;
        }
        lastX = event.getRawX();
        lastY = event.getRawY();
        return false;
    }

    /**
     * 自动贴边
     */
    private void toCalculateWelt() {
        if (!autoSuctionSide()) return;
        int endX = wmParams.x < ((screenWidth / 2) - 100) ? 0 : screenWidth;
        float dx = endX - wmParams.x;
        float sx = wmParams.x;
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.addUpdateListener(animation -> {
            Float value = (Float) animation.getAnimatedValue();
            wmParams.x = (int) (sx + dx * value);
            windowManager.updateViewLayout(rootLayout, wmParams);
        });
        animator.addListener(new MyAnimatorListener());
        animator.setDuration(onAutoSuctionSideAnimatorDuration());
        animator.start();
    }

    private class MyAnimatorListener implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }

    /**
     * 继承该类的子类需要显示的悬浮窗view
     *
     * @param inflater
     * @return
     */
    protected abstract View onWindowView(LayoutInflater inflater);

    protected abstract IBinder onIBinder(Intent intent);

    /**
     * 处理其他逻辑
     */
    protected abstract void initialize();

    /**
     * 自动吸边
     *
     * @return
     */
    protected abstract boolean autoSuctionSide();

    /**
     * 自动吸边动画时长，单位毫秒
     *
     * @return
     */
    protected long onAutoSuctionSideAnimatorDuration() {
        return 300;
    }

    /**
     * 设置LayoutParams参数值，不处理则使用默认初始值
     *
     * @param wmParams
     */
    protected void onWindowManagerLayoutParams(WindowManager.LayoutParams wmParams) {
    }

    /**
     * 悬浮窗点击事件
     */
    protected abstract void onWindowClickEvent();
}

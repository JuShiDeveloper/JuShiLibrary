package com.jushi.library.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.jushi.library.systemBarUtils.SystemBarUtil;
import com.jushi.library.utils.ToastUtil;
import com.jushi.library.viewinject.ViewInjecter;

/**
 * 基类activity
 */
public abstract class BaseFragmentActivity extends BasePermissionActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    private void initialize() {
        setContentView(getLayoutResId());
        ViewInjecter.inject(this);
        BaseApplication.getInstance().injectManager(this);
        initView();
        getIntentData(getIntent());
        initData();
        setListener();
        initAnimator();
    }

    /**
     * 重写此方法，返回布局文件资源id
     *
     * @return
     */
    protected abstract int getLayoutResId();

    /**
     * 初始化视图控件
     */
    protected abstract void initView();

    protected void getIntentData(Intent intent) {

    }

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 设置监听事件
     */
    protected abstract void setListener();

    /**
     * 初始化动画 (根据需要选择是否重写此方法)
     */
    protected void initAnimator() {

    }

    @Override
    protected void onCameraPermissionOpened() {

    }

    @Override
    protected void onExternalStoragePermissionOpened() {

    }

    @Override
    protected void onLocationPermissionOpened() {

    }

    @Override
    protected void onRecordAudioPermissionOpened() {

    }

    @Override
    protected void onAlertWindowPermissionOpened() {

    }

    /**
     * 设置状态栏状态 （在重写的getLayoutResId()方法中调用才有效）
     *
     * @param isFitsSystemWindows    是否沉浸式状态栏
     * @param isTranslucentSystemBar 是否透明状态栏
     * @param statusBarTextDark      状态栏文字颜色是否为深色 true-深色模式 , false-亮色模式
     */
    public void setSystemBarStatus(boolean isFitsSystemWindows, boolean isTranslucentSystemBar, boolean statusBarTextDark) {
        SystemBarUtil.setRootViewFitsSystemWindows(this, isFitsSystemWindows);
        if (isTranslucentSystemBar) { //沉浸式状态栏，设置状态栏透明
            SystemBarUtil.setTranslucentStatus(this);
        }
        if (isFitsSystemWindows) { //沉浸式状态栏，设置状态栏文字颜色模式
            SystemBarUtil.setAndroidNativeLightStatusBar(this, statusBarTextDark);
        }
    }

    public void showToast(long msg) {
        showToast(msg + "");
    }

    public void showToast(boolean msg) {
        showToast(msg + "");
    }

    public void showToast(float msg) {
        showToast(msg + "");
    }

    public void showToast(int msg) {
        showToast(msg + "");
    }

    public void showToast(String msg) {
        ToastUtil.showToast(this, msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (Fragment f : fragmentManager.getFragments()) {
            if (f == null) continue;
            handleChildResult(f, requestCode, resultCode, data);
        }
    }

    /**
     * activity跳转结果响应事件传递到fragment
     *
     * @param f
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void handleChildResult(Fragment f, int requestCode, int resultCode, Intent data) {
        f.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : f.getChildFragmentManager().getFragments()) {
            if (fragment == null) continue;
            handleChildResult(fragment, requestCode, resultCode, data);
        }
    }

    /**
     * 隐藏键盘
     */
    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocus = this.getCurrentFocus();
        if (currentFocus == null)
            return;
        IBinder windowToken = currentFocus.getWindowToken();
        imm.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 打开键盘
     *
     * @param view view
     */
    public void openSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }

    /**
     * 获取软件盘的高度
     *
     * @return
     */
    private int getSupportSoftInputHeight(Activity activity) {
        Rect r = new Rect();
        /**
         * decorView是window中的最顶层view，可以从window中通过getDecorView获取到decorView。
         * 通过decorView获取到程序显示的区域，包括标题栏，但不包括状态栏。
         */
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        //获取屏幕的高度
        int screenHeight = activity.getWindow().getDecorView().getRootView().getHeight();
        //计算软件盘的高度
        int softInputHeight = screenHeight - r.bottom;
        /**
         * 某些Android版本下，没有显示软键盘时减出来的高度总是144，而不是零，
         * 这是因为高度是包括了虚拟按键栏的(例如华为系列)，所以在API Level高于20时，
         * 我们需要减去底部虚拟按键栏的高度（如果有的话）
         */
        if (Build.VERSION.SDK_INT >= 20) {
            // When SDK Level >= 20 (Android L), the softInputHeight will contain the height of softButtonsBar (if has)
            softInputHeight = softInputHeight - getSoftButtonsBarHeight(activity);
        }
        return softInputHeight;
    }

    /**
     * 底部虚拟按键栏的高度
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private int getSoftButtonsBarHeight(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        //这个方法获取可能不是真实屏幕的高度
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        //获取当前屏幕的真实高度
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }
}

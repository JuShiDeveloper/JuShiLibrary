package com.jushi.library.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jushi.library.systemBarUtils.SystemBarUtil;
import com.jushi.library.utils.ToastUtil;
import com.jushi.library.viewinject.ViewInjecter;

/**
 * 基类activity
 */
public abstract class BaseFragmentActivity extends AppCompatActivity {

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

    /**
     * 设置状态栏状态 （在重写的getLayoutResId()方法中调用才有效）
     *
     * @param isFitsSystemWindows    是否沉浸式状态栏
     * @param isTranslucentSystemBar 是否透明状态栏
     */
    public void setSystemBarStatus(boolean isFitsSystemWindows, boolean isTranslucentSystemBar) {
        SystemBarUtil.setRootViewFitsSystemWindows(this, isFitsSystemWindows);
        if (!isTranslucentSystemBar) return;
        SystemBarUtil.setTranslucentStatus(this);
    }

    /**
     * 如果设置沉浸式状态栏，如果view的父控件是 LinearLayout 则调用该方法 (在initView()中调用)
     *
     * @param view 显示在状态栏位置的View
     */
    public void setSystemBarViewLayoutParamsL(View view) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, SystemBarUtil.getStatusBarHeight(this));
        view.setLayoutParams(params);
    }

    /**
     * 如果设置沉浸式状态栏，如果view的父控件是RelativeLayout则调用该方法 (在initView()中调用)
     *
     * @param view 显示在状态栏位置的View
     */
    public void setSystemBarViewLayoutParamsR(View view) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, SystemBarUtil.getStatusBarHeight(this));
        view.setLayoutParams(params);
    }

    public void showToast(long msg){
        showToast(msg+"");
    }

    public void showToast(boolean msg){
        showToast(msg+"");
    }

    public void showToast(float msg){
        showToast(msg+"");
    }

    public void showToast(int msg) {
        showToast(msg + "");
    }

    public void showToast(String msg) {
        ToastUtil.showToast(this, msg);
    }

}

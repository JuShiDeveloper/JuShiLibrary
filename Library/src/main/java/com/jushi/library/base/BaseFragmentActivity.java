package com.jushi.library.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

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
        getIntentData(getIntent());
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

}

package com.jushi.library.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jushi.library.viewinject.ViewInjecter;

/**
 * 配合viewPage使用，页面可见时才加载数据
 */
public abstract class ViewPagerFragment extends BasePermissionFragment {
    /**
     * rootView是否初始化标志，防止回调函数在rootView为空的时候触发
     */
    private boolean hasCreateView = false;
    /**
     * 当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
     */
    private boolean isFragmentVisible = false;
    /**
     * onCreateView()里返回的view，修饰为protected,所以子类继承该类时，在onCreateView里必须对该变量进行初始化
     */
    protected View rootView;

    public boolean isInitDataSuccess = false;  //数据是否加载成功

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = initRootView(inflater, container, savedInstanceState);
        ViewInjecter.inject(this, rootView);
        BaseApplication.getInstance().injectManager(this);
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (rootView == null) return;
        hasCreateView = true;
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            onFragmentVisibleChange(false);
            isFragmentVisible = false;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        setViewListener();
        if (!hasCreateView && getUserVisibleHint()) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
        }
    }

    /**
     * 在此方法初始化布局文件
     */
    protected abstract View initRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract void initView();

    /**
     * 设置View的监听事件
     */
    protected abstract void setViewListener();

    /**
     * 子类重写此方法，在该方法中请求数据
     *
     * @param isVisible true 表示可见，false表示不可见
     */
    protected abstract void onFragmentVisibleChange(Boolean isVisible);

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
}

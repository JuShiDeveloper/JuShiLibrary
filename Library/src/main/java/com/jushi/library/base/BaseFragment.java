package com.jushi.library.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jushi.library.viewinject.ViewInjecter;

public abstract class BaseFragment extends BasePermissionFragment {
    protected View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = initRootView(inflater, container, savedInstanceState);
        ViewInjecter.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BaseApplication.getInstance().injectManager(this);
        initViewWidget();
        initData();
        setListener();
    }

    /**
     * 初始化Fragment界面视图
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    protected abstract View initRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 初始化视图控件
     */
    protected abstract void initViewWidget();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 设置事件监听
     */
    protected abstract void setListener();

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

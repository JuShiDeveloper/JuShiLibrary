package com.jushi.library.base;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jushi.library.manager.NetworkManager;
import com.jushi.library.manager.UserManager;
import com.jushi.library.viewinject.ViewInjecter;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public abstract class BaseFragment extends BasePermissionFragment {
    protected View rootView;
    protected UserManager userManager;
    protected NetworkManager networkManager;

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
        userManager = getManager(UserManager.class);
        networkManager = getManager(NetworkManager.class);
        initViewWidget();
        initData();
        setListener();
    }

    protected void reLaunchApp(){
        showToast("设置成功，3秒后应用将重新启动!", Gravity.CENTER);
        rootView.postDelayed(()-> {
            Intent i = getContext().getPackageManager().getLaunchIntentForPackage( getContext().getPackageName() );
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            Runtime.getRuntime().exit(0);
        },3000);
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

    @Override
    protected void onCallPhonePermissionOpened() {

    }

    protected <Manager extends BaseManager> Manager getManager(Class<Manager> cls) {
        return BaseApplication.getInstance().getManager(cls);
    }

    protected void showIndeterminateProgressDialog(){
        ((BaseFragmentActivity)getActivity()).showIndeterminateProgressDialog();
    }

    protected void dismissIndeterminateProgressDialog(){
        ((BaseFragmentActivity)getActivity()).dismissIndeterminateProgressDialog();
    }
}

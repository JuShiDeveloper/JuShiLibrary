package com.jushi.library.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import com.jushi.library.base.BaseApplication;
import com.jushi.library.base.BaseManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 网络变化管理器，负责管理网络变化的通知，如果需要知道当前手机的网络连接类型，调用getNetWorkType函数
 * 如果需要时刻监听网络的断开与连接，请调用：addOnNetworkChangeListener
 */
public class NetworkManager extends BaseManager {
    private final String TAG = NetworkManager.class.getSimpleName();
    /**
     * 上一次的网络连接类型
     */
    private int lastConnectType = -1;
    private Context context;
    private List<OnNetworkChangeListener> onNetworkChangeListeners = new ArrayList<>();


    @Override
    public void onManagerCreate(BaseApplication application) {
        this.context = application;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(broadcastReceiver, intentFilter);
    }

    /**
     * 获取当前的网络连接类型 {@link NetworkInfo#getType()}
     * 注意：手机网络并不是只有wifi和移动流量。
     *
     * @return 网络类型包括：wifi，移动数据，蓝牙共享，网线，VPN等等，
     */
    public int getNetWorkType() {
        return lastConnectType;
    }

    public int checkNetWork() {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            int currentType = -1;
            if (networkInfo != null && networkInfo.isConnected()) {
                currentType = networkInfo.getType();
            } else {
                currentType = -1;
            }
            if (lastConnectType != currentType) {
                Logger.i(TAG, "checkNetWork:网络状态未同步，同步当前网络状态:" + currentType);
                lastConnectType = currentType;
            }
        }
        return lastConnectType;
    }

    /**
     * 添加网络变化监听器
     */
    public void addOnNetworkChangeListener(OnNetworkChangeListener onNetworkChangeListener) {
        onNetworkChangeListeners.add(onNetworkChangeListener);
    }

    /**
     * 移除网络变化监听器
     */
    public void removeOnNetworkChangeListener(OnNetworkChangeListener onNetworkChangeListener) {
        onNetworkChangeListeners.remove(onNetworkChangeListener);
    }

    /**
     * 是否有网
     *
     * @return true 为有网
     */
    public boolean isNetConect() {
        return lastConnectType != -1;
    }

    /**
     * 是否是wifi连接
     * @param mContext
     * @return
     */
    public static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }


    public interface OnNetworkChangeListener {
        /**
         * 网络变化的回调
         *
         * @param networkType != -1 为有网
         */
        void onNetworkChange(int networkType);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            int currentType = -1;
            if (networkInfo != null && networkInfo.isConnected()) {
                currentType = networkInfo.getType();
            } else {
                currentType = -1;
            }

            if (lastConnectType != currentType) {
                Logger.i(TAG, "NetworkManager:网络状态变化了:" + currentType);
                lastConnectType = currentType;
                for (OnNetworkChangeListener onNetworkChangeListener : onNetworkChangeListeners) {
                    onNetworkChangeListener.onNetworkChange(currentType);
                }
            }
        }
    };
}

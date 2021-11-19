package com.jushi.library.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;

import com.jushi.library.base.BaseApplication;
import com.jushi.library.base.BaseManager;
import com.jushi.library.utils.ConnectedBluetooth;
import com.jushi.library.utils.LogUtil;
import com.jushi.library.utils.ToastUtil;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 蓝牙功能管理类
 * 1、蓝牙连接状态监听
 * 2、蓝牙扫描管理
 * 3、蓝牙连接、断开连接
 * 4、蓝牙通信(收发信息)
 * created by wyf on 2021-11-12
 */
public class BluetoothFuncManager extends BaseManager {
    public static final int ACTION_FOUND = 1;//扫描发现设备
    public static final int ACTION_FINISHED = 2; //扫描结束
    public static final int MESSAGE_STATE_CHANGE = 0;
    public static final int MESSAGE_DEVICE_NAME = 1;
    public static final int MESSAGE_TOAST = 2;
    public static final int MESSAGE_READ = 3;
    public static final int MESSAGE_WRITE = 4;


    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
    public static final String BYTES = "bytes";
    public static final String READ_MSG = "read_msg";
    public static final String BYTES_LENGTH = "bytes_length";

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothConnectService connectService;
    private Activity activity;
    private List<BluetoothConnectListener> connectListeners = new ArrayList<>();
    private List<BluetoothFoundListener> foundListeners = new ArrayList<>();
    private List<OnMessageListener> receivedMessageListeners = new ArrayList<>();
    private String readMsg = "";

    private SpBLE spBLE;


    @Override
    public void onManagerCreate(BaseApplication application) {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    /**
     * 检查已连接的蓝牙设备数量
     */
    public void checkConnectedDevice() {
        List<BluetoothDevice> devices = ConnectedBluetooth.getConnectedBtDevice(bluetoothAdapter);
        log("已连接设备数：" + devices.size());
        for (BluetoothDevice device : devices) {
            log("设备名称：" + device.getName() + "    设备地址: " + device.getAddress());
        }
    }

    /**
     * 蓝牙是否开启
     *
     * @return
     */
    public boolean bluetoothIsEnabled() {
        if (bluetoothAdapter == null) {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        if (bluetoothAdapter == null) {
            showToast("该设备不支持蓝牙");
            return false;
        }
        return bluetoothAdapter.isEnabled();
    }

    /**
     * 请求开启蓝牙
     */
    public void requestOpenBluetooth() {
        if (bluetoothAdapter == null) {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        if (bluetoothAdapter == null) {
            showToast("该设备不支持蓝牙");
            return;
        }
        if (!bluetoothAdapter.isEnabled()) {
            //请求打开并可见
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivity(intent);
        }
    }

    /**
     * 注册广播 (在需要使用蓝牙的模块)
     */
    public void registerReceiver(Activity activity) {
        this.activity = activity;
        initBluetoothOpenStateReceiver();
        initBluetoothConnectReceiver();
        initBluetoothFoundReceiver();
    }

    /**
     * 取消注册广播
     */
    public void unregisterReceiver() {
        activity.unregisterReceiver(connectReceiver);
        activity.unregisterReceiver(foundReceiver);
        activity.unregisterReceiver(openStateReceiver);
        activity = null;
        System.gc();
    }

    /**
     * 注册蓝牙开关状态广播
     */
    private void initBluetoothOpenStateReceiver() {
        IntentFilter intentFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        activity.registerReceiver(openStateReceiver, intentFilter);
    }

    /**
     * 注册蓝牙连接状态广播监听
     */
    private void initBluetoothConnectReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        activity.registerReceiver(connectReceiver, filter);
    }

    /**
     * 注册蓝牙扫描广播
     */
    private void initBluetoothFoundReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        activity.registerReceiver(foundReceiver, filter);
    }

    private void showToast(String msg) {
        ToastUtil.showToast(activity, msg, Gravity.CENTER);
    }

    private void log(String msg) {
        LogUtil.v(msg);
    }

    /**
     * 扫描蓝牙设备
     */
    public void doDiscovery() {
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }
        bluetoothAdapter.startDiscovery();
    }

    /**
     * 停止扫描
     */
    public void cancelDiscovery() {
        bluetoothAdapter.cancelDiscovery();
    }

    /**
     * 连接设备
     *
     * @param device
     */
    public void connect(BluetoothDevice device) {
        //DEVICE_TYPE_CLASSIC 1 BR/EDR
        //DEVICE_TYPE_LE 2 LE-only
        //DEVICE_TYPE_DUAL 3 双模式BR/EDR/LE
        //DEVICE_TYPE_UNKNOWN 0 蓝牙不可用
        if (device.getType() == BluetoothDevice.DEVICE_TYPE_DUAL) {
            if (connectService == null) {
                connectService = new BluetoothConnectService(activity, this);
            }
            connectService.connect(device);
        } else {
            if (spBLE == null) {
                spBLE = new SpBLE(activity, this);
            }
            spBLE.connect(device, activity);
        }
    }

    /**
     * 断开连接
     */
    public void disConnect() {
        if (connectService != null) {
            connectService.stop();
        }
        if (spBLE != null) {
            spBLE.disconnect();
        }
    }

    /**
     * 发送数据
     *
     * @param msg
     */
    public void write(String msg) {
        try {
            if (connectService != null) {
                connectService.write(msg.getBytes("GBK"));
            }
            if (spBLE != null) {
                spBLE.write(msg.getBytes("GBK"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 蓝牙开启状态广播监听
     */
    private BroadcastReceiver openStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
            switch (state) {
                case BluetoothAdapter.STATE_ON:
                    showToast("蓝牙已打开");
                    break;
                case BluetoothAdapter.STATE_OFF:
                    showToast("蓝牙已关闭");
                    break;
                case BluetoothAdapter.STATE_TURNING_ON:
                    showToast("蓝牙正在打开");
                    break;
                case BluetoothAdapter.STATE_TURNING_OFF:
                    showToast("蓝牙正在关闭");
                    break;
            }
        }
    };

    /**
     * 蓝牙连接广播
     */
    private BroadcastReceiver connectReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            switch (intent.getAction()) {
                case BluetoothDevice.ACTION_ACL_CONNECTED://已连接
                    notificationConnectChange("已连接",0, device);
                    break;
                case BluetoothDevice.ACTION_ACL_DISCONNECTED://断开连接
                    notificationConnectChange("已断开连接",1, device);
                    break;
            }
        }
    };


    /**
     * 通知外部监听，蓝牙连接状态改变
     *
     * @param state  已连接  断开连接
     * @param code 0-已连接  1-已断开连接
     * @param device 蓝牙设备对象
     */
    private void notificationConnectChange(String state,int code, BluetoothDevice device) {
        log(device.getName() + "   " + state);
        for (BluetoothConnectListener listener : connectListeners) {
            listener.onConnectChange(state,code, device);
        }
    }

    /**
     * 添加蓝牙连接监听
     *
     * @param listener
     */
    public void addBluetoothConnectListener(BluetoothConnectListener listener) {
        connectListeners.add(listener);
    }

    /**
     * 移除蓝牙连接监听
     *
     * @param listener
     */
    public void removeBluetoothConnectListener(BluetoothConnectListener listener) {
        connectListeners.remove(listener);
    }

    public interface BluetoothConnectListener {
        void onConnectChange(String state, int code, BluetoothDevice device);

    }

    /**
     * 蓝牙扫描广播
     */
    private BroadcastReceiver foundReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case BluetoothDevice.ACTION_FOUND:
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    notifyBluetoothFoundChange(ACTION_FOUND, device);
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED://扫描结束
                    notifyBluetoothFoundChange(ACTION_FINISHED, null);
                    break;
            }
        }
    };


    private void notifyBluetoothFoundChange(int action, BluetoothDevice device) {
        for (BluetoothFoundListener listener : foundListeners) {
            listener.onFoundDevice(action, device);
        }
    }

    /**
     * 添加蓝牙扫描结果监听
     *
     * @param listener
     */
    public void addBluetoothFoundListener(BluetoothFoundListener listener) {
        foundListeners.add(listener);
    }

    /**
     * 移除蓝牙扫描结果监听
     *
     * @param listener
     */
    public void removeBluetoothFoundListener(BluetoothFoundListener listener) {
        foundListeners.remove(listener);
    }

    public interface BluetoothFoundListener {
        void onFoundDevice(int action, BluetoothDevice device);

    }

    /**
     * 接收子线程传输的数据
     */
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case MESSAGE_STATE_CHANGE://连接状态变化
                handleConnectStateChange(msg);
                break;
            case MESSAGE_DEVICE_NAME://已连接的设备名称
                log("设备 " + msg.getData().getString(DEVICE_NAME) + " 已连接");
                break;
            case MESSAGE_TOAST://toast提示
//                showToast(msg.getData().getString(TOAST));
                break;
            case MESSAGE_READ://收到远程设备发送的数据
                handleReadMessage(msg);
                break;
            case MESSAGE_WRITE://发送数据结果
                log("发送结果：" + msg.arg1);
                for (OnMessageListener listener : receivedMessageListeners) {
                    listener.onSendResult(msg.arg1);
                }
                break;
        }
    }

    /**
     * 处理连接状态变化
     *
     * @param msg
     */
    private void handleConnectStateChange(Message msg) {
        switch (msg.arg1) {
            case BluetoothConnectService.STATE_LISTEN:
//                log("等待传入连接中");
                break;
            case BluetoothConnectService.STATE_CONNECTING:
//                log("开始传出连接中");
                break;
            case BluetoothConnectService.STATE_CONNECTED:
                log("已连接到远程设备");
                break;
        }
    }

    /**
     * 处理远程设备发送的数据
     *
     * @param msg
     */
    private void handleReadMessage(Message msg) {
        byte[] buffer = msg.getData().getByteArray(BYTES);
//        String readMsg = msg.getData().getString(READ_MSG);
        try {
            String readMsg = new String(buffer, 0, msg.getData().getInt(BYTES_LENGTH), "GBK");
            this.readMsg += readMsg;
            notifyReceivedMessage();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通知界面 收到消息
     */
    private void notifyReceivedMessage() {
        postDelayed(() -> {
            if (!TextUtils.isEmpty(this.readMsg)) {
                log("收到消息：" + this.readMsg);
                for (OnMessageListener listener : receivedMessageListeners) {
                    listener.onReceivedMessage(this.readMsg);
                }
                this.readMsg = "";
            }
        }, 1000);
    }

    /**
     * 添加消息监听
     *
     * @param listener
     */
    public void addOnReceivedMessageListener(OnMessageListener listener) {
        this.receivedMessageListeners.add(listener);
    }

    /**
     * 移除消息监听
     *
     * @param listener
     */
    public void removeOnReceivedMessageListener(OnMessageListener listener) {
        this.receivedMessageListeners.remove(listener);
    }

    public interface OnMessageListener {
        /**
         * 发送结果
         * @param code 大于0表示成功
         */
        void onSendResult(int code);
        /**
         * 收到消息
         * @param message
         */
        void onReceivedMessage(String message);
    }

}

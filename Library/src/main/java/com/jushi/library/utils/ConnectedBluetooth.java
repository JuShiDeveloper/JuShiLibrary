package com.jushi.library.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 获取已连接的蓝牙设备
 */
public class ConnectedBluetooth {

    //获取已连接的蓝牙设备
    public static List<BluetoothDevice> getConnectedBtDevice(BluetoothAdapter adapter) {
        List<BluetoothDevice> deviceList = new ArrayList<>();
        try {
            Set<BluetoothDevice> devices = adapter.getBondedDevices(); //集合里面包括已绑定的设备和已连接的设备
            for (BluetoothDevice device : devices) {
                Method isConnectedMethod = BluetoothDevice.class.getDeclaredMethod("isConnected", (Class[]) null);
                isConnectedMethod.setAccessible(true);
                boolean isConnected = (boolean) isConnectedMethod.invoke(device, (Object[]) null);
                if (isConnected) { //根据状态来区分是已连接的还是已绑定的，isConnected为true表示是已连接状态。
                    deviceList.add(device);
                }
            }
            return deviceList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceList;
    }

}

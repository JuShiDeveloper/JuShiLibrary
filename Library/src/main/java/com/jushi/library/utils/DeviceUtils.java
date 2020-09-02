package com.jushi.library.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import com.jushi.library.base.BaseApplication;

import java.util.UUID;


/**
 * 设备工具类，本类提供了所有关于设备的工具方法
 *
 * @author wyf
 * @since 2018-11-25
 */
public class DeviceUtils {
    // 获取版本号
    public static String getStringDeviceVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 检测应用程序是否安装
     */
    public static final boolean isApkInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * QQ是否安装
     *
     * @return
     */
    public static boolean isQQInstalled() {
        return isApkInstalled(BaseApplication.getInstance(), "com.tencent.mobileqq");
    }

    /**
     * QQ空间是否安装
     *
     * @return
     */
    public static boolean isQzoneInstalled() {
        return isApkInstalled(BaseApplication.getInstance(), "com.qzone");
    }

    /**
     * 微博是否安装
     *
     * @return
     */
    public static boolean isWeiboInstalled() {
        return isApkInstalled(BaseApplication.getInstance(), "com.sina.weibo");
    }

    /**
     * 微信是否安装
     *
     * @return
     */
    public static boolean isWeixinInstalled() {
        return isApkInstalled(BaseApplication.getInstance(), "com.tencent.mm");
    }

    public static String getPhoneVersion() {
        return Build.VERSION.CODENAME;
    }

    public static int getDeviceSdk() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取MacAddress
     *
     * @return
     */
    public static String getMacAddress() {
        String macAddress = "";
        WifiManager wifi = (WifiManager) BaseApplication.getInstance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifi != null) {
            WifiInfo info = wifi.getConnectionInfo();
            // 如果Wifi关闭的时候，硬件设备可能无法返回MAC ADDRESS
            if (null != info)
                macAddress = ((null == info.getMacAddress()) ? "" : info.getMacAddress());
        }
        return macAddress;
    }

    /**
     * 设备的唯一标识
     *
     * @return
     */
    public static String getIMEI() {
        String m_szDevIDShort = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (Build.CPU_ABI.length() %
                10) + (Build.DEVICE.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build
                .PRODUCT.length() % 10);
        String serial = null;
        try {
            serial = Build.class.getField("SERIAL").get(null).toString();
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            serial = "serial"; // some value
        }
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getPhoneStyle() {
        return Build.MODEL.replace(" ", "_");
    }

    /**
     * 获取手机品牌及型号
     *
     * @return
     */
    public static String getPhoneBrandAndStyle() {
        String rom = "unknow";
        if (OSUtils.isEmui()) { //华为系统
            rom = "EMUI";
        }
        if (OSUtils.isVivo()) { //vivo系统
            rom = "FuntouchOS";
        }
        if (OSUtils.isOppo()) { //oppo系统
            rom = "ColorOs";
        }
        if (OSUtils.isMiui()) { //XiaoMi系统
            rom = "MIUI";
        }
        if (OSUtils.isFlyme()) { //魅族系统
            rom = "Flyme";
        }
        if (OSUtils.is360()) { //360系统
            rom = "360";
        }
        if (OSUtils.isSmartisan()) { //三星系统
            rom = "Smartisan";
        }
        return rom + "+" + Build.BRAND + " " + Build.MODEL.replace(" ", "_");
    }
}

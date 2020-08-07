package com.jushi.library.utils;

import android.util.Log;

import cn.longmaster.health.BuildConfig;
import cn.longmaster.health.app.HApplication;
import cn.longmaster.health.app.HConfig;
import cn.longmaster.health.app.LogService;

/**
 * 日志打印封装类
 */
public class Logger {

    private String TAG;

    public Logger(String TAG) {
        this.TAG = TAG;
    }

    public static void log(String tag, String msg) {
        if (HConfig.IS_DEBUG_MODE)
            Log.d(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (HConfig.IS_DEBUG_MODE)
            Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (HConfig.IS_DEBUG_MODE)
            Log.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (HConfig.IS_DEBUG_MODE)
            Log.w(tag, msg);
    }

    public static void w(String tag, String msg, Throwable throwable) {
        if (HConfig.IS_DEBUG_MODE)
            Log.w(tag, msg, throwable);
    }

    public static void e(String tag, String msg) {
        if (HConfig.IS_DEBUG_MODE)
            Log.e(tag, msg);
    }

    public static void e(String tag, String msg, Throwable throwable) {
        if (HConfig.IS_DEBUG_MODE)
            Log.e(tag, msg, throwable);
    }

    public static void v(String tag, String msg) {
        if (BuildConfig.DEBUG)
            Log.v(tag, msg);
    }

    /**
     * 将日志打印到文件中，日志文件路径：<b>SDCard/health/log/今天日期.txt</b>
     *
     * @param msg 要打印到日志文件中的内容 ， 日志一行样式：<b>HH:mm:ss --> + msg + \r\n</b>
     */
    public static void logFile(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            HApplication context = HApplication.getInstance();
            try {
                LogService.startActionLog(context, tag, msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void i(String msg) {
        i(TAG, msg);
    }

    public void e(String msg) {
        e(TAG, msg);
    }

    public void d(String msg) {
        d(TAG, msg);
    }

    public void w(String msg) {
        w(TAG, msg);
    }
}

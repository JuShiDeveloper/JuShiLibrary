package com.jushi.library.utils;

import android.util.Log;

import com.jushi.library.BuildConfig;


/**
 * 日志打印封装类
 */
public class Logger {

    private String TAG;

    public Logger(String TAG) {
        this.TAG = TAG;
    }

    public static void log(String tag, String msg) {
        if (BuildConfig.DEBUG)
            Log.d(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG)
            Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (BuildConfig.DEBUG)
            Log.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (BuildConfig.DEBUG)
            Log.w(tag, msg);
    }

    public static void w(String tag, String msg, Throwable throwable) {
        if (BuildConfig.DEBUG)
            Log.w(tag, msg, throwable);
    }

    public static void e(String tag, String msg) {
        if (BuildConfig.DEBUG)
            Log.e(tag, msg);
    }

    public static void e(String tag, String msg, Throwable throwable) {
        if (BuildConfig.DEBUG)
            Log.e(tag, msg, throwable);
    }

    public static void v(String tag, String msg) {
        if (BuildConfig.DEBUG)
            Log.v(tag, msg);
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

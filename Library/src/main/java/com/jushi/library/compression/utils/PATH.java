package com.jushi.library.compression.utils;

import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * 相关路径信息类
 *
 * @author wyf
 */
public class PATH {
    private static WeakReference<Context> weakReference;

    public static void initialize(Context context) {
        weakReference = new WeakReference(context);
        FILE.createDir(getImageSaveDir());
    }

    /**
     * 图片另存为SDCard/Android/data/目录
     *
     * @return
     */
    public static String getImageSaveDir() {
        return weakReference.get().getExternalFilesDir(null).toString() + "/photo/";
    }
}

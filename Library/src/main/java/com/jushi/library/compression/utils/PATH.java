package com.jushi.library.compression.utils;

import android.content.Context;

import com.jushi.library.base.BaseApplication;

import java.lang.ref.WeakReference;

/**
 * 相关路径信息类
 *
 * @author wyf
 */
public class PATH {

    public static void initialize(Context context) {
        FILE.createDir(getImageSaveDir());
    }

    /**
     * 图片另存为SDCard/Android/data/目录
     *
     * @return
     */
    public static String getImageSaveDir() {
        return BaseApplication.getInstance().getExternalCacheDir().getPath() + "/photo/";
    }
}

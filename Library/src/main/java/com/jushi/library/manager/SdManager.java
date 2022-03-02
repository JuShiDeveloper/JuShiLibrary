package com.jushi.library.manager;

import android.os.Environment;
import android.text.TextUtils;

import com.jushi.library.base.BaseApplication;
import com.jushi.library.base.BaseManager;
import com.jushi.library.utils.LogUtil;

import java.io.File;


/**
 *
 */
public class SdManager extends BaseManager {
    /**** 主目录 ***/
    private final String DIR_ROOT = "/ShiYongJun/";
    /*** 存放文件 ***/
    private final String FILES = "files/";
    /*** 存放图片 ***/
    private final String IMAGE = "image/";

    private final String APK = "apk/";

    private String rootPath;
    private String filePath;
    private String imagePath;
    private String apkPath;

    @Override
    public void onManagerCreate(BaseApplication application) {
        rootPath = getDirectoryPath(application) + DIR_ROOT;
        filePath = rootPath + FILES;
        imagePath = rootPath + IMAGE;
        apkPath = rootPath + APK;
        mkdir(filePath,imagePath,apkPath);
    }

    /**
     * 获得系统内部sdcard路径
     */
    private String getDirectoryPath(BaseApplication application) {
        return application.getExternalCacheDir().getPath();
    }

    /**
     * 检测sdcard是否可用
     */
    public boolean available() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public String getFileName(String url) {
        if (!TextUtils.isEmpty(url)) {
            return (url.hashCode() + "").replace("-", "_");
        } else {
            return "";
        }
    }

    /**
     * 获取文件下载保存路径
     * @return
     */
    public String getDownloadFilePath() {
        return filePath;
    }

    /**
     * 获取图片存放路径
     * @return
     */
    public String getImagePath(){
        return imagePath;
    }

    /**
     * 版本更新 安装包下载存放位置
     * @return
     */
    public String getApkPath(){
        return apkPath;
    }

    private final synchronized boolean mkdir(String... filePaths) {
        try {
            for (String filePath : filePaths) {
                LogUtil.v("->mkdir()->filePath:" + filePath);
                if (filePath == null) {
                    return false;
                }
                if (!filePath.endsWith(File.separator))
                    filePath = filePath.substring(0, filePath.lastIndexOf(File.separatorChar));
                File file = new File(filePath);
                if (!file.exists()) {
                    file.mkdirs();
                } else if (file.isFile()) {
                    file.delete();
                    file.mkdirs();
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public synchronized boolean mkdirs(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isDirectory()) {
            return true;
        } else {
            if (file.exists()) {
                file.delete();
            }
            file.mkdirs();
            return true;
        }
    }

}

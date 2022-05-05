package com.jushi.library.http;

import android.view.Gravity;

import com.jushi.library.BuildConfig;
import com.jushi.library.R;
import com.jushi.library.base.BaseApplication;
import com.jushi.library.utils.LogUtil;
import com.jushi.library.utils.SPUtils;
import com.jushi.library.utils.ToastUtil;

/**
 * 接口ip地址/域名配置
 */
public abstract class HttpUrlConfig {
    public static final String BASE_URL; //接口host地址配置
    public static final String BASE_H5_URL; //H5host地址配置
    public static final String BASE_UPLOAD_FILE_URL;

    static {
        if (BuildConfig.DEBUG){
            if (SPUtils.getInstance(BaseApplication.getInstance()).getBoolean("test_release",false)){
                BASE_URL = "https://47.108.134.102:23004/app/api";//测试服测试正式库使用
                BASE_UPLOAD_FILE_URL = "https://47.108.134.102:23004/app/api/fileapp";//测试服测试正式库使用
                BASE_H5_URL = "https://47.108.134.102:23004/#/";
                ToastUtil.showToast(BaseApplication.getInstance(),"当前测试版本为正式服地址", Gravity.CENTER);
            }else {
                BASE_URL = "https://58.16.58.76:8003/app/api";
                BASE_UPLOAD_FILE_URL = "https://58.16.58.76:8003/app/api/fileapp";
                BASE_H5_URL = "https://58.16.58.76:8003/#/";
//                BASE_H5_URL = "http://192.168.1.112:8080/truffle/#/";
            }
        }else {
            BASE_URL = "https://47.108.134.102:23004/app/api";
            BASE_UPLOAD_FILE_URL = "https://47.108.134.102:23004/app/api/fileapp";
            BASE_H5_URL = "https://47.108.134.102:23004/#/";
        }
    }

}

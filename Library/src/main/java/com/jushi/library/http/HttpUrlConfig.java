package com.jushi.library.http;

import com.jushi.library.BuildConfig;

/**
 * 接口ip地址/域名配置
 */
public abstract class HttpUrlConfig {
    public static String BASE_URL = baseUrl();

    /**
     * 接口host地址配置
     *
     * @return
     */
    private static String baseUrl() {
        if (BuildConfig.DEBUG) {
            return "http://139.159.248.233:8085/";
//            return "http://192.168.1.107:20000";
        } else {
            return "http://139.159.248.233:8085/";
        }
    }

    /**
     * H5页面host地址配置
     *
     * @return
     */
    public static String h5Host() {
        if (BuildConfig.DEBUG) {
//            return "http://139.159.248.233:8086/";
            return "http://192.168.1.111:8080/";
        } else {
            return "http://139.159.248.233:8086/";
        }
    }
}

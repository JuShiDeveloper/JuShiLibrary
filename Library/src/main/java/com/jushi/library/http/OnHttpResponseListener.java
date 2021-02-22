package com.jushi.library.http;

public interface OnHttpResponseListener<Data> {
    /**
     * 请求成功响应
     *
     * @param code   响应结果码
     * @param router 请求的接口路由
     * @param data   响应数据
     */
    void onHttpRequesterResponse(int code, String router, Data data);

    /**
     * 请求失败响应
     *
     * @param code    响应结果码
     * @param router  请求的接口路由
     * @param message 失败信息
     */
    void onHttpRequesterError(int code, String router, String message);
}

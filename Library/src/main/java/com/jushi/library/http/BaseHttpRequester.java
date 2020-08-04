package com.jushi.library.http;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * 提供给自定义类继承的HTTP请求基类
 */
public abstract class BaseHttpRequester<Data> extends BaseHttp {
    private OnHttpResponseListener<Data> responseListener;

    public BaseHttpRequester(@NonNull OnHttpResponseListener<Data> listener) {
        responseListener = listener;
    }

    @Override
    protected String onHttpUrl() {
        return HttpUrlConfig.BASE_URL + onRequestRouter();
    }

    @Override
    protected void onRequestSuccess(int code, String message, JSONObject jsonObject) throws JSONException {
        if (code == 0) {
            responseListener.onHttpRequesterResponse(code, onRequestRouter(), onDumpData(jsonObject));
        } else {
            responseListener.onHttpRequesterResponse(code, onRequestRouter(), onDumpDataError(jsonObject));
        }
    }

    @Override
    protected void onError(int code, String errorMsg) {
        responseListener.onHttpRequesterError(code, onRequestRouter(), errorMsg);
    }

    /**
     * 返回请求的服务器路由(或者方法)
     *
     * @return 例： "/login"
     */
    protected abstract String onRequestRouter();

    /**
     * 请求成功 子类在该方法中做数据解析操作
     *
     * @param jsonObject
     * @return
     * @throws JSONException
     */
    protected abstract Data onDumpData(JSONObject jsonObject) throws JSONException;

    /**
     * 请求失败  子类在该方法中做数据解析操作
     *
     * @param jsonObject
     * @return
     * @throws JSONException
     */
    protected abstract Data onDumpDataError(JSONObject jsonObject) throws JSONException;
}

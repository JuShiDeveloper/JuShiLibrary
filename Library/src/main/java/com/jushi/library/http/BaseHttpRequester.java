package com.jushi.library.http;

import android.support.annotation.NonNull;

import com.jushi.library.base.BaseApplication;
import com.jushi.library.base.BaseManager;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Response;


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
    protected void onRequestSuccess(int code, String message, JSONObject jsonObject, Response response) throws JSONException {
        if (code == 410) {//未登录或登录信息过期
            loginOverdue();
            return;
        }
        JSONObject dataObj = jsonObject.getJSONObject("data");
        if (response.request().url().toString().contains("user/authority/login")) {
            String authorization = response.header("authorization");
            dataObj.put("authorization", authorization);
        }
        BaseApplication.getInstance().getHandler().post(() -> {
            try {
                if (code == 200) {
                    responseListener.onHttpRequesterResponse(code, onRequestRouter(), onDumpData(dataObj));
                } else {
                    responseListener.onHttpRequesterResponse(code, onRequestRouter(), onDumpDataError(dataObj));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                onError(-1, "Json 转换异常");
            }
        });
    }

    @Override
    protected void onError(int code, String errorMsg) {
        BaseApplication.getInstance().getHandler().post(() -> {
            responseListener.onHttpRequesterError(code, onRequestRouter(), errorMsg);
        });
    }

    /**
     * 登录失效或未登录
     */
    protected void loginOverdue() {
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

    protected <V extends BaseManager> V getManager(Class<V> cls) {
        return BaseApplication.getInstance().getManager(cls);
    }
}

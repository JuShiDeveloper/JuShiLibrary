package com.jushi.library.http;

import android.util.Log;


import com.jushi.library.BuildConfig;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * http请求基类
 */
abstract class BaseHttp implements Callback {
    private final String TAG = BaseHttp.class.getSimpleName();
    private static final OkHttpClient httpClient;
    private Call call;
    private boolean isResponse = false;

    static {
        httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
    }

    public void get() {
        Request request = new Request.Builder()
                .url(onHttpUrl() + getMethodParams())
                .headers(Headers.of(getHeaders()))
                .get()
                .build();
        call = httpClient.newCall(request);
        call.enqueue(this);
        isResponse = false;
        if (BuildConfig.DEBUG)
            Log.v(TAG, "request url = " + onHttpUrl() + getMethodParams());
    }

    public void post() {
        Request request = new Request.Builder()
                .url(onHttpUrl())
                .headers(Headers.of(getHeaders()))
                .post(RequestBody.create(postMethodParams(), MediaType.parse("application/json; charset=utf-8")))
                .build();
        call = httpClient.newCall(request);
        call.enqueue(this);
        isResponse = false;
        if (BuildConfig.DEBUG)
            Log.v(TAG, "request url = " + onHttpUrl() + " " + postMethodParams());
    }

    /**
     * 取消请求
     */
    public void cancel() {
        if (isResponse) return;
        call.cancel();
        if (BuildConfig.DEBUG)
            Log.v(TAG, "cancel request url = " + onHttpUrl() + " " + postMethodParams());
    }

    private String getMethodParams() {
        List<String> keys = new ArrayList<>(getParamsObject().keySet());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("?");
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            if (i != 0) {
                stringBuilder.append("&");
            }
            stringBuilder.append(key);
            stringBuilder.append("=");
            stringBuilder.append(getParamsObject().get(key));
        }
        return stringBuilder.toString();
    }

    private String postMethodParams() {
        JSONObject jsonObject = new JSONObject();
        List<String> keys = new ArrayList<>(getParamsObject().keySet());
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            try {
                jsonObject.put(key, getParamsObject().get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject.toString();
    }

    /**
     * 请求头参数
     *
     * @return
     */
    private Map<String, String> getHeaders() {
        String time = System.currentTimeMillis() / 1000 + "";
        String randomStr = Encoder.encodeByMD5(System.currentTimeMillis() + "" + (int) (Math.random() * 1000000));
        Map<String, String> headers = new HashMap<>();
        headers.put("app-id", "10004");
        headers.put("nonce-str", randomStr);
        headers.put("timestamp", time);

        Map<String, String> signParams = new HashMap<>(changeParams(getParamsObject()));
        signParams.put("app_id", "10004");
        signParams.put("nonce_str", randomStr);
        signParams.put("timestamp", time);
        headers.put("sign", makeSign(signParams, "s5bMpNjp9DePwVZLLzZmrHPP45unpMVu"));

        return headers;
    }

    private Map<String, String> changeParams(Map<String, Object> params) {
        Map<String, String> newParams = new HashMap<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            newParams.put(entry.getKey(), entry.getValue().toString());
        }
        return newParams;
    }

    /**
     * 公共参数
     *
     * @return
     */
    private Map<String, Object> getParamsObject() {
        Map<String, Object> params = new HashMap<>();
        // 放入公共参数
        params.put("sid", "c93745878e749c2ccf5e1825f1d5663f");
        onParams(params);
        return params;
    }

    /**
     * 签名
     *
     * @param params
     * @param authKey
     * @return
     */
    private String makeSign(Map<String, String> params, String authKey) {
        ArrayList<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            if (!params.get(key).equals("")) {
                if (i != 0) {
                    stringBuilder.append("&");
                }
                stringBuilder.append(key);
                stringBuilder.append("=");
                stringBuilder.append(params.get(key));
            }
        }
        stringBuilder.append("&key=");
        stringBuilder.append(authKey);
        return Encoder.encodeByMD5(stringBuilder.toString());
    }

    /******************************  结果响应   *******************************************/

    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {
        onError(-1, e.getMessage());
        isResponse = true;
    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        isResponse = true;
        int code = -1;
        String message = "";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response.body().string());
            if (BuildConfig.DEBUG) {
                Log.v(TAG, "response url = " + onHttpUrl() + " result = " + jsonObject.toString());
            }
            code = jsonObject.getInt("code");
            message = jsonObject.getString("message");
            onRequestSuccess(code, message, jsonObject);
        } catch (JSONException e) {
            onError(code, e.getMessage());
        }
    }

    /**
     * 返回请求的URL地址
     *
     * @return
     */
    protected abstract String onHttpUrl();

    /**
     * 请求参数配置
     *
     * @param params
     */
    protected abstract void onParams(Map<String, Object> params);

    /**
     * 请求结果成功响应
     *
     * @param code
     * @param message
     * @param jsonObject
     * @throws JSONException
     */
    protected abstract void onRequestSuccess(int code, String message, JSONObject jsonObject) throws JSONException;

    /**
     * 请求结果失败响应
     *
     * @param code
     * @param errorMsg
     */
    protected abstract void onError(int code, String errorMsg);
}

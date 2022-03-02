package com.jushi.library.http;


import com.jushi.library.BuildConfig;
import com.jushi.library.base.BaseApplication;
import com.jushi.library.manager.UserManager;
import com.jushi.library.utils.LogUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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
    private static final OkHttpClient httpClient;
    private Call call;
    private boolean isResponse = false;
    protected UserManager userManager = BaseApplication.getInstance().getManager(UserManager.class);

    static {
        httpClient = new OkHttpClient().newBuilder()
                .sslSocketFactory(createSSLSocketFactory(),new TrustAllCerts())
                .hostnameVerifier(new TrustAllHostnameVerifier())
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
    }

    //自定义SS验证相关类
    private static class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
//            SSLContext sc = SSLContext.getInstance("TLS");
//            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
            ssfFactory = sslContext.getSocketFactory();
        } catch (Exception e) {
        }
        return ssfFactory;
    }

    public void get() {
        LogUtil.v("request url = " + onHttpUrl() + getMethodParams());
        Request request = new Request.Builder()
                .url(onHttpUrl() + getMethodParams())
                .headers(Headers.of(getHeaders()))
                .get()
                .build();
        call = httpClient.newCall(request);
        call.enqueue(this);
        isResponse = false;
    }

    public void post() {
        LogUtil.v("request url = " + onHttpUrl() + " " + postMethodParams());
        Request request = new Request.Builder()
                .url(onHttpUrl())
                .headers(Headers.of(getHeaders()))
                .post(RequestBody.create(postMethodParams(), MediaType.parse("application/json")))
                .build();
        call = httpClient.newCall(request);
        call.enqueue(this);
        isResponse = false;
    }
    public void patch() {
        LogUtil.v("request url = " + onHttpUrl() + " " + postMethodParams());
        Request request = new Request.Builder()
                .url(onHttpUrl())
                .headers(Headers.of(getHeaders()))
                .patch(RequestBody.create(postMethodParams(), MediaType.parse("application/json")))
                .build();
        call = httpClient.newCall(request);
        call.enqueue(this);
        isResponse = false;
    }

    /**
     * 取消请求
     */
    public void cancel() {
        if (isResponse) return;
        call.cancel();
        if (BuildConfig.DEBUG)
            LogUtil.v("cancel request url = " + onHttpUrl() + " " + postMethodParams());
    }

    private String getMethodParams() {
        List<String> keys = new ArrayList<>(getParamsObject().keySet());
        StringBuilder stringBuilder = new StringBuilder();
        if (keys.size()==0)return "";
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
        Map<String, String> headers = new HashMap<>();
        if (userManager.getUserInfo() != null) {
            headers.put("authorization", userManager.getUserInfo().getToken());
            headers.put("bossId", userManager.getBossId());
        }
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
//        params.put("authorization", userManager.getUserInfo().getAuthorization());
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
        LogUtil.v("response url = " + onHttpUrl() + " result = " +  e.getMessage());
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
            LogUtil.v("response url = " + onHttpUrl() + " result = " + jsonObject.toString());
            if (response.request().url().toString().contains("apis.map.qq.com")){
                onRequestSuccess(code, message, jsonObject, response);
            }else {
                code = jsonObject.getInt("code");
                message = jsonObject.getString("msg");
                onRequestSuccess(code, message, jsonObject, response);
            }
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
    protected abstract void onRequestSuccess(int code, String message, JSONObject jsonObject, Response response) throws JSONException;

    /**
     * 请求结果失败响应
     *
     * @param code
     * @param errorMsg
     */
    protected abstract void onError(int code, String errorMsg);
}

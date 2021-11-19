package com.library.jushi.jushilibrary;

import android.support.annotation.NonNull;


import com.jushi.library.http.BaseHttpRequester;
import com.jushi.library.http.Encoder;
import com.jushi.library.http.OnHttpResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class TestPOSTRequester extends BaseHttpRequester<String> {

    public TestPOSTRequester(@NonNull OnHttpResponseListener<String> listener) {
        super(listener);
    }

    @Override
    protected String onRequestRouter() {
        return "/doctor/app/password_verify";
    }

    @Override
    protected String onDumpData(JSONObject jsonObject) throws JSONException {
        return jsonObject.toString();
    }

    @Override
    protected String onDumpDataError(JSONObject jsonObject) throws JSONException {
        return "";
    }

    @Override
    protected void onParams(Map<String, Object> params) {
        params.put("user_account", "15185190441");
        params.put("user_password", Encoder.encodeByMD5("123456qwe"));
    }
}

package com.library.jushi.jushilibrary;

import android.support.annotation.NonNull;


import com.jushi.library.http.BaseHttpRequester;
import com.jushi.library.http.OnHttpResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class TestGETRequester extends BaseHttpRequester<String> {

    public TestGETRequester(@NonNull OnHttpResponseListener<String> listener) {
        super(listener);
    }

    @Override
    protected String onRequestRouter() {
        return "doctor/app/get_info_auth";
    }

    @Override
    protected String onDumpData(JSONObject jsonObject) throws JSONException {
        return jsonObject.getString("data");
    }

    @Override
    protected String onDumpDataError(JSONObject jsonObject) throws JSONException {
        return jsonObject.toString();
    }

    @Override
    protected void onParams(Map<String, Object> params){
        params.put("doc_id", 1008930);
    }
}

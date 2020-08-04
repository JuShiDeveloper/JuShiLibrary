package com.library.jushi.jushilibrary;

import android.util.Log;

import com.jushi.library.base.BaseFragmentActivity;
import com.jushi.library.http.OnHttpResponseListener;

public class TestActivity extends BaseFragmentActivity implements OnHttpResponseListener<String> {
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        new TestGETRequester(this).doGet();

        new TestPOSTRequester(this).doPost();
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onHttpRequesterResponse(int code, String router, String s) {
        switch (router) {
            case "doctor/app/get_info_auth":
                Log.v(MainActivity.class.getSimpleName(), "测试GET请求成功！ code = " + code + "  result = " + s);
                break;
            case "doctor/app/password_verify":
                Log.v(MainActivity.class.getSimpleName(), "测试POST请求成功！code = " + code + "  result = " + s);
                break;
        }
    }

    @Override
    public void onHttpRequesterError(int code, String router, String message) {
        switch (router) {
            case "doctor/app/get_info_auth":
                Log.v(MainActivity.class.getSimpleName(), "测试GET请求失败! code = "+code+"  message = " + message);
                break;
            case "doctor/app/password_verify":
                Log.v(MainActivity.class.getSimpleName(), "测试POST请求失败! code = "+code+"  message = " + message);
                break;
        }
    }
}

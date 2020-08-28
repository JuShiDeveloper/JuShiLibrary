package com.library.jushi.jushilibrary;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jushi.library.base.BaseFragmentActivity;
import com.jushi.library.http.OnHttpResponseListener;
import com.jushi.library.viewinject.FindViewById;
import com.jushi.library.viewinject.ViewInjecter;

public class TestActivity extends BaseFragmentActivity implements OnHttpResponseListener<String> {
    private TestGETRequester t;
    @FindViewById(R.id.btn_start)
    private Button btnStart;
    @FindViewById(R.id.btn_cancel)
    private Button btnCancel;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        t = new TestGETRequester(this);
        new TestPOSTRequester(this).post();
    }

    @Override
    protected void setListener() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t.get();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t.cancel();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        t.cancel();
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
                Log.v(MainActivity.class.getSimpleName(), "测试GET请求失败! code = " + code + "  message = " + message);
                break;
            case "doctor/app/password_verify":
                Log.v(MainActivity.class.getSimpleName(), "测试POST请求失败! code = " + code + "  message = " + message);
                break;
        }
    }
}

package com.library.jushi.jushilibrary;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jushi.library.base.BaseFragmentActivity;
import com.jushi.library.base.Manager;
import com.jushi.library.database.DatabaseManager;
import com.jushi.library.http.OnHttpResponseListener;
import com.jushi.library.utils.NetworkManager;
import com.jushi.library.viewinject.FindViewById;
import com.jushi.library.viewinject.ViewInjecter;

public class TestActivity extends BaseFragmentActivity implements OnHttpResponseListener<String>, NetworkManager.OnNetworkChangeListener {
    private TestGETRequester t;
    @FindViewById(R.id.btn_start)
    private Button btnStart;
    @FindViewById(R.id.btn_cancel)
    private Button btnCancel;
    @Manager
    private NetworkManager networkManager;
    @Manager
    private DatabaseManager databaseManager;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
//        App.getInstance().getManager(DatabaseManager.class);
    }

    @Override
    protected void initData() {
        t = new TestGETRequester(this);
        new TestPOSTRequester(this).post();
        testUseDataBase();
    }

    @Override
    protected void setListener() {
        btnStart.setOnClickListener(v -> t.get());
        btnCancel.setOnClickListener(v -> t.cancel());
        networkManager.addOnNetworkChangeListener(this);
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

    @Override
    public void onNetworkChange(int networkType) {
        // networkType != 1 为有网
        Log.v(MainActivity.class.getSimpleName(), "网络类型：" + networkType);
    }

    private void testUseDataBase() {
        databaseManager.submitDBTask(new DatabaseManager.DBTask<String>() {
            @Override
            public String runOnDBThread(SQLiteDatabase sqLiteDatabase) {
                //执行sql 语句
                Log.v(MainActivity.class.getSimpleName(), "执行SQL语句");
                return null;
            }

            @Override
            public void runOnUIThread(String s) {

            }
        });
    }

}

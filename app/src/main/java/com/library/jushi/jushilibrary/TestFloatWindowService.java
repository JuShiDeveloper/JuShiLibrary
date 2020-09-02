package com.library.jushi.jushilibrary;

import android.content.Intent;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.jushi.library.base.BaseFloatWindowService;
import com.jushi.library.utils.ToastUtil;

public class TestFloatWindowService extends BaseFloatWindowService {


    @Override
    protected View onWindowView(LayoutInflater inflater) { //返回悬浮窗视图
        return inflater.inflate(R.layout.test_float_window_layout, null);
    }

    @Override
    protected IBinder onIBinder(Intent intent) {
        return null;
    }

    @Override
    protected void initialize() {
        //处理其他逻辑
    }

    @Override
    protected boolean autoSuctionSide() { //自动吸边
        return true;
    }

    @Override
    protected long onAutoSuctionSideAnimatorDuration() { //自动吸边动画时长，单位毫秒，默认值为300ms
        return 1000;
    }

    @Override //在该方法更改 WindowManager.LayoutParams 的参数值，不设置则使用默认初始化设置的值
    protected void onWindowManagerLayoutParams(WindowManager.LayoutParams wmParams) {
//        wmParams.x = getResources().getDisplayMetrics().widthPixels - (getResources().getDisplayMetrics().widthPixels / 4);
//        wmParams.y = 200;
    }

    @Override
    protected void onWindowClickEvent() {
        ToastUtil.showToast(getApplicationContext(), "点击悬浮窗");
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

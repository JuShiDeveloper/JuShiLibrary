package com.jushi.library.manager;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;

import com.jushi.library.base.BaseApplication;
import com.jushi.library.base.BaseManager;
import com.jushi.library.utils.LogUtil;
import com.jushi.library.utils.ToastUtil;


/**
 * 剪切板操作管理类，用于管理剪切板操作
 * 1、应用启动时清空剪切板内容；
 * 2、监听到剪切板复制、剪切事件后开始计时5分钟内无其他操作则自动清空剪切板内容；
 * 3、在应用内输入框执行粘贴事件后清空剪切板内容；
 * <p>
 * create time 2020/1/21
 */
public class MClipboardManager extends BaseManager implements ClipboardManager.OnPrimaryClipChangedListener {
    private final String KEY_MSG_CONTENT_TX = "key_msg_content_tx";
    private ClipboardManager mClipboardManager;
    private Handler handler;
    private final int period = 5 * 60 * 1000;
    private boolean isClearContent = false;//剪切板状态变化
    private boolean needClear = false;//是否需要自动清除


    @Override
    public void onManagerCreate(BaseApplication application) {
        mClipboardManager = (ClipboardManager) application.getSystemService(Context.CLIPBOARD_SERVICE);
        mClipboardManager.addPrimaryClipChangedListener(this);
        handler = new Handler();
    }

    public void onPrimaryClipChanged(boolean isClearContent) {
        this.isClearContent = isClearContent;
    }

    public void setNeedClear(boolean needClear) {
        this.needClear = needClear;
    }

    /**
     * 剪切板复制、剪切事件回调
     */
    @Override
    public void onPrimaryClipChanged() {
        if (isClearContent&&needClear) return;
        //设置5分钟后自动清除
        handler.removeCallbacks(handlerCallback);
        handler.postDelayed(handlerCallback, period);
        LogUtil.v("剪切板复制、剪切事件回调");
    }

    /**
     * 复制数据到剪切板
     *
     * @param content 复制的内容
     */
    public void setPrimaryClip(String content) {
        isClearContent = false;
        mClipboardManager.setPrimaryClip(ClipData.newPlainText(KEY_MSG_CONTENT_TX, content));
        LogUtil.v( "复制数据到剪切板 ： "+content);
        handler.post(()->{
            ToastUtil.showToast(BaseApplication.getInstance(),"内容已复制成功", Gravity.CENTER);
        });
    }

    /**
     * 复制数据到剪切板
     *
     * @param content 复制的内容
     */
    public void setPrimaryClip(String content,String toastText) {
        isClearContent = false;
        mClipboardManager.setPrimaryClip(ClipData.newPlainText(KEY_MSG_CONTENT_TX, content));
        LogUtil.v( "复制数据到剪切板 ： "+content);
        handler.post(()->{
            ToastUtil.showToast(BaseApplication.getInstance(),toastText, Gravity.CENTER);
        });
    }

    /**
     * 清空剪切板内容
     */
    public void clearClipboardContent() {
        isClearContent = true;
        handler.removeCallbacks(handlerCallback);
        try {
            mClipboardManager.setPrimaryClip(mClipboardManager.getPrimaryClip());
            mClipboardManager.setText(null);
            LogUtil.v( "剪切板内容已清空");
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.v( "清空剪切板内容失败：" + e.getMessage());
        }
    }


    public void onDestroy() {
        if (mClipboardManager != null) {
            mClipboardManager.removePrimaryClipChangedListener(this);
        }
    }

    /**
     * 5分钟倒计时结束后清空剪切板内容
     */
    private Runnable handlerCallback = new Runnable() {
        @Override
        public void run() {
            clearClipboardContent();
        }
    };

}

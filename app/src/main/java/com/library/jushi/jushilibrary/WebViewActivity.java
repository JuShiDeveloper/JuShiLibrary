package com.library.jushi.jushilibrary;

import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.jushi.library.base.BaseWebViewActivity;
import com.jushi.library.utils.LogUtil;

/**
 * 网页加载界面示例
 */
public class WebViewActivity extends BaseWebViewActivity {

    public static final String EXTRA_KEY_WEB_URL = "web_url";

    private String url;

    @Override
    protected void getIntentData(Intent intent) {
        super.getIntentData(intent);
        url = intent.getStringExtra(EXTRA_KEY_WEB_URL);
    }

    @Override
    protected void initData() {
        onTitleChanged("测试WebView加载");
    }

    @Override
    protected void setListener() {
        super.setListener();

    }

    @Override
    protected boolean needShowProgressBar() {
        return true;
    }

    @Override
    protected String onWebUrl() {
        return url;
    }

    @Override
    protected void onTitleChanged(String title) {
        navigationBar.setTitleText(title);
    }

    @Override
    protected Object onJavascriptInterfaceObject() {
//        return this;
        return new Test();
    }

    @Override
    protected String onJavascriptInterfaceName() {
        return "greatHealth";
    }

    @JavascriptInterface
    public void toPay(String data) {
        LogUtil.v("WebViewActivity", data);
    }

    private class Test {

        @JavascriptInterface
        public void toPay(String data) {
            LogUtil.v("WebViewActivity", data);
        }
    }
}

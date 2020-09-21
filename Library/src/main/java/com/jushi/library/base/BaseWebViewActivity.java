package com.jushi.library.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.jushi.library.BuildConfig;
import com.jushi.library.R;
import com.jushi.library.customView.navigationbar.NavigationBar;
import com.jushi.library.utils.Logger;

/**
 * 网页加载界面基类
 */
public abstract class BaseWebViewActivity extends BaseFragmentActivity implements DownloadListener {
    private WebView mWebView;
    private ProgressBar progressBar;
    protected NavigationBar navigationBar;

    @Override
    protected int getLayoutResId() {
        setSystemBarStatus(true, true, true);
        return R.layout.activity_base_webview_layout;
    }

    @Override
    protected void initView() {
        navigationBar = findViewById(R.id.NavigationBar);
        initWebView();
        initProgressBar();
    }

    @Override
    protected void setListener() {

    }

    private void initProgressBar() {
        progressBar = findViewById(R.id.webView_progress);
        progressBar.setVisibility(needShowProgressBar() ? View.VISIBLE : View.GONE);
    }

    @SuppressLint({"ObsoleteSdkInt", "JavascriptInterface", "setJavaScriptEnabled"})
    private void initWebView() {
        mWebView = findViewById(R.id.webview);
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.addJavascriptInterface(onJavascriptInterfaceObject(), onJavascriptInterfaceName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mWebView.getSettings().setDisplayZoomControls(false);
        }
        mWebView.setDownloadListener(this);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.loadUrl(onWebUrl());
    }


    @Override
    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }


    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            view.scrollTo(0, 0);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            return true;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
//            mWebView.setVisibility(View.GONE);
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (progressBar == null) return;
            progressBar.setProgress(newProgress > 5 ? newProgress : 5);
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            onTitleChanged(title);
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            if (BuildConfig.DEBUG)
                Logger.v("WebViewActivity", consoleMessage.message());
            return super.onConsoleMessage(consoleMessage);
        }
    }


    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 是否显示进度条
     *
     * @return
     */
    protected abstract boolean needShowProgressBar();

    /**
     * 加载的网页链接
     *
     * @return
     */
    protected abstract String onWebUrl();

    /**
     * 网页标题发生改变
     *
     * @param title
     */
    protected abstract void onTitleChanged(String title);

    /**
     * js回调对象
     *
     * @return
     */
    protected abstract Object onJavascriptInterfaceObject();

    /**
     * js回调接口名
     *
     * @return
     */
    protected abstract String onJavascriptInterfaceName();
}

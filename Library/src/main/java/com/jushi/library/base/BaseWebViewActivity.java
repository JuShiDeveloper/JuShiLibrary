package com.jushi.library.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
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
    private final int FILE_CHOOSER_RESULT_CODE = 10000;
    private final int VIDEO_REQUEST = 104;
    private final int REQUEST_CODE_PERMISSIONS_CAMERA = 100;
    private WebView mWebView;
    private ProgressBar progressBar;
    protected NavigationBar navigationBar;

    private ValueCallback<Uri[]> uploadMessageAboveL;
    private ValueCallback<Uri> uploadMessage;
    private String accept;

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

            return false;
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

        // For Android  >= 3.0
        public void openFileChooser(ValueCallback valueCallback, String acceptType) {
            uploadMessage = valueCallback;
            openImageChooserActivity(acceptType);
        }

        //For Android  >= 4.1
        public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
            uploadMessage = valueCallback;
            openImageChooserActivity(acceptType);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
            uploadMessageAboveL = filePathCallback;
            String[] acceptTypes = fileChooserParams.getAcceptTypes();
            for (int i = 0; i < acceptTypes.length; i++) {
                openImageChooserActivity(acceptTypes[i]);
            }
            return true;
        }
    }


    private void openImageChooserActivity(String accept) {//调用自己的图库
        this.accept = accept;
        if (TextUtils.isEmpty(accept)) {
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            startActivityForResult(Intent.createChooser(i, "Image Chooser"), FILE_CHOOSER_RESULT_CODE);
            return;
        }
        if (accept.contains("image")) {
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            startActivityForResult(Intent.createChooser(i, "Image Chooser"), FILE_CHOOSER_RESULT_CODE);
        } else if (accept.contains("video") && checkCameraPermission()) {
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                // set the video file name
                //限制时长
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 5);
                //开启摄像机
                startActivityForResult(intent, VIDEO_REQUEST);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FILE_CHOOSER_RESULT_CODE:
                if (null == uploadMessageAboveL) return;
                onActivityResultAboveL(requestCode, resultCode, data);
                break;
            case VIDEO_REQUEST:
                if (null != uploadMessage) {
                    uploadMessage.onReceiveValue(data == null ? null : data.getData());
                    uploadMessage = null;
                }
                if (null != uploadMessageAboveL) {
                    uploadMessageAboveL.onReceiveValue(new Uri[]{data == null ? null : data.getData()});
                    uploadMessageAboveL = null;
                }
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        if (requestCode != FILE_CHOOSER_RESULT_CODE || uploadMessageAboveL == null)
            return;
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        uploadMessageAboveL.onReceiveValue(results);
        uploadMessageAboveL = null;
    }

    @Override
    protected void onCameraPermissionOpened() {
        super.onCameraPermissionOpened();
        if (TextUtils.isEmpty(accept)) return;
        openImageChooserActivity(accept);
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

package com.jushi.library.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jushi.library.R;
import com.jushi.library.customView.navigationbar.NavigationBar;
import com.jushi.library.utils.LogUtil;
import com.jushi.library.utils.ToastUtil;

import java.util.Objects;

/**
 * 网页加载界面Fragment基类
 */
public abstract class BaseWebViewFragment extends BaseFragment implements DownloadListener {
    private final int FILE_CHOOSER_RESULT_CODE = 10000;
    private final int VIDEO_REQUEST = 104;
    private final int REQUEST_CODE_PERMISSIONS_CAMERA = 100;
    private WebView mWebView;
    private ProgressBar progressBar;
    protected NavigationBar navigationBar;
    private TextView tvReload;

    private ValueCallback<Uri[]> uploadMessageAboveL;
    private ValueCallback<Uri> uploadMessage;
    private String accept;
    private String loadUrl;
    private int errorNum = 0;

    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_base_webview_layout,container,false);
    }

    @Override
    protected void initViewWidget() {
        tvReload = rootView.findViewById(R.id.tv_webview_reload);
        navigationBar = rootView.findViewById(R.id.NavigationBar);
        navigationBar.setVisibility(showNavigationBar() ? View.VISIBLE : View.GONE);
        initWebView();
        initProgressBar();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        tvReload.setOnClickListener(v->{
            if (!networkManager.isNetConect()){
                showToast("当前网络不可用，请检查网络设置！");
                return;
            }
            if (errorNum>=5){
                showToast("失败次数过多，请检查网址是否正确！");
                return;
            }
            tvReload.setVisibility(View.GONE);
            mWebView.loadUrl(loadUrl);
        });
    }

    protected boolean showNavigationBar() {
        return false;
    }

    private void initProgressBar() {
        progressBar = rootView.findViewById(R.id.webView_progress);
        progressBar.setVisibility(needShowProgressBar() ? View.VISIBLE : View.GONE);
    }

    @SuppressLint({"ObsoleteSdkInt", "JavascriptInterface", "setJavaScriptEnabled"})
    private void initWebView() {
        mWebView = rootView.findViewById(R.id.webview);
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setGeolocationEnabled(true);
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
            loadUrl = url;
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
            tvReload.setVisibility(View.VISIBLE);
            errorNum++;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//            super.onReceivedSslError(view, handler, error);
            handler.proceed();
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
            navigationBar.setTitleText(title);
            onTitleChanged(title);
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            LogUtil.v("H5日志： " + consoleMessage.message());
            if (consoleMessage.message().contains("MissingPDFException")){
                ToastUtil.showToast(getContext(),"文件加载失败，请确认文件链接有效！", Gravity.CENTER);
            }
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
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            uploadMessageAboveL = filePathCallback;
            String[] acceptTypes = fileChooserParams.getAcceptTypes();
            for (int i = 0; i < acceptTypes.length; i++) {
                openImageChooserActivity(acceptTypes[i]);
            }
            return true;
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
            super.onGeolocationPermissionsShowPrompt(origin, callback);
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
            if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                // set the video file name
                //限制时长
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 5);
                //开启摄像机
                startActivityForResult(intent, VIDEO_REQUEST);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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

    public boolean canBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return false;
        } else {
            return true;
        }
    }

    @SuppressLint("NewApi")
    protected void clearWebView() {
        mWebView.setWebChromeClient(null);
        mWebView.setWebViewClient(null);
        mWebView.getSettings().setJavaScriptEnabled(false);
        mWebView.clearCache(true);
        CookieSyncManager.createInstance(getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeSessionCookies(null);
            cookieManager.removeAllCookie();
            cookieManager.flush();
        } else {
            cookieManager.removeSessionCookies(null);
            cookieManager.removeAllCookie();
            CookieSyncManager.getInstance().sync();
        }
    }

    public WebView getWebView() {
        return mWebView;
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
    protected void onTitleChanged(String title) {
    }

    ;

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

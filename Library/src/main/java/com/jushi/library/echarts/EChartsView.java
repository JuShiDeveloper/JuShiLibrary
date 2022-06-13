package com.jushi.library.echarts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jushi.library.utils.LogUtil;
import com.jushi.library.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 基于echarts.js和echarts.html封装继承自WebView的ECharts图标视图
 * created by jushi on 2022/06/10
 */
public class EChartsView extends WebView {
    private boolean requestDisallowInterceptTouchEvent = false;//父视图是否可拦截触摸事件
    private  int emptyFontSize = 14;
    private  String emptyMsg = "暂无数据~";
    private String data = "";
    private int height = 280;//默认echarts视图高度位300px
    private OnEChartsClickListener listener;

    public EChartsView(Context context) {
        super(context);
        init();
    }

    public EChartsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EChartsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressLint({"ClickableViewAccessibility", "SetJavaScriptEnabled", "JavascriptInterface"})
    private void init() {
        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.getSettings().setDisplayZoomControls(false);
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        this.getSettings().setDefaultTextEncodingName("UTF-8");
        this.getSettings().setUseWideViewPort(true);
        this.getSettings().setSupportZoom(true);
        this.getSettings().setBuiltInZoomControls(true);
        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setDomStorageEnabled(true);
        this.getSettings().setGeolocationEnabled(true);
        this.addJavascriptInterface(this, "AndroidECharts");
        this.setWebViewClient(new EChartsWebViewClient());
        this.setWebChromeClient(new EChartsWebChromeClient());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        requestDisallowInterceptTouchEvent(requestDisallowInterceptTouchEvent);
        return super.onTouchEvent(event);
    }

    public void setData(String data) {
        loadData(data);
    }

    public void setData(String data,int height){
        this.height = height;
        try {
            JSONObject object = new JSONObject(data);
            loadData(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setEmpty() {
        loadData("");
    }

    public void setEmpty(String msg, int emptyFontSize) {
        emptyMsg = msg;
        this.emptyFontSize = emptyFontSize;
        loadData("");
    }

    private void loadData(String data) {
        this.data = data;
        this.loadUrl("file:///android_asset/echarts.html");
    }

    private class EChartsWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(final WebView view, String url) {
            LogUtil.v( "html加载完成 onPageFinished");
            view.post(() -> {
                if (TextUtils.isEmpty(data)) {
                    String emptyUrl = String.format("javascript:setEmpty(%s, %s)", "'" + emptyMsg + "'", emptyFontSize);
                    LogUtil.v( emptyUrl);
                    view.loadUrl(emptyUrl);
                } else
                    view.loadUrl(String.format("javascript:setHeight(%s)", height));
                    view.loadUrl(String.format("javascript:setData(%s)", data));
            });
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            LogUtil.v( error.toString());
        }
    }

    private class EChartsWebChromeClient extends WebChromeClient{
        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            LogUtil.v("H5日志： " + consoleMessage.message());
            return super.onConsoleMessage(consoleMessage);
        }
    }

    public boolean isRequestDisallowInterceptTouchEvent() {
        return requestDisallowInterceptTouchEvent;
    }

    public void setRequestDisallowInterceptTouchEvent(boolean requestDisallowInterceptTouchEvent) {
        this.requestDisallowInterceptTouchEvent = requestDisallowInterceptTouchEvent;
    }

    @JavascriptInterface
    public void onEChartsClick(String params){
        LogUtil.v("ECharts Params："+params);
        if (listener!=null){
            try {
                listener.onEChartsClick(new JSONObject(params));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void setOnEChartsClickListener(OnEChartsClickListener listener){
        this.listener = listener;
    }

    public interface OnEChartsClickListener{
        void onEChartsClick(JSONObject object);
    }
}

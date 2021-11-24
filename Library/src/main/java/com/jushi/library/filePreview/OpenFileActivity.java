package com.jushi.library.filePreview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.jushi.library.R;
import com.jushi.library.base.BaseFragmentActivity;
import com.jushi.library.http.DownloadFileRequester;
import com.jushi.library.manager.SdManager;
import com.jushi.library.utils.LogUtil;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;
import com.tencent.smtt.sdk.TbsReaderView;

/**
 * 文件在线预览（基于腾讯TBS实现，支持office文档以及PDF文档预览）
 * created by wyf on 2021-11-22
 */
public class OpenFileActivity extends BaseFragmentActivity implements TbsListener, QbSdk.PreInitCallback, TbsReaderView.ReaderCallback, View.OnClickListener, DownloadFileRequester.OnDownloadListener {
    public static final String EXTRA_FILE_PATH = "filePath";
    public static final String EXTRA_FILE_NAME = "fileName";
    private TbsReaderView tbsReaderView;
    private String mOfficeUrl = "";
    private String fileName = "";
    private String tbsReaderTemp;
    private FrameLayout mFrame;
    private TextView textView;
    private TextView tvTitle;
    private SdManager sdManager;

    @Override
    protected int getLayoutResId() {
        setSystemBarStatus(true,true,false);
        return R.layout.activity_open_file_layout;
    }

    @Override
    protected void initView() {
        mFrame = findViewById(R.id.FrameLayout);
        textView = findViewById(R.id.TextView);
        tvTitle = findViewById(R.id.tv_title);
    }

    @Override
    protected void initData() {
        sdManager = getManager(SdManager.class);
        mOfficeUrl = getIntent().getStringExtra(EXTRA_FILE_PATH);
        fileName = getIntent().getStringExtra(EXTRA_FILE_NAME);
        tbsReaderTemp = this.getExternalCacheDir() + "/TbsReaderTemp";
        initQbSdk();
    }

    @Override
    protected void setListener() {
        findViewById(R.id.back).setOnClickListener(this);
    }

    private void initQbSdk() {
        log("OfficeFrame initQbSdk start");
        QbSdk.setTbsListener(this);
        QbSdk.initX5Environment(this, this);
        loadOffice(mOfficeUrl);
    }

    private void loadOffice(String officePath) {
        this.mOfficeUrl = officePath;
        tvTitle.setText(fileName != null ? fileName : mOfficeUrl.substring(mOfficeUrl.lastIndexOf('/') + 1));
        textView.setVisibility(View.GONE);
        log("loadOffice method officePath = " + this.mOfficeUrl);
        if (officePath.startsWith("http://") || officePath.startsWith("https://")) {//文件网络连接，先下载文件
            DownloadFileRequester downloadFileRequester = new DownloadFileRequester();
            String fileSavePath = sdManager.getDownloadFilePath();
            downloadFileRequester.download(officePath, fileSavePath,
                    fileName != null ? fileName : mOfficeUrl.substring(mOfficeUrl.lastIndexOf('/') + 1), this);
            showIndeterminateProgressDialog();
        } else {
            startOpenFile();
        }
    }

    private void startOpenFile() {
        Bundle localBundle = new Bundle();
        localBundle.putString("filePath", this.mOfficeUrl);
        localBundle.putString("tempPath", this.tbsReaderTemp);
        resetTbsView();
        boolean result = this.tbsReaderView.preOpen(getFileType(this.mOfficeUrl), false);
        if (result) {
            this.tbsReaderView.openFile(localBundle);
        }
    }

    private void resetTbsView() {
        if (null != this.tbsReaderView) {
            this.tbsReaderView.onStop();
            this.mFrame.removeView(this.tbsReaderView);
            this.tbsReaderView = null;
        }
        this.tbsReaderView = new TbsReaderView(this, this);
        this.mFrame.addView(this.tbsReaderView);
    }

    private String getFileType(String filePath) {
        String str = "";
        if (TextUtils.isEmpty(filePath)) {
            log("filePath --> null");
        } else {
            log("filePath --> " + filePath);
            String fileName = this.fileName != null ? this.fileName : filePath.substring(filePath.lastIndexOf("/") + 1);
            log("fileName --> " + fileName);
            int i = fileName.lastIndexOf(".");
            if (i <= -1) {
                log("i <= -1  file name = " + fileName);
            } else {
                str = fileName.substring(i + 1);
                log("filePath.substring(i + 1) --> " + str);
            }
        }
        return str;
    }

    private void log(String msg) {
        LogUtil.v(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tbsReaderView != null) {
            this.tbsReaderView.onStop();
            this.mFrame.removeView(this.tbsReaderView);
            this.tbsReaderView = null;
        }
        QbSdk.clear(this);
    }


    @Override
    public void onDownloadFinish(int i) {
        log("OfficeFrame onDownloadFinish " + i);
        runOnUiThread(() -> textView.setText("插件加载中,请勿退出  " + i + "%"));
    }

    @Override
    public void onInstallFinish(int i) {
        log("OfficeFrame onInstallFinish " + i);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onDownloadProgress(final int i) {
        log("OfficeFrame onDownloadProgress " + i);
        runOnUiThread(() -> textView.setText("插件加载中,请勿退出  " + i + "%"));
    }

    @Override
    public void onCoreInitFinished() {
        log("OfficeFrame onCoreInitFinished ");
    }

    @Override
    public void onViewInitFinished(boolean isSuccess) {
        log("OfficeFrame onViewInitFinished isSuccess = " + isSuccess);
        if (isSuccess)
            loadOffice(mOfficeUrl);
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            finish();
        }
    }

    /********************** 文件下载回调方法 ************************************/
    @Override
    public void onProgress(int progress) {

    }

    @Override
    public void onSuccess(String filePath) {
        runOnUiThread(this::dismissIndeterminateProgressDialog);
        this.mOfficeUrl = filePath;
        runOnUiThread(this::startOpenFile);
    }

    @Override
    public void onError(int code, String msg) {
        runOnUiThread(() -> {
            dismissIndeterminateProgressDialog();
            textView.setVisibility(View.VISIBLE);
            textView.setText("文件加载失败，请确保文件链接有效！");
        });
        log("文件下载失败，请确保文件链接有效！");
    }
}

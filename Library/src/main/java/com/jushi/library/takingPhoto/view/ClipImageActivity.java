package com.jushi.library.takingPhoto.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


import com.jushi.library.R;
import com.jushi.library.base.BaseApplication;
import com.jushi.library.manager.SdManager;
import com.jushi.library.systemBarUtils.SystemBarUtil;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;


/**
 * 图片裁剪界面
 */
public class ClipImageActivity extends AppCompatActivity {

    private ClipViewLayout clipView;
    private TextView clipCancel, clipReset, clipOk;
    private Uri imageSrc = null;
    private SdManager sdManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemBarStatus(true, true, false);
        setContentView(R.layout.activity_clip_image);
        imageSrc = getIntent().getData();
        sdManager = BaseApplication.getInstance().getManager(SdManager.class);
        initialize();
    }

    private void initialize() {
        findWidget();
        setViewClickListener();
    }

    private void findWidget() {
        clipView = findViewById(R.id.clip_view);
        clipView.setImageSrc(imageSrc);
        clipCancel = findViewById(R.id.clip_cancel);
        clipReset = findViewById(R.id.clip_reset);
        clipOk = findViewById(R.id.clip_ok);
    }

    private void setViewClickListener() {
        clipCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        clipReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clipView.reset();
            }
        });
        clipOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toCropPhoto();
            }
        });
    }

    /**
     * 裁剪图片
     */
    private void toCropPhoto() {
        Bitmap bitmap = clipView.clip();
        if (bitmap == null) return;
        File rootFile = new File(sdManager.getImagePath());
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        Uri imageUri = Uri.fromFile(new File(rootFile, "crop" + System.currentTimeMillis() + ".jpg"));
        if (imageUri == null) return;
        OutputStream outputStream = null;
        try {
            outputStream = getContentResolver().openOutputStream(imageUri);
            if (outputStream != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Intent intent = new Intent();
        intent.setData(imageUri);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * 设置状态栏状态 （在重写的getLayoutResId()方法中调用才有效）
     *
     * @param isFitsSystemWindows    是否沉浸式状态栏
     * @param isTranslucentSystemBar 是否透明状态栏
     * @param statusBarTextDark      状态栏文字颜色是否为深色 true-深色模式 , false-亮色模式
     */
    public void setSystemBarStatus(boolean isFitsSystemWindows, boolean isTranslucentSystemBar, boolean statusBarTextDark) {
        SystemBarUtil.setRootViewFitsSystemWindows(this, isFitsSystemWindows);
        if (isTranslucentSystemBar) { //沉浸式状态栏，设置状态栏透明
            SystemBarUtil.setTranslucentStatus(this);
        }
        if (isFitsSystemWindows) { //沉浸式状态栏，设置状态栏文字颜色模式
            SystemBarUtil.setAndroidNativeLightStatusBar(this, statusBarTextDark);
        }
    }

}

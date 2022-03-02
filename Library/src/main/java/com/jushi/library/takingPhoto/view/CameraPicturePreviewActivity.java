package com.jushi.library.takingPhoto.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jushi.library.R;
import com.jushi.library.base.BaseFragmentActivity;
import com.jushi.library.customView.scaleImageView.ScaleImageView;


/**
 * 拍照确定页
 * Created by Lxx on 2016/10/12.
 */
public class CameraPicturePreviewActivity extends BaseFragmentActivity {
    private static final String EXTRA_PICTURE_PATH = "extra_picture_path";
    private static final String EXTRA_NEED_WATERMARK = "extra_need_watermark";

    private ScaleImageView imageView;

    private TextView reMakeText;

    private TextView usePictureText;

    public static void startActivityForResult(Activity activity, String picturePath, boolean needWatermark, int requestCode) {
        Intent intent = new Intent(activity, CameraPicturePreviewActivity.class);
        intent.putExtra(EXTRA_PICTURE_PATH, picturePath);
        intent.putExtra(EXTRA_NEED_WATERMARK, needWatermark);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected int getLayoutResId() {
        setSystemBarStatus(true,true,false);
        return R.layout.activity_camera_picture_preview;
    }

    @Override
    protected void initView() {
        reMakeText = findViewById(R.id.tv_remake);
        usePictureText = findViewById(R.id.tv_use_picture);
        imageView = findViewById(R.id.siv_image);
    }

    @Override
    protected void initData() {
        String path = getIntent().getStringExtra(EXTRA_PICTURE_PATH);
        if (TextUtils.isEmpty(path)) {
            finish();
            return;
        }
        showPicture(path);
    }

    private void showPicture(String path) {
        imageView.setImageBitmap(BitmapFactory.decodeFile(path));
    }

    @Override
    protected void setListener() {
        reMakeText.setOnClickListener(v -> finish());
        usePictureText.setOnClickListener(v -> {
            setResult(RESULT_OK);
            finish();
        });
    }

}

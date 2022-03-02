package com.jushi.library.takingPhoto;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jushi.library.R;


/**
 * 获取照片的弹窗（选择拍照或相册）
 * create time 2019/8/8
 *
 * @author JuShi
 */
public class TakingPhotoDialog extends Dialog implements OnClickListener {
    public static final int CAMERA = 0x010;
    public static final int ALBUM = 0x101;

    private RelativeLayout cameraItem;
    private RelativeLayout phonephotoItem;

    private TextView cameraTv;
    private TextView phonephotoTv;

    private TextView cancleBtn;
//    private TextView titleTv;

    private OnClickListener onClickListener;

    public TakingPhotoDialog(Context context) {
        super(context, R.style.avatardialogtheme);
        setParams();
    }

    public TakingPhotoDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        setParams();
    }

    public TakingPhotoDialog(Context context, int theme) {
        super(context, theme);
        setParams();
    }

    private void setParams() {
        Window dialogWindow = getWindow();
//        dialogWindow.setWindowAnimations(R.style.dialogAniamtionstyle);
        dialogWindow.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL);
        this.setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avatardialog);
        initView();
        setOnClick();
    }

    private void initView() {
        cameraItem = findViewById(R.id.avatardialog_camera);
        phonephotoItem = findViewById(R.id.avatardialog_phone_photo);
        cameraTv = findViewById(R.id.avatardialog_camera_tv);
        phonephotoTv = findViewById(R.id.avatardialog_phone_photo_tv);
        cancleBtn = findViewById(R.id.avatardialog_cancle);
//        titleTv = findViewById(R.id.avaterdialog_title);
    }

    private void setOnClick() {
        cameraItem.setOnClickListener(this);
        phonephotoItem.setOnClickListener(this);
        cancleBtn.setOnClickListener(this);
    }

    public TakingPhotoDialog setTip(String title) {
        if (title == null || TextUtils.isEmpty(title)) {
//            titleTv.setVisibility(View.INVISIBLE);
        } else {
//            titleTv.setText(title);
        }
        return this;
    }

    public void hideCameraItem() {
        this.cameraItem.setVisibility(View.GONE);
    }

    public void showPhonePhotoItem() {
        this.phonephotoItem.setVisibility(View.VISIBLE);
    }

    public TakingPhotoDialog setCamearTitle(String videoTitle) {
        this.cameraTv.setText(videoTitle);
        return this;
    }

    public TakingPhotoDialog setCamearTitle(int resId) {
        this.cameraTv.setText(resId);
        return this;
    }

    public TakingPhotoDialog setPhonePhotoTitle(String phonePhotoTitle) {
        this.phonephotoTv.setText(phonePhotoTitle);
        return this;
    }

    public TakingPhotoDialog setPhonePhotoTitle(int resId) {
        this.phonephotoTv.setText(resId);
        return this;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.avatardialog_camera) {
            if (onClickListener != null)
                onClickListener.onButtonClick(cameraItem, CAMERA);
            dismiss();
        } else if (id == R.id.avatardialog_cancle) {
            dismiss();
        } else if (id == R.id.avatardialog_phone_photo) {
            if (onClickListener != null)
                onClickListener.onButtonClick(phonephotoItem, ALBUM);
            dismiss();
        }
    }

    public interface OnClickListener {
        void onButtonClick(View v, int type);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}

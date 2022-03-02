package com.jushi.library.takingPhoto.view;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.jushi.library.R;
import com.jushi.library.base.BaseFragmentActivity;
import com.jushi.library.takingPhoto.util.BitmapUtils;
import com.jushi.library.viewinject.FindViewById;

import java.io.File;
import java.util.List;


/**
 * 自定义拍照  横屏 矩形框拍照
 * Created by wyf on 2016/10/11.
 */
@SuppressWarnings("deprecation")
public class CameraActivity extends BaseFragmentActivity implements Camera.PictureCallback, OnPictureListener {
    /**
     * 拍完照后从Intent中获取图片的路径
     */
    public static final String EXTRA_PATH = "extra_path";
    /**
     * 咨询
     */
    public static final int CAMERA_TYPE_INTERVIEW = 1;
    /**
     * 证件
     */
    public static final int CAMERA_TYPE_AUTH = 2;
    public static final int REQUEST_CODE_PERMISSIONS_CAMERA = 100;
    private static final int CAMERA_PREVIEW_REQUEST_CODE = 666;
    private static final String EXTRA_CAMERA_TYPE = "extra_camera_type";
    String picturePath;

    /**
     * 闪光灯
     */
    private ImageButton mFlashButton;

    /**
     * 切换摄像头
     */
    private ImageButton mSwitchCameraButton;

    /**
     * 拍照按钮
     */
    private ImageButton mTakePictureButton;

    /**
     * 取消按钮
     */
    private TextView mCancelText;

    /**
     * 图片预览父控件
     */
    private FrameLayout mPreviewContainer;

    private RelativeLayout rlLeftController;//左边控制闪光灯及切换摄像头布局
    private RelativeLayout rlRightController; //拍照按钮布局

    private CameraTopRectView rectView;

    private Camera mCamera;
    private CameraPreview mPreview;
    private int mCurrentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
    private int mCurrentOrientation; // 当前传感器角度
    private int mLastOrientation; //上次传感器角度
    private ObjectAnimator animator1;
    private ObjectAnimator animator2;
    private ObjectAnimator animator3;


    public static void startActivity(Activity activity, int cameraType, int requestCode) {
        Intent intent = new Intent(activity, CameraActivity.class);
        intent.putExtra(EXTRA_CAMERA_TYPE, cameraType);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected int getLayoutResId() {
        setSystemBarStatus(true,true,true);
        return R.layout.activity_camera;
    }

    @Override
    protected void initView() {
        mFlashButton = findViewById(R.id.ib_flash);
        mSwitchCameraButton = findViewById(R.id.ib_switch_camera);
        mTakePictureButton = findViewById(R.id.ib_take_picture);
        mCancelText = findViewById(R.id.tv_cancel);
        mPreviewContainer = findViewById(R.id.camera_preview_container);
        rlLeftController = findViewById(R.id.rl_top);
        rlRightController = findViewById(R.id.rl_bottom);
        rectView = findViewById(R.id.topRectView);
        checkHasCameraPermission();
    }

    private void initPictureView(){
        mPreview = new CameraPreview(this, mCamera);
        mPreviewContainer.addView(mPreview);
        startPreview();
    }

    @Override
    protected void initData() {
        checkHasCameraPermission();
    }

    private void checkHasCameraPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            goOn();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示");
                builder.setMessage("需要获取您的相机权限，用于获取头像、上传照片等功能。如果您拒绝，将无法使用获取头像等功能！");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(CameraActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_PERMISSIONS_CAMERA);
                    }
                });
                builder.show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_PERMISSIONS_CAMERA);
            }
        }
    }

    private void goOn() {
        if (!CameraHelper.checkCameraHardware(this)) {
            showToast("相机不可用");
            finish();
            return;
        }
        if (initCamera()) {
            initPictureView();
            handleIntent();
//            setListener();
        } else {
            showToast("相机不可用");
            finish();
        }
    }

    private void handleIntent() {
        int type = getIntent().getIntExtra(EXTRA_CAMERA_TYPE, CAMERA_TYPE_INTERVIEW);
        if (type == CAMERA_TYPE_INTERVIEW) {

        }
    }
    //初始化Camera实例(false：初始化失败)

    private boolean initCamera() {
        if (mCamera != null) {
            return true;
        }

        if (mCurrentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
            mCamera = CameraHelper.getBackCameraInstance();
        } else if (mCurrentCameraId == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            mCamera = CameraHelper.getFrontCameraInstance();
        } else {
            mCamera = CameraHelper.getBackCameraInstance();
        }
        return mCamera != null;
    }

    @Override
    protected void setListener() {
        mFlashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraHelper.switchLight(mCamera);
            }
        });

        if (CameraHelper.checkHasFrontCamera()) {
            mSwitchCameraButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchCamera();
                }
            });
        } else {
            showToast("前置摄像头不可用");
        }

        mCancelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releaseCamera();
                finish();
            }
        });

        mTakePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mCamera.takePicture(null, null, CameraActivity.this); //拍照
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
                mTakePictureButton.setEnabled(false);
            }
        });
    }

    //开始预览
    private void startPreview() {
        try {
            setCamera();
            mCamera.setPreviewDisplay(mPreview.getHolder());
            CameraHelper.setCameraDisplayOrientation(this, mCurrentCameraId, mCamera);
            mPreview.setCamera(mCamera);
            mCamera.startPreview();
        } catch (Exception e) {
            showToast("获取Camera实例失败！");
            finish();
        }
    }

    private void setCamera() throws Exception {
        Camera.Parameters parameters = mCamera.getParameters();
        List<String> focusModes = parameters.getSupportedFocusModes();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            }
        } else if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }
        parameters.setPictureFormat(ImageFormat.JPEG);
        parameters.setJpegQuality(100);

        int sizeWidth = getResources().getDisplayMetrics().widthPixels;
        int sizeHeight = getResources().getDisplayMetrics().heightPixels;
        Camera.Size preSize = CameraHelper.getPreSize(sizeWidth, sizeHeight, mCamera);
//        Camera.Size picSize = CameraHelper.getBastSize(preSize.width, preSize.height, mCamera.getParameters().getSupportedPictureSizes());
        Camera.Size picSize = CameraHelper.getPictureSize(preSize, mCamera);
        parameters.setPreviewSize(preSize.width, preSize.height);
        parameters.setPictureSize(picSize.width, picSize.height);
//        parameters.setPictureSize(2560, 1920);
        mCamera.setParameters(parameters);
        startOrientationChangeListener();
    }

    //切换前置后置摄像头
    private void switchCamera() {
        releaseCamera();
        if (mCurrentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
            mCamera = CameraHelper.getFrontCameraInstance();
            mCurrentCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
        } else {
            mCamera = CameraHelper.getBackCameraInstance();
            mCurrentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
        }
        startPreview();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            if (initCamera()) {
                startPreview();
            } else {
                showToast("相机不可用");
                finish();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    @Override
    protected void onDestroy() {
        releaseCamera();
        super.onDestroy();
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mPreview.setCamera(null);
            mCamera.release();
            mCamera = null;
        }
    }


    @Override
    public void onPictureTaken(final byte[] data, Camera camera) {
        SavePictureUtil savePictureUtil = new SavePictureUtil(this, this);
        savePictureUtil.execute(data, mCurrentOrientation, mCurrentCameraId);
    }


    @Override
    public void onPicturePath(Bitmap mBitmap) {
        File rootFile = new File(getExternalCacheDir().getPath() + "/image");
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        File file = new File(rootFile, System.currentTimeMillis() + ".jpg");
        int sizeWidth = getResources().getDisplayMetrics().widthPixels;
        int sizeHeight = getResources().getDisplayMetrics().heightPixels;
        Bitmap bitmap1 = Bitmap.createScaledBitmap(mBitmap, sizeWidth, sizeHeight, true);
        int x = rectView.getRectLeft();
        int y = rectView.getRectTop();
        int width = rectView.getRectRight() - x;
        int height = rectView.getRectBottom() - y;
        Bitmap bitmap2 = Bitmap.createBitmap(bitmap1, x, y, width, height);
        picturePath = file.getPath();
        BitmapUtils.saveBitmap(bitmap2, file);
        rectView.postDelayed(()->{
            CameraPicturePreviewActivity.startActivityForResult(this, picturePath, true, CAMERA_PREVIEW_REQUEST_CODE);
        },1200);
        mTakePictureButton.setEnabled(true);
        mBitmap.recycle();
        bitmap1.recycle();
        bitmap2.recycle();
    }

    //开启传感器方向监听
    private void startOrientationChangeListener() {
        final OrientationEventListener orientationEventListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int rotation) {
                if (((rotation >= 0) && (rotation <= 45)) || (rotation > 315)) {
                    rotation = 0;
                } else if ((rotation > 45) && (rotation <= 135)) {
                    rotation = 90;
                } else if ((rotation > 135) && (rotation <= 225)) {
                    rotation = 180;
                } else if ((rotation > 225) && (rotation <= 315)) {
                    rotation = 270;
                } else {
                    rotation = 0;
                }

                if (rotation != mCurrentOrientation) {
                    mLastOrientation = mCurrentOrientation;
                    mCurrentOrientation = rotation;
                    rotateFlashAndSwitchButton2();
                }
            }
        };
        orientationEventListener.enable();
    }

    //旋转闪光灯和切换摄像头图标方向
    private void rotateFlashAndSwitchButton() {
        int offset;
        if (mCurrentOrientation - mLastOrientation != -270 && (mCurrentOrientation - mLastOrientation < 0 || mCurrentOrientation - mLastOrientation == 270)) {
            //逆时针旋转屏幕
            offset = 90;
        } else {
            //顺时针旋转屏幕
            offset = -90;
        }

        if (animator1 != null && animator2 != null) {
            animator1.end();
            animator2.end();
            animator3.end();
        }

        animator1 = ObjectAnimator.ofFloat(mSwitchCameraButton, "rotation", mSwitchCameraButton.getRotation(), mSwitchCameraButton.getRotation() + offset)
                .setDuration(600);
        animator2 = ObjectAnimator.ofFloat(mFlashButton, "rotation", mFlashButton.getRotation(), mFlashButton.getRotation() + offset)
                .setDuration(600);
        animator3 = ObjectAnimator.ofFloat(mCancelText, "rotation", mCancelText.getRotation(), mCancelText.getRotation() + offset)
                .setDuration(600);
        animator1.start();
        animator2.start();
        animator3.start();
    }

    private void rotateFlashAndSwitchButton2() {
        if (animator1 != null && animator2 != null) {
            animator1.end();
            animator2.end();
            animator3.end();
        }
        int toDegree = 0;
        if (mCurrentOrientation == 0) {//正面朝上
            toDegree = 270;
        } else if (mCurrentOrientation == 270) {//向左
            toDegree = 360;
        } else if (mCurrentOrientation == 90) { //向右
            toDegree = 180;
        } else if (mCurrentOrientation == 180) { //上下颠倒
            toDegree = 90;
        }
        animator1 = ObjectAnimator.ofFloat(mSwitchCameraButton, "rotation", mSwitchCameraButton.getRotation(), toDegree)
                .setDuration(600);
        animator2 = ObjectAnimator.ofFloat(mFlashButton, "rotation", mFlashButton.getRotation(), toDegree)
                .setDuration(600);
        animator3 = ObjectAnimator.ofFloat(mCancelText, "rotation", mCancelText.getRotation(), toDegree)
                .setDuration(600);
        animator1.start();
        animator2.start();
        animator3.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PREVIEW_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent();
                intent.putExtra(EXTRA_PATH, picturePath);
                setResult(RESULT_OK, intent);
                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSIONS_CAMERA) {
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                goOn();
            } else {
                showToast("相机权限已被禁止");
                finish();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

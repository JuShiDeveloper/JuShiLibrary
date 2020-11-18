package com.jushi.library.base;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.jushi.library.utils.ToastUtil;

/**
 * 权限申请基类activity
 */
abstract class BasePermissionActivity extends AppCompatActivity {
    private final int REQUEST_CODE_PERMISSIONS_CAMERA = 0x01;
    private final int REQUEST_CODE_PERMISSIONS_EXTERNAL_STORAGE = 0x02;
    private final int REQUEST_CODE_PERMISSIONS_LOCATION = 0x03;
    private final int REQUEST_CODE_PERMISSIONS_RECORD_AUDIO = 0x04;

    /**
     * 检查相机权限
     *
     * @return
     */
    protected boolean checkCameraPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_PERMISSIONS_CAMERA);
            return false;
        }
        return true;
    }

    /**
     * 检查SD卡读写权限
     *
     * @return
     */
    protected boolean checkExternalStoragePermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSIONS_EXTERNAL_STORAGE);
            return false;
        }
        return true;
    }

    /**
     * 检查位置权限
     *
     * @return
     */
    protected boolean checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_CODE_PERMISSIONS_LOCATION);
            return false;
        }
        return true;
    }

    /**
     * 检查录音权限
     *
     * @return
     */
    protected boolean checkRecordAudioPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    REQUEST_CODE_PERMISSIONS_RECORD_AUDIO);
            return false;
        }
        return true;
    }

    private void showToast(String msg) {
        ToastUtil.showToast(this, msg);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSIONS_CAMERA:
                if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onCameraPermissionOpened();
                } else {
                    showToast("相机权限已被禁止");
                }
                break;
            case REQUEST_CODE_PERMISSIONS_EXTERNAL_STORAGE:
                if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onExternalStoragePermissionOpened();
                } else {
                    showToast("存储权限已被禁止");
                }
                break;
            case REQUEST_CODE_PERMISSIONS_LOCATION:
                if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onLocationPermissionOpened();
                } else {
                    showToast("定位权限已被禁止");
                }
                break;
            case REQUEST_CODE_PERMISSIONS_RECORD_AUDIO:
                if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onRecordAudioPermissionOpened();
                } else {
                    showToast("录音权限已被禁止");
                }
                break;
        }
    }

    /**
     * 相机权限打开
     */
    protected abstract void onCameraPermissionOpened();

    /**
     * 存储权限打开
     */
    protected abstract void onExternalStoragePermissionOpened();

    /**
     * 定位权限打开
     */
    protected abstract void onLocationPermissionOpened();

    /**
     * 录音权限打开
     */
    protected abstract void onRecordAudioPermissionOpened();
}

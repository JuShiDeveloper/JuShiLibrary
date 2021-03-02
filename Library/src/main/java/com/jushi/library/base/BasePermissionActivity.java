package com.jushi.library.base;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.jushi.library.utils.PermissionUtil;
import com.jushi.library.utils.ToastUtil;

import java.util.List;

/**
 * 权限申请基类activity
 */
abstract class BasePermissionActivity extends AppCompatActivity {
    private final int REQUEST_CODE_PERMISSIONS_CAMERA = 0x01;
    private final int REQUEST_CODE_PERMISSIONS_EXTERNAL_STORAGE = 0x02;
    private final int REQUEST_CODE_PERMISSIONS_LOCATION = 0x03;
    private final int REQUEST_CODE_PERMISSIONS_RECORD_AUDIO = 0x04;
    private final int SYSTEM_ALERT_WINDOW_CODE = 0x05;

    /**
     * 检查相机权限
     *
     * @return
     */
    protected boolean checkCameraPermission() {
        return PermissionUtil.request(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_PERMISSIONS_CAMERA);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_PERMISSIONS_CAMERA);
//            return false;
//        }
//        return true;
    }

    /**
     * 检查SD卡读写权限
     *
     * @return
     */
    protected boolean checkExternalStoragePermission() {
        return PermissionUtil.request(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_CODE_PERMISSIONS_EXTERNAL_STORAGE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
//                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    REQUEST_CODE_PERMISSIONS_EXTERNAL_STORAGE);
//            return false;
//        }
//        return true;
    }

    /**
     * 检查位置权限
     *
     * @return
     */
    protected boolean checkLocationPermission() {
        return PermissionUtil.request(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_CODE_PERMISSIONS_LOCATION);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
//                    REQUEST_CODE_PERMISSIONS_LOCATION);
//            return false;
//        }
//        return true;
    }

    /**
     * 检查录音权限
     *
     * @return
     */
    protected boolean checkRecordAudioPermission() {
        return PermissionUtil.request(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_CODE_PERMISSIONS_RECORD_AUDIO);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
//                    REQUEST_CODE_PERMISSIONS_RECORD_AUDIO);
//            return false;
//        }
//        return true;
    }

    /**
     * 检查悬浮窗权限
     *
     * @return
     */
    protected boolean checkAlertWindowPermission() {
        if (canDrawOverlays()) return true;
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, SYSTEM_ALERT_WINDOW_CODE);
        return false;
    }

    /**
     * 是否有悬浮窗权限
     *
     * @return
     */
    private boolean canDrawOverlays() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(this);
    }

    private void showToast(String msg) {
        ToastUtil.showToast(this, msg);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        onFragmentPermissionResult(requestCode, permissions, grantResults);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (canDrawOverlays() && requestCode == SYSTEM_ALERT_WINDOW_CODE) {
            onAlertWindowPermissionOpened();
        } else {
            showToast("悬浮窗权限已被禁止");
        }
    }

    /**
     * 权限事件传递到fragment
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    private void onFragmentPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (Fragment fragment : fragmentManager.getFragments()) {
            if (fragment == null) continue;
            handleChildPermissionResult(fragment, requestCode, permissions, grantResults);
        }
    }

    private void handleChildPermissionResult(Fragment fragment, int requestCode, String[] permissions, int[] grantResults) {
        fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);//onRequestPermissionsResult
        List<Fragment> childFragment = fragment.getChildFragmentManager().getFragments(); //找到第二层Fragment
        for (Fragment f : childFragment) {
            if (f == null) continue;
            handleChildPermissionResult(f, requestCode, permissions, grantResults);
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

    /**
     * 悬浮窗权限打开
     */
    protected abstract void onAlertWindowPermissionOpened();
}

package com.jushi.library.takingPhoto.view;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.support.annotation.Nullable;
import android.view.Surface;

import java.util.Collections;
import java.util.List;

/**
 * 相机帮助类
 * Created by Lxx on 2016/10/11.
 */
@SuppressWarnings("deprecation")
 class CameraHelper {

    /**
     * 获取后置Camera实例
     *
     * @eturn 返回null，代表相机不可用
     */
    @Nullable
    public static Camera getBackCameraInstance() {
        Camera camera = null;
        try {
            camera = Camera.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return camera;
    }

    /**
     * 获取前置Camera实例
     *
     * @eturn 返回null，代表相机不可用
     */
    @Nullable
    public static Camera getFrontCameraInstance() {
        if (!checkHasFrontCamera()) {
            return null;
        }

        Camera camera = null;
        try {
            camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return camera;
    }


    /**
     * 检查手机是否存在相机资源
     */
    public static boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    /**
     * 是否有前置摄像头
     */
    public static boolean checkHasFrontCamera() {
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                return true;
            }
        }
        return false;
    }

    /**
     * 保证预览方向正确
     *
     * @param activity
     * @param cameraId
     * @param camera
     */
    public static void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;
        } else {
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    //检测闪光灯是否存在
    private static boolean checkLightAvailable(Camera mCamera) {
        if (mCamera == null) {
            return false;
        }
        Camera.Parameters parameters = mCamera.getParameters();
        if (parameters == null) {
            return false;
        }
        List<String> flashModes = parameters.getSupportedFlashModes();
        return flashModes != null;
    }

    /**
     * 打开闪关灯
     *
     * @param mCamera
     */
    public static void turnLightOn(Camera mCamera) {
        if (!checkLightAvailable(mCamera)) {
            return;
        }
        Camera.Parameters parameters = mCamera.getParameters();
        List<String> flashModes = parameters.getSupportedFlashModes();
        String flashMode = parameters.getFlashMode();
        if (!Camera.Parameters.FLASH_MODE_ON.equals(flashMode)) {
            if (flashModes.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(parameters);
            } else {
                //无法打开
            }
        }
    }

    /**
     * 关闭闪光灯
     *
     * @param mCamera
     */
    public static void turnLightOff(Camera mCamera) {
        if (!checkLightAvailable(mCamera)) {
            return;
        }
        Camera.Parameters parameters = mCamera.getParameters();
        List<String> flashModes = parameters.getSupportedFlashModes();
        String flashMode = parameters.getFlashMode();
        if (!Camera.Parameters.FLASH_MODE_OFF.equals(flashMode)) {
            if (flashModes.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(parameters);
            } else {
                //无法打开
            }
        }
    }

    /**
     * 切换闪光灯
     */
    public static void switchLight(Camera mCamera) {
        if (!checkLightAvailable(mCamera)) {
            return;
        }
        Camera.Parameters parameters = mCamera.getParameters();
        String flashMode = parameters.getFlashMode();
        if (Camera.Parameters.FLASH_MODE_OFF.equals(flashMode)) {
            turnLightOn(mCamera);
        } else {
            turnLightOff(mCamera);
        }
    }

    //旋转拍照返回的图片
    public static Bitmap rotatePicture(int cameraId, Bitmap bitmap, int offset) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        Matrix matrix = new Matrix();

        if (cameraId == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            matrix.postRotate(info.orientation);
            matrix.postScale(-1, 1); //加入翻转 把相机拍照返回照片转正
            matrix.postRotate(offset);
        } else {
            matrix.postRotate(info.orientation + offset);
        }
        // 创建新的图片
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * 获取最适合的尺寸
     */
    public static Camera.Size getBastSize(int targetWidth, int targetHeight, List<Camera.Size> sizeList) {
        Camera.Size bastSize = null;
        //目标大小的宽高比
        int targetRatio = (targetHeight / targetWidth);
        int mindiff = targetRatio;
        for (Camera.Size size : sizeList) {
            //系统支持的尺寸
            int supportedRatio = (size.width / size.height);
            if (size.height == targetWidth) {
                bastSize = size;
                break;
            }
            if (Math.abs(supportedRatio - targetRatio) < mindiff) {
                mindiff = Math.abs(supportedRatio - targetRatio);
                bastSize = size;
            }
        }
        return bastSize;
    }

    public static Camera.Size getPreSize(int surfaceWidth, int surfaceHeight, Camera camera) {
        List<Camera.Size> preSizeList = camera.getParameters().getSupportedPreviewSizes();
        //升序
        Collections.sort(preSizeList, (size1, size2) -> {
            if (size1.width == size2.width) {
                return 0;
            } else if (size1.width > size2.width) {
                return 1;
            } else {
                return -1;
            }
        });

        //先查找preview中是否存在与surfaceView相同宽高的尺寸
        for (Camera.Size size : preSizeList) {
            if ((size.width == surfaceWidth) && (size.height == surfaceHeight)) {
                return size;
            }
        }

        //若支持的尺寸的最大值比surfaceWidth小，直接返回支持的最大尺寸
        if (surfaceWidth > preSizeList.get(preSizeList.size() - 1).width) {
            return preSizeList.get(preSizeList.size() - 1);
        }

        // 得到与传入的宽高比最接近的Camera.Size
        float reqRatio = ((float) surfaceWidth) / surfaceHeight;
        float curRatio, deltaRatio;
        float deltaRatioMin = Float.MAX_VALUE;
        Camera.Size retSize = preSizeList.get(preSizeList.size() - 1);
        for (Camera.Size size : preSizeList) {
            if (size.width < 0.7 * surfaceWidth) {
                continue;
            }
            curRatio = ((float) size.width) / size.height;
            deltaRatio = Math.abs(reqRatio - curRatio);

            if (deltaRatio < deltaRatioMin) {
                deltaRatioMin = deltaRatio;
                retSize = size;
            }
        }
        return retSize;
    }

    public static Camera.Size getPictureSize(Camera.Size previewSize, Camera camera) {
        List<Camera.Size> pictureList = camera.getParameters().getSupportedPictureSizes();
        for (Camera.Size size : pictureList) {
            if ((size.width == previewSize.width) && (size.height == previewSize.height)) {
                return size;
            }
        }

        float reqRatio = previewSize.width / previewSize.height;
        float curRatio, deltaRatio;
        float deltaRatioMin = Float.MAX_VALUE;
        Camera.Size retSize = pictureList.get(pictureList.size() / 2);
        for (Camera.Size size : pictureList) {
            curRatio = ((float) size.width) / size.height;
            deltaRatio = Math.abs(reqRatio - curRatio);
            if (deltaRatio < deltaRatioMin) {
                if (size.width > 1000) {
                    deltaRatioMin = deltaRatio;
                    retSize = size;
                }
            }
        }
        return retSize;
    }

}

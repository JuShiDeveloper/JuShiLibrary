package com.jushi.library.takingPhoto.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.widget.Toast;

 class SavePictureUtil extends AsyncTask<Object, Object, Bitmap> {

    private OnPictureListener pictureListener;
    private Context context;
    private byte[] data;
    private int currentOrientation;
    private int currentCameraId;

    public SavePictureUtil(Context context, OnPictureListener pictureListener) {
        this.context = context;
        this.pictureListener = pictureListener;
    }

    public void execute(byte[] data, int currentOrientation, int currentCameraId) {
        this.data = data;
        this.currentOrientation = currentOrientation;
        this.currentCameraId = currentCameraId;
        execute();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(context, "正在处理中", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Bitmap doInBackground(Object... objects) {
        Bitmap bitmap = CameraHelper.rotatePicture(currentCameraId,
                BitmapFactory.decodeByteArray(data, 0, data.length).copy(Bitmap.Config.RGB_565, true)
                , currentOrientation == 270 ? -90 : currentOrientation);
        Bitmap newBitmap = null;
        if (bitmap.getWidth() < bitmap.getHeight()) {//竖屏拍照旋转-90度
            Matrix matrix = new Matrix();
            matrix.postRotate(-90);
            newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
        return newBitmap == null ? bitmap : newBitmap;
    }

    @Override
    protected void onPostExecute(Bitmap path) {
        super.onPostExecute(path);
        if (pictureListener == null) return;
        pictureListener.onPicturePath(path);
    }

}

package com.jushi.library.takingPhoto;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.FileProvider;


import com.jushi.library.BuildConfig;
import com.jushi.library.base.BaseApplication;
import com.jushi.library.manager.SdManager;
import com.jushi.library.takingPhoto.view.ClipImageActivity;

import java.io.File;


public class PictureHelper {

    /**
     * 打开相机拍照
     *
     * @param activity
     * @param requestCode
     * @return
     */
    public static File gotoCamera(Activity activity,String pkgName, int requestCode) {
        SdManager sdManager = BaseApplication.getInstance().getManager(SdManager.class);
        File rootFile = new File(sdManager.getImagePath());
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        File imageFile = new File(rootFile, System.currentTimeMillis() + "uImage.jpg");
        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri contentUri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            contentUri = FileProvider.getUriForFile(activity,  pkgName+".provider", imageFile);
        } else {
            contentUri = Uri.fromFile(imageFile);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        activity.startActivityForResult(intent, requestCode);
        return imageFile;
    }

    /**
     * 打开相册选择图片
     */
    public static void gotoPhotoAlbum(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(Intent.createChooser(intent, "请选择图片"), requestCode);
    }

    /**
     * 打开图片裁剪界面
     *
     * @param activity
     * @param uri
     * @param requestCode
     */
    public static void gotoClipActivity(Activity activity, Uri uri, int requestCode) {
        if (uri == null) return;
        Intent intent = new Intent(activity, ClipImageActivity.class);
        intent.setData(uri);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 根据图片的Uri获取图片的绝对路径。@uri 图片的uri
     * @return 如果Uri对应的图片存在,那么返回该图片的绝对路径,否则返回null
     */
    public static String getRealPathFromUri(Context context, Uri uri) {
        if(context == null || uri == null) {
            return null;
        }
        if("file".equalsIgnoreCase(uri.getScheme())) {
            return getRealPathFromUri_Byfile(context,uri);
        } else if("content".equalsIgnoreCase(uri.getScheme())) {
            return getRealPathFromUri_Api11To18(context,uri);
        }
//        int sdkVersion = Build.VERSION.SDK_INT;
//        if (sdkVersion < 11) {
//            // SDK < Api11
//            return getRealPathFromUri_BelowApi11(context, uri);
//        }
        if (Build.VERSION.SDK_INT < 19) {
            return getRealPathFromUri_Api11To18(context, uri);
//            return getRealPathFromUri_ByXiaomi(context, uri);
        }
//        // SDK > 19
        return getRealPathFromUri_AboveApi19(context, uri);//没用到
    }

    //针对图片URI格式为Uri:: file:///storage/emulated/0/DCIM/Camera/IMG_20170613_132837.jpg
    private static String getRealPathFromUri_Byfile(Context context,Uri uri){
        String uri2Str = uri.toString();
        String filePath = uri2Str.substring(uri2Str.indexOf(":") + 3);
        return filePath;
    }

    /**
     * 适配api19以上,根据uri获取图片的绝对路径
     */
    @SuppressLint("NewApi")
    private static String getRealPathFromUri_AboveApi19(Context context, Uri uri) {
        String filePath = null;
        String wholeID = null;

        wholeID = DocumentsContract.getDocumentId(uri);

        // 使用':'分割
        String id = wholeID.split(":")[1];

        String[] projection = { MediaStore.Images.Media.DATA };
        String selection = MediaStore.Images.Media._ID + "=?";
        String[] selectionArgs = { id };

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                selection, selectionArgs, null);
        int columnIndex = cursor.getColumnIndex(projection[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

    /**
     * //适配api11-api18,根据uri获取图片的绝对路径。
     * 针对图片URI格式为Uri:: content://media/external/images/media/1028
     */
    private static String getRealPathFromUri_Api11To18(Context context, Uri uri) {
        String filePath = null;
        String[] projection = { MediaStore.Images.Media.DATA };

        CursorLoader loader = new CursorLoader(context, uri, projection, null,
                null, null);
        Cursor cursor = loader.loadInBackground();

        if (cursor != null) {
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(projection[0]));
            cursor.close();
        }
        return filePath;
    }

    /**
     * 适配api11以下(不包括api11),根据uri获取图片的绝对路径
     */
    private static String getRealPathFromUri_BelowApi11(Context context, Uri uri) {
        String filePath = null;
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri, projection,
                null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(projection[0]));
            cursor.close();
        }
        return filePath;
    }
}

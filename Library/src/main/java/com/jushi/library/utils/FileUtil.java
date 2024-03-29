package com.jushi.library.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;


import com.jushi.library.base.BaseApplication;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import static android.support.v4.content.FileProvider.getUriForFile;

/**
 * 文件工具类
 */
public class FileUtil {
    /**
     * 系统相册的路径
     *
     * @return
     */
    public static String getSystemPhotoPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera";
    }

    /**
     * 根据Uri返回文件绝对路径
     * 兼容了file:///开头的 和 content://开头的情况
     */
    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equalsIgnoreCase(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 检查文件是否存在
     */
    public static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }

    /**
     * 复制文件
     *
     * @param oldPath 文件当前存放的地址
     * @param newPath 文件复制的目标地址
     */
    public static boolean copyFile(String oldPath, String newPath) {
        boolean isSuccess = false;
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                int size = (int) oldfile.length();
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[size];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                isSuccess = true;
                notifySystemScanImage(newPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * 通知系统检索刚刚保存的图片
     */
    public static void notifySystemScanImage(String imagePath) {
//        ContentValues values = new ContentValues();
//        values.put(MediaStore.Images.Media.DATA, imagePath);
//        values.put(MediaStore.Images.Media.CONTENT_TYPE, "image/jpeg");
//        Uri uri = BaseApplication.context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//        BaseApplication.context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        MediaScannerConnection.scanFile(BaseApplication.getInstance(),
                new String[]{new File(imagePath).getAbsolutePath()},
                new String[]{"image/jpeg"}, new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        Log.v("yufei", "onScanCompleted：path = " + path);
                        Log.v("yufei", "onScanCompleted：uri = " + uri);
                    }
                });
    }

    /**
     * 保存图片文件
     *
     * @param fileFolderStr 文件地址
     * @param isDir
     * @param croppedImage
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String saveToFile(String fileFolderStr, boolean isDir, Bitmap croppedImage) throws FileNotFoundException, IOException {
        File jpgFile;
        if (isDir) {
            File fileFolder = new File(fileFolderStr);
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss"); // 格式化时间
            String filename = format.format(date) + ".jpg";
            if (!fileFolder.exists()) {
                fileFolder.mkdirs();
            }
            jpgFile = new File(fileFolder, filename);
        } else {
            jpgFile = new File(fileFolderStr);
            if (!jpgFile.getParentFile().exists()) { // 如果目录不存在，则创建一个名为"finger"的目录
                jpgFile.getParentFile().mkdirs();
            }
        }
        FileOutputStream outputStream = new FileOutputStream(jpgFile); // 文件输出流
        croppedImage.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        outputStream.close();
        return jpgFile.getPath();
    }

    /**
     * 获取path路径下的图片
     *
     * @param path 存有图片的文件夹路径
     * @return 存有该文件夹路径下图片的集合
     */
    public static ArrayList<String> findPicsInDir(String path) {
        ArrayList<String> photos = new ArrayList<String>();
        File dir = new File(path);
        if (dir.exists() && dir.isDirectory()) {
            for (File file : dir.listFiles(new FileFilter() {

                @Override
                public boolean accept(File pathname) {
                    String filePath = pathname.getAbsolutePath();
                    return (filePath.endsWith(".png") || filePath.endsWith(".jpg") || filePath
                            .endsWith(".jepg"));
                }
            })) {
                photos.add(file.getAbsolutePath());
            }
        }
//        Collections.sort(photos); //升序排序
        //降序排序
        Collections.sort(photos, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });
        return photos;
    }

    /**
     * 调用第三方软件打开office文件
     * @param activity
     * @param pkgName
     * @param filePath
     */
    public static void openOfficeFile(Activity activity,String pkgName, String filePath) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            File file = new File(filePath);
            String type = getMIMEType(file);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri uri = getUriForFile(activity, pkgName + ".fileprovider", file);
                // 设置文件类型
                intent.setDataAndType(uri, type);
            } else {
                Uri uri = Uri.fromFile(file);
                intent.setDataAndType(uri, type);
            }
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            activity.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            activity.runOnUiThread(() -> ToastUtil.showToast(activity, "附件不能打开，请下载相关软件！", Gravity.CENTER));
        }
    }

    /**
     * 判断文件类型
     */
    private static String getMIMEType(File f) {
        String type = "";
        String fName = f.getName();
        /* 取得扩展名 */
        String end = fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase();
        /* 依扩展名的类型决定MimeType */
        if (end.equals("pdf")) {
            type = "application/pdf";
        } else if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") ||
                end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
            type = "audio/*";
        } else if (end.equals("3gp") || end.equals("mp4")) {
            type = "video/*";
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png") ||
                end.equals("jpeg") || end.equals("bmp")) {
            type = "image/*";
        } else if (end.equals("apk")) {
            type = "application/vnd.android.package-archive";
        } else if (end.equals("pptx") || end.equals("ppt")) {
            type = "application/vnd.ms-powerpoint";
        } else if (end.equals("docx") || end.equals("doc")) {
            type = "application/vnd.ms-word";
        } else if (end.equals("xlsx") || end.equals("xls")) {
            type = "application/vnd.ms-excel";
        } else if (end.equals("txt")) {
            type = "text/plain";
        } else if (end.equals("html") || end.equals("htm")) {
            type = "text/html";
        } else {
            //如果无法直接打开，就跳出软件列表给用户选择
            type = "*/*";
        }
        return type;
    }
}

package com.jushi.library.compression;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.jushi.library.compression.luban.CompressionPredicate;
import com.jushi.library.compression.luban.Luban;
import com.jushi.library.compression.luban.OnCompressListener;
import com.jushi.library.compression.luban.OnRenameListener;
import com.jushi.library.compression.minterface.OnCompressionPicturesListener;
import com.jushi.library.compression.minterface.OnPictureCompressionListener;
import com.jushi.library.compression.utils.PATH;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 图片压缩工具类
 */
public class PictureCompression {

    private static File temPictureFile = new File(PATH.getImageSaveDir());

    private static List<File> compressPhotos;

    private static void initialize(Context context) {
        PATH.initialize(context);
    }

    private static String getTargetDir() {
        return temPictureFile.toString();
    }

    private static String getRename() {
        return "compression-" + getCurrentTime() + ".jpg";
    }

    private static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    private static boolean needCompress(String path) {
        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
    }

    /**
     * 图片压缩
     *
     * @param context
     * @param path     图片保存路径
     * @param listener 回掉接口
     */
    public static void compressionPicture(Context context, String path, final OnPictureCompressionListener listener) {
        initialize(context);
        Luban.with(context)
                .load(path)
                .ignoreBy(80) //不压缩阈值 80KB
                .setTargetDir(getTargetDir())
                .setFocusAlpha(false)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {//gif图不压缩
                        return needCompress(path);
                    }
                })
                .setRenameListener(new OnRenameListener() {
                    @Override
                    public String rename(String filePath) {
                        return getRename();
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        listener.onCompressStart();
                    }

                    @Override
                    public void onSuccess(File file) {
                        listener.onCompressSuccess(file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onCompressError(e.getMessage());
                    }
                }).launch();
    }

    /**
     * 图片压缩
     *
     * @param context
     * @param file
     * @param listener 回掉接口
     */
    public static void compressionPicture(Context context, File file, final OnPictureCompressionListener listener) {
        initialize(context);
        Luban.with(context)
                .load(file)
                .ignoreBy(20)
                .setTargetDir(getTargetDir())
                .setFocusAlpha(false)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return needCompress(path);
                    }
                })
                .setRenameListener(new OnRenameListener() {
                    @Override
                    public String rename(String filePath) {
                        return getRename();
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        listener.onCompressStart();
                    }

                    @Override
                    public void onSuccess(File file) {
                        listener.onCompressSuccess(file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onCompressError(e.getMessage());
                    }
                }).launch();
    }

    /**
     * 图片压缩
     *
     * @param context
     * @param uri
     * @param listener 回掉接口
     */
    public static void compressionPicture(Context context, Uri uri, final OnPictureCompressionListener listener) {
        initialize(context);
        Luban.with(context)
                .load(uri)
                .ignoreBy(100)
                .setTargetDir(getTargetDir())
                .setFocusAlpha(false)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return needCompress(path);
                    }
                })
                .setRenameListener(new OnRenameListener() {
                    @Override
                    public String rename(String filePath) {
                        return getRename();
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        listener.onCompressStart();
                    }

                    @Override
                    public void onSuccess(File file) {
                        listener.onCompressSuccess(file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onCompressError(e.getMessage());
                    }
                }).launch();
    }

    /**
     * 多张图片压缩
     *
     * @param context
     * @param photos   装有图片的集合  （集合的泛型可以是  File、String和Uri 类型）
     * @param listener 回掉接口
     */
    public static void compressPictures(Context context, List<Object> photos, final OnCompressionPicturesListener listener) throws IOException {
        initialize(context);
        compressPhotos = Luban.with(context)
                .setTargetDir(getTargetDir())
                .ignoreBy(80)
                .setRenameListener(new OnRenameListener() {
                    @Override
                    public String rename(String filePath) {
                        return getRename();
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        listener.onPictureFiles(compressPhotos);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(e.getMessage());
                    }
                }).load(photos).get();
    }

}

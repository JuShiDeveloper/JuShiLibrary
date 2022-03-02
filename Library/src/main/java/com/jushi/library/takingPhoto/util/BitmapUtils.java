//$_FILEHEADER_BEGIN ***************************
//版权声明: 贵阳朗玛信息技术股份有限公司版权所有
//Copyright (C) 2007 Longmaster Corporation. All Rights Reserved
//文件名称: BitmapUtils.java
//创建日期: 2012/11/23
//创 建 人:  czc
//文件说明: Bitmap utils
//$_FILEHEADER_END *****************************
package com.jushi.library.takingPhoto.util;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.util.Log;


import com.jushi.library.base.BaseApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class BitmapUtils {
    public static final int ORIENTATION_ROTATE_0 = 0;
    public static final int ORIENTATION_ROTATE_90 = 90;
    public static final int ORIENTATION_ROTATE_180 = 180;
    public static final int ORIENTATION_ROTATE_270 = 270;
    /**
     * Constant used to indicate we should recycle the input in
     * input.
     */
    public static final int OPTIONS_RECYCLE_INPUT = 0x2;
    private static final String TAG = "BitmapUtils";
    private static final int OPTIONS_NONE = 0x0;
    private static final int OPTIONS_SCALE_UP = 0x1;
    @SuppressWarnings("unused")
    private static int m_screenWidth = -1;
    @SuppressWarnings("unused")
    private static int m_screenHeight = -1;

    /**
     * 保存图像
     */
    public static boolean saveImage(Bitmap aBitmap, String aStrFileName, int aQuality) {
        if (aBitmap == null) {
            return false;
        }
        boolean result = false;
        FileOutputStream fo = null;
        try {
            if (aQuality > 100 || aQuality < 0)
                aQuality = 90;

            fo = new FileOutputStream(aStrFileName);
            result = aBitmap.compress(CompressFormat.JPEG, aQuality, fo);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fo != null) {
                try {
                    fo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 保存bitmap到本地
     *
     * @param bitmap
     * @param file
     * @return
     */
    public static File saveBitmap(Bitmap bitmap, File file) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(CompressFormat.JPEG, 90, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            file = null;
        }
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 根据指定的最大宽度和最大高度读取图像
     */
    public static Bitmap decodeFromPath(String aPath, int aMaxWidth, int aMaxHeight) {
        File lBitmapFile = new File(aPath);
        if (!lBitmapFile.exists() || !lBitmapFile.isFile()) {
            lBitmapFile = null;
            return null;
        }

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(aPath, opts);

        opts.inSampleSize = computeSampleSize(opts, aMaxWidth > aMaxHeight ? aMaxHeight : aMaxWidth, aMaxWidth * aMaxHeight);
        opts.inJustDecodeBounds = false;

        Bitmap resultBitmap = null;

        try {
            resultBitmap = BitmapFactory.decodeFile(aPath, opts);
        } catch (OutOfMemoryError oom) {
            // 没有足够的内存来读取图像
        }

        return resultBitmap;
    }

    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options a_oOptions, int a_iMinSideLength, int a_iMaxNumOfPixels) {
        double w = a_oOptions.outWidth;
        double h = a_oOptions.outHeight;

        int lowerBound = (a_iMaxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / a_iMaxNumOfPixels));
        int upperBound = (a_iMinSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / a_iMinSideLength), Math.floor(h / a_iMinSideLength));

        if (upperBound < lowerBound) {
            return lowerBound;
        }

        if ((a_iMaxNumOfPixels == -1) && (a_iMinSideLength == -1)) {
            return 1;
        } else if (a_iMinSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    /**
     * Drawable转Bitmap
     *
     * @param drawable Drawable对象
     * @return 转换后的Bitmap对象
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }


    /**
     * 将指定路径图片文件按指定大小缩放解码为Bitmap对象
     *
     * @param path      图片路径
     * @param reqWidth  缩放宽度
     * @param reqHeight 缩放高度
     * @return 缩放后的Bitmap对象
     */
    public static Bitmap decodeSampledFile(String path, int reqWidth, int reqHeight, Bitmap.Config config) {
        if (TextUtils.isEmpty(path) || reqWidth <= 0 || reqHeight <= 0) {
            return null;
        }

        final BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, opts);

        opts.inSampleSize = calculateInSampleSize(opts, reqWidth, reqHeight);
        opts.inJustDecodeBounds = false;
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        opts.inPreferredConfig = config;

        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeFile(path, opts);

            int degree = readPictureDegree(path);
            if (degree != 0 && bitmap != null) {
                Matrix matrix = new Matrix();
                matrix.setRotate(degree);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            }

        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 计算BitmapFactory.Options对象中inSampleSize（缩放比例）值
     *
     * @param options   BitmapFactory.Options 对象
     * @param reqWidth  缩放宽度
     * @param reqHeight 缩放高度
     * @return inSampleSize（缩放比例）值
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 旋转图像。如果操作成功并生成一个新的图像，旧图像将被释放
     */
    public static Bitmap rotate(Bitmap bitmap, int degrees) {
        return rotateAndMirror(bitmap, degrees, false);
    }

    /**
     * 图像旋转和镜像。如果操作成功并生成一个新的图像，旧图像将被释放
     */
    public static Bitmap rotateAndMirror(Bitmap aBitmap, int aDegrees, boolean aMirror) {
        if ((aDegrees != 0 || aMirror) && aBitmap != null) {
            Matrix m = new Matrix();
            // Mirror first.
            // horizontal flip + rotation = -rotation + horizontal flip
            if (aMirror) {
                m.postScale(-1, 1);
                aDegrees = (aDegrees + 360) % 360;
                if (aDegrees == 0 || aDegrees == 180) {
                    m.postTranslate(aBitmap.getWidth(), 0);
                } else if (aDegrees == 90 || aDegrees == 270) {
                    m.postTranslate(aBitmap.getHeight(), 0);
                } else {
                    throw new IllegalArgumentException("Invalid degrees=" + aDegrees);
                }
            }
            if (aDegrees != 0) {
                // clockwise
                m.postRotate(aDegrees, (float) aBitmap.getWidth() / 2, (float) aBitmap.getHeight() / 2);
            }

            try {
                Bitmap b2 = Bitmap.createBitmap(aBitmap, 0, 0, aBitmap.getWidth(), aBitmap.getHeight(), m, true);
                if (aBitmap != b2) {
                    aBitmap.recycle();
                    aBitmap = b2;
                }
            } catch (OutOfMemoryError ex) {
                // We have no memory to rotate. Return the original bitmap.
            }
        }
        return aBitmap;
    }

    /**
     * 将bitmap转换为Drawable
     */
    public static Drawable getDrawable(Bitmap bitmap) {
        BitmapDrawable bitmapDrawable = new BitmapDrawable(BaseApplication.getInstance().getResources(), bitmap);
        bitmapDrawable.setAntiAlias(true);
        bitmapDrawable.setFilterBitmap(true);
        return bitmapDrawable;
    }

    public static boolean saveImage(EImageType imageType, String savePath, String lTempFilePath, int maxPx) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(lTempFilePath, options);
        double scale = Math.sqrt(options.outWidth * options.outHeight / maxPx);
        int width = options.outWidth;
        int height = options.outHeight;
        if (scale > 1) {
            width = (int) (options.outWidth / scale);
            height = (int) (options.outHeight / scale);
            Log.d(TAG, "save image by max px , new width = " + width + " , new height = " + height);
        }
        int degree = getPictureExifDegree(lTempFilePath);
        if (degree % 180 != 0) {
            int temp = width;
            width = height;
            height = temp;
        }
        return saveImage(imageType, savePath, lTempFilePath, width, height);
    }

    public static boolean saveImage(EImageType imageType, String savePath, String lTempFilePath, int width, int height) {
        FileOutputStream fos1 = null;
        FileOutputStream fos2 = null;
        Bitmap bitmap = null;
        Bitmap saveBitmap = null;
        byte[] buffer = null;
        int degree = 0;
        try {

            degree = getPictureExifDegree(lTempFilePath);
            if (degree % 180 != 0) {
                int temp = width;
                width = height;
                height = temp;
            }
            Log.d(TAG, "from = " + lTempFilePath + " , to = " + savePath);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(lTempFilePath, options);
            options.inSampleSize = calculateInSampleSize(options, width, height);

            int offSetX = 0, offSetY = 0;
            // if (imageType == EImageType.JPEG || imageType == EImageType.PNG)
            // {
            // 开始计算图片尺寸
            float scaleOld, scaleNew;
            scaleOld = (float) options.outWidth / options.outHeight;
            scaleNew = (float) width / height;

            int cutHeight = options.outHeight / options.inSampleSize;
            int cutWidth = options.outWidth / options.inSampleSize;

            if (scaleOld > scaleNew) {
                width = cutHeight * width / height;
                height = cutHeight;
                Log.d(TAG, "宽了");
                offSetX = (width - cutWidth) / 2;
            } else {
                height = cutWidth * height / width;
                width = cutWidth;
//                if (BuildConfig.DEBUG)
//                    Log.d(TAG, "高了");
                offSetY = (cutWidth * height / width - cutHeight) / 2;
            }
//            if (BuildConfig.DEBUG)
//                Log.d(TAG, "offSetX = " + offSetX + " , offSetY = " + offSetY);

            // }

            // 配置读取图片大小。
            options.inJustDecodeBounds = false;
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inDither = false;
            buffer = new byte[16384];
            options.inTempStorage = buffer;

            bitmap = BitmapFactory.decodeFile(lTempFilePath, options);
            Matrix lMatrix = new Matrix();
            lMatrix.setRotate(degree, width / 2, (float) height / 2);
            if (bitmap != null) {
                switch (imageType) {
                    default:
                    case JPEG:
                    case PNG:
                        // if (offSetX != 0 || offSetY != 0) {
                        saveBitmap = Bitmap.createBitmap(bitmap, Math.abs(offSetX), Math.abs(offSetY), width, height, lMatrix, true);
                        // } else {
                        // saveBitmap = bitmap;
                        // }
                        CompressFormat lCompressFormat = imageType == EImageType.JPEG ? CompressFormat.JPEG : CompressFormat.PNG;
                        fos1 = new FileOutputStream(savePath);
                        boolean result = saveBitmap.compress(lCompressFormat, 75, fos1);
                        fos1.flush();
                        return result;
                    case ROUNDED_IMAGE:
                        // Bitmap tempBitmap = Bitmap.createScaledBitmap(bitmap,
                        // width, height, true);
                        Bitmap tempBitmap = Bitmap.createBitmap(bitmap, Math.abs(offSetX), Math.abs(offSetY), width, height, lMatrix, true);
                        float fRound = tempBitmap.getWidth() / 12;
                        saveBitmap = Bitmap.createBitmap(tempBitmap.getWidth(), tempBitmap.getHeight(), Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(saveBitmap);
                        final int color = 0xff424242;
                        final Paint paint = new Paint();
                        final Rect rect = new Rect(0, 0, tempBitmap.getWidth(), tempBitmap.getHeight());
                        final RectF rectF = new RectF(rect);
                        paint.setAntiAlias(true);
                        canvas.drawARGB(0, 0, 0, 0);
                        paint.setColor(color);
                        canvas.drawRoundRect(rectF, fRound, fRound, paint);
                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                        canvas.drawBitmap(tempBitmap, rect, rect, paint);
                        releaseBitmap(tempBitmap);
                        fos2 = new FileOutputStream(savePath);
                        boolean result2 = saveBitmap.compress(CompressFormat.PNG, 75, fos2);
                        fos2.flush();
                        return result2;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (degree % 180 != 0)
                try {
                    setPictureExifDegree(savePath, 0);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            releaseBitmap(saveBitmap);
            if (buffer != null) {
                buffer = null;
            }
            if (fos1 != null) {
                try {
                    fos1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos2 != null) {
                try {
                    fos2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static void releaseBitmap(Bitmap... bitmaps) {
        try {
            for (Bitmap bm : bitmaps) {
                if (bm != null) {
                    if (!bm.isRecycled())
                        bm.recycle();
                    bm = null;
                }
            }
            bitmaps = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getPictureExifDegree(String aPath) {
        ExifInterface exifInterface = null;
        try {
            exifInterface = new ExifInterface(aPath);
        } catch (IOException e) {
            e.printStackTrace();
            return ORIENTATION_ROTATE_0;
        }
        int degree = ORIENTATION_ROTATE_0;
        int rotate = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        switch (rotate) {
            case ExifInterface.ORIENTATION_NORMAL:
                degree = ORIENTATION_ROTATE_0;
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                degree = ORIENTATION_ROTATE_90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                degree = ORIENTATION_ROTATE_180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                degree = ORIENTATION_ROTATE_270;
                break;
        }
        return degree;
    }

    /**
     * 设置图像Exif方向信息
     *
     * @param aPath   图像文件路径
     * @param aDegree 方向角度：{@link #ORIENTATION_ROTATE_0} 、 {@link #ORIENTATION_ROTATE_90} 、 {@link #ORIENTATION_ROTATE_180} 、
     *                {@link #ORIENTATION_ROTATE_270}
     * @throws Exception
     */
    public static void setPictureExifDegree(String aPath, int aDegree) throws Exception {
        ExifInterface exifInterface = new ExifInterface(aPath);
        switch (aDegree) {
            case ORIENTATION_ROTATE_0:
                exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, String.valueOf(ExifInterface.ORIENTATION_NORMAL));
                break;

            case ORIENTATION_ROTATE_90:
                exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, String.valueOf(ExifInterface.ORIENTATION_ROTATE_90));
                break;

            case ORIENTATION_ROTATE_180:
                exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, String.valueOf(ExifInterface.ORIENTATION_ROTATE_180));
                break;

            case ORIENTATION_ROTATE_270:
                exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, String.valueOf(ExifInterface.ORIENTATION_ROTATE_270));
                break;

            default:
                return;
        }
        exifInterface.saveAttributes();
    }

    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    public enum EImageType {
        /**
         * .jpg
         */
        JPEG(0),
        /**
         * .png
         */
        PNG(1),
        /**
         * .png 圆角
         */
        ROUNDED_IMAGE(2),
        /**
         * Head
         */
        @Deprecated
        HEAD(3);

        private final int mType;

        EImageType(int type) {
            mType = type;
        }

        public int toValue() {
            return mType;
        }
    }
}

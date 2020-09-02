package com.jushi.library.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.RequiresApi;

/**
 * 高斯模糊工具类
 * <p>
 * Created by wyf on 2019/11/17.
 */

public class GaussUtils {

    /** 默认模糊半径 */
    public static int DEFAULT_BLUR_RADIUS = 25;

    /**
     * 快速模糊
     *
     * @param context Context
     * @param bitmap  源bitmap
     * @return 模糊后的bitmap
     */
    public static Bitmap fastBlur(Context context, Bitmap bitmap) {
        return fastBlur(context, bitmap, DEFAULT_BLUR_RADIUS);
    }

    /**
     * 高斯模糊（先压缩图片）
     *
     * @param context Context
     * @param bitmap  源bitmap
     * @param radius  模糊半径(Api17以下有效)
     * @return 模糊后的bitmap
     */
    public static Bitmap fastBlur(Context context, Bitmap bitmap, int radius) {
        return fastBlur(context, bitmap, radius, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
    }

    /**
     * 高斯模糊
     *
     * @param context Context
     * @param bitmap  源bitmap
     * @param radius  模糊半径(Api17以下有效)
     * @param width   模糊图片宽度
     * @param height  模糊图片高度
     * @return 模糊后的bitmap
     */
    public static Bitmap fastBlur(Context context, Bitmap bitmap, int radius, int width, int height) {
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return doBlurByRenderScript(context, bitmap);
        } else {
            return doBlur(bitmap, radius);
        }
    }

    /**
     * 低版本进行原生计算模糊
     *
     * @param sentBitmap 源bitmap
     * @param radius     模糊半径
     * @return bitmap
     */
    private static Bitmap doBlur(Bitmap sentBitmap, int radius) {
        Bitmap bitmap;
        bitmap = sentBitmap.copy(Bitmap.Config.ARGB_8888, true);

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        for (int i = 0; i < pix.length; i++) {
            int pointI = pix[i];
            if ((pointI & 0xFF000000) == 0) {
                pix[i] = 0xFFFFFFFF;
            }
        }

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        long dv[] = new long[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = (int) dv[rsum];
                g[yi] = (int) dv[gsum];
                b[yi] = (int) dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (int) ((0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum]);

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        bitmap.setPixels(pix, 0, w, 0, 0, w, h);

        return (bitmap);
    }

    /**
     * 调用系统renderscript进行模糊
     *
     * @param context Context
     * @param bitmap  源bitmap
     * @return bitmap
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static Bitmap doBlurByRenderScript(Context context, Bitmap bitmap) {
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);

        blurScript.setRadius(24);
        blurScript.setInput(allIn);
        blurScript.forEach(allOut);

        allOut.copyTo(outBitmap);
        bitmap.recycle();

        rs.destroy();
        return outBitmap;
    }
}

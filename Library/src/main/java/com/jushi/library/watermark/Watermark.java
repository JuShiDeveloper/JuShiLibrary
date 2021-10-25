package com.jushi.library.watermark;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.annotation.StringDef;
import android.text.TextUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 给图片添加水印
 * create time 2018/9/12
 *
 * @author wyf
 */
public class Watermark {
    /*------------水印显示在图片上的位置------------------*/
    public static final String LEFT_TOP = "left_top"; //左上角
    public static final String LEFT_CENTER = "left_center"; //左边居中
    public static final String LEFT_BOTTOM = "left_bottom";  //左下角
    public static final String TOP_CENTER = "top_center"; //顶部居中
    public static final String CENTER = "center"; //完全居中
    public static final String BOTTOM_CENTER = "bottom_center";//底部居中
    public static final String RIGHT_TOP = "right_top";//右上角
    public static final String RIGHT_CENTER = "right_center"; //右边居中
    public static final String RIGHT_BOTTOM = "right_bottom";//右下角

    private float width = 0f;
    private float height = 0f;
    private String gravity = RIGHT_BOTTOM; //水印的显示位置，默认右下角
    private Paint paint = new Paint();
    private float textWith = 0f; //文字水印的宽
    private float watermarkWidth = 0f; //图片水印的宽
    private float watermarkHeight = 0f; //图片水印的高
    private int alpha = 255;  //水印透明度
    private boolean isRecycle = false;


    private static Watermark watermark;

    public static Watermark getInstance() {
        if (watermark == null) {
            watermark = new Watermark();
        }
        return watermark;
    }

    private Watermark() {
    }


    /**
     * 给图片添加文字水印
     *
     * @param targetBitmap 需要添加水印的图片的bitmap
     * @param text         水印文字
     * @param color        水印文字的颜色
     * @param gravity      水印显示的位置
     * @param textSize     水印文字的大小
     * @param alpha        水印透明度值（0 - 255），值越小越透明
     */
    public Bitmap createTextWatermark(Bitmap targetBitmap, String text, int color, @GravityType String gravity, float textSize, int alpha) {
        this.gravity = gravity;
        this.alpha = alpha;
        this.isRecycle = false;
        return getWatermarkBitmap(targetBitmap, null, text, color, textSize);
    }

    /**
     * 给图片添加文字水印
     *
     * @param targetImageResource 需要添加水印的图片资源
     * @param text                水印文字
     * @param color               水印文字的颜色
     * @param gravity             水印显示的位置
     * @param textSize            水印文字的大小
     * @param alpha               水印透明度值（0 - 255），值越小越透明
     */
    public Bitmap createTextWatermark(Context context, int targetImageResource, String text, int color, @GravityType String gravity, float textSize, int alpha) {
        this.gravity = gravity;
        this.alpha = alpha;
        this.isRecycle = false;
        return getWatermarkBitmap(getBitmap(context, targetImageResource), null, text, color, textSize);
    }

    /**
     * 给图片添加图片水印
     *
     * @param targetBitmap    需要添加水印的图片的bitmap
     * @param watermarkBitmap 作为水印的图片的bitmap
     * @param gravity         水印显示的位置
     * @param alpha           水印透明度值（0 - 255） ，值越小越透明
     */
    public Bitmap createPictureWatermark(Bitmap targetBitmap, Bitmap watermarkBitmap, @GravityType String gravity, int alpha) {
        this.gravity = gravity;
        this.alpha = alpha;
        this.isRecycle = false;
        return getWatermarkBitmap(targetBitmap, watermarkBitmap, "", 0, 0f);
    }

    /**
     * 给图片添加图片水印
     *
     * @param targetImageResource 需要添加水印的图片资源
     * @param watermarkBitmap     作为水印的图片的bitmap
     * @param gravity             水印显示的位置
     * @param alpha               水印透明度值（0 - 255） ，值越小越透明
     */
    public Bitmap createPictureWatermark(Context context, int targetImageResource, Bitmap watermarkBitmap, @GravityType String gravity, int alpha) {
        this.gravity = gravity;
        this.alpha = alpha;
        this.isRecycle = false;
        return getWatermarkBitmap(getBitmap(context, targetImageResource), watermarkBitmap, "", 0, 0f);
    }


    /**
     * 给图片添加图片水印
     *
     * @param context
     * @param targetBitmap 需要添加水印的图片的bitmap
     * @param resourceId   作为水印的图片资源id
     * @param gravity      水印显示的位置
     * @param alpha        水印透明度值（0 - 255） ，值越小越透明
     */
    public Bitmap createPictureWatermark(Context context, Bitmap targetBitmap, int resourceId, @GravityType String gravity, int alpha) {
        this.gravity = gravity;
        this.alpha = alpha;
        this.isRecycle = true;
        return getWatermarkBitmap(targetBitmap, BitmapFactory.decodeResource(context.getResources(), resourceId), "", 0, 0f);
    }

    /**
     * 给图片添加图片水印
     *
     * @param context
     * @param targetImageResource 需要添加水印的图片资源
     * @param resourceId          作为水印的图片资源id
     * @param gravity             水印显示的位置
     * @param alpha               水印透明度值（0 - 255） ，值越小越透明
     */
    public Bitmap createPictureWatermark(Context context, int targetImageResource, int resourceId, @GravityType String gravity, int alpha) {
        this.gravity = gravity;
        this.alpha = alpha;
        this.isRecycle = true;
        return getWatermarkBitmap(getBitmap(context, targetImageResource), BitmapFactory.decodeResource(context.getResources(), resourceId), "", 0, 0f);
    }

    /**
     * 获取添加了水印后的bitmap
     */
    private Bitmap getWatermarkBitmap(Bitmap targetBitmap, Bitmap watermark, String text, int color, float textSize) {
        getWatermarkgravity(targetBitmap);
        Bitmap watermarkBitmap = Bitmap.createBitmap(targetBitmap.getWidth(), targetBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(watermarkBitmap);
        canvas.drawBitmap(targetBitmap, 0f, 0f, null); // 绘制原图
        if (!TextUtils.isEmpty(text)) {
            drawTextWatermark(canvas, text, color, textSize);  //绘制文字水印
        }
        if (watermark != null) {
            drawPictureWatermark(canvas, watermark); //绘制图片水印
        }
        if (isRecycle) {
            watermark.recycle();
        }
        return watermarkBitmap;
    }


    /**
     * 绘制图片水印
     */
    private void drawPictureWatermark(Canvas canvas, Bitmap watermark) {
        watermarkWidth = watermark.getWidth();
        watermarkHeight = watermark.getHeight();
        getPictureWatermarkGravity();
        paint.setAlpha(alpha);
        canvas.drawBitmap(watermark, width, height, paint); //绘制图片水印
    }


    /**
     * 绘制文字水印
     */
    private void drawTextWatermark(Canvas canvas, String text, int color, float textSize) {
        paint.setColor(color);
        paint.setTextSize(textSize);
        paint.setAntiAlias(true);
        textWith = paint.measureText(text);
        paint.setAlpha(alpha);
        switch (gravity) {  //文字水印显示的详细位置
            case TOP_CENTER:
            case CENTER:
            case BOTTOM_CENTER:
                width -= textWith / 2;
                break;
            case RIGHT_TOP:
            case RIGHT_CENTER:
            case RIGHT_BOTTOM:
                width = width - textWith - 15;
                break;
        }
        canvas.drawText(text, width, height, paint);   //绘制文字水印
    }


    /**
     * 图片水印显示的详细位置
     */
    private void getPictureWatermarkGravity() {
        switch (gravity) {
            case TOP_CENTER:
                width -= watermarkWidth / 2;
                height -= 20;
                break;
            case CENTER:
                width -= watermarkWidth / 2;
                height -= watermarkHeight / 2;

                break;

            case BOTTOM_CENTER:
                width -= watermarkWidth / 2;
                height -= watermarkHeight;
                break;
            case LEFT_CENTER:
                height -= watermarkHeight / 2;
                break;
            case LEFT_BOTTOM:
                height -= watermarkHeight;
                break;
            case RIGHT_TOP:
                width = width - watermarkWidth - 15;
                height -= 20;
                break;
            case RIGHT_CENTER:
                width = width - watermarkWidth - 15;
                height -= watermarkHeight / 2;
                break;
            case RIGHT_BOTTOM:
                width = width - watermarkWidth - 15;
                height -= watermarkHeight;
                break;
        }
    }


    /**
     * 获取水印显示在图片上的初始位置
     */
    private void getWatermarkgravity(Bitmap targetBitmap) {
        switch (gravity) {
            case LEFT_TOP: //水印显示在左上角
                width = (targetBitmap.getWidth() / 55);
                height = (targetBitmap.getHeight() / 18);
                break;
            case LEFT_CENTER://水印显示在左边居中
                width = (targetBitmap.getWidth() / 55);
                height = (targetBitmap.getHeight() / 2);
                break;
            case LEFT_BOTTOM:  //水印显示在左下角
                width = (targetBitmap.getWidth() / 55);
                height = (targetBitmap.getHeight() - 15);
                break;
            case TOP_CENTER:  //顶部居中
                width = (targetBitmap.getWidth() / 2);
                height = (targetBitmap.getHeight() / 20);
                break;
            case CENTER:  //完全居中
                width = (targetBitmap.getWidth() / 2);
                height = (targetBitmap.getHeight() / 2);
                break;
            case BOTTOM_CENTER://底部居中
                width = (targetBitmap.getWidth() / 2);
                height = (targetBitmap.getHeight() - 15);
                break;
            case RIGHT_TOP: //右上角
                width = targetBitmap.getHeight();
                height = (targetBitmap.getHeight() / 20);
                break;
            case RIGHT_CENTER:  //右边居中
                width = targetBitmap.getWidth();
                height = (targetBitmap.getHeight() / 2);
                break;
            case RIGHT_BOTTOM:   //右下角
                width = targetBitmap.getWidth();
                height = (targetBitmap.getHeight() - 15);
                break;
        }
    }

    private Bitmap getBitmap(Context context, int targetImageResource) {
        Drawable drawable = context.getResources().getDrawable(targetImageResource);
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({LEFT_TOP, LEFT_CENTER, LEFT_BOTTOM, TOP_CENTER, CENTER, BOTTOM_CENTER, RIGHT_TOP, RIGHT_CENTER, RIGHT_BOTTOM})
    @interface GravityType {
    }
}

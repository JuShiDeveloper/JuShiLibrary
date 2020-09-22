package com.jushi.library.customView.radar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;


import java.util.List;

/**
 * 五边形雷达view
 */
public class RadarView extends View {

    private final static String TAG = RadarView.class.getSimpleName();

    private List<RadarData> dataList;

    private int count = 6;//雷达网圈数
    private float angle;//多边形弧度
    private float radius;
    private float maxValue = 100f;
    private Paint mainPaint;//雷达区画笔
    private Paint valuePaint;//数据区画笔
    private Paint dotPaint; //数据区域圆点画笔
    private Paint textPaint;//文本画笔

    private int mainColor = 0xFF888888;//雷达区颜色
    private int valueColor = 0xFF79D4FD;//数据区颜色
    private int textColor = 0xFF808080;//文本颜色

    private float mainLineWidth = 0.5f;//雷达网线宽度dp
    private float valueLineWidth = 1f;//数据区边宽度dp
    private float valuePointRadius = 2f;//数据区圆点半径dp
    private float textSize = 14f;//字体大小sp

    private int mWidth, mHeight;

    public RadarView(Context context) {
        this(context, null);
    }

    public RadarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setup();
    }

    private void setup() {
        mainPaint = new Paint();
        mainPaint.setAntiAlias(true);
//        mainPaint.setColor(mainColor);
        mainPaint.setStyle(Paint.Style.STROKE);

        valuePaint = new Paint();
        valuePaint.setAntiAlias(true);
//        valuePaint.setColor(valueColor);
        valuePaint.setColor(Color.parseColor("#5b6aff"));
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.parseColor("#333333"));

        dotPaint = new Paint();
        dotPaint.setAntiAlias(true);
        dotPaint.setColor(Color.parseColor("#5b6aff")); //圆点的颜色
        dotPaint.setStyle(Paint.Style.FILL_AND_STROKE);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        radius = Math.min(h, w) / 2 * 0.6f;
        mWidth = w;
        mHeight = h;
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(mWidth / 2, mHeight / 2);

        if (isDataListValid()) {
            drawSpiderweb(canvas);
            drawText(canvas);
            drawRegion(canvas);
        }
    }

    /**
     * 绘制蜘蛛网
     * a7dbff  虚线颜色值
     *
     * @param canvas
     */
    private void drawSpiderweb(Canvas canvas) {
        mainPaint.setStrokeWidth(dip2px(getContext(), mainLineWidth));
        Path webPath = new Path();
        Path linePath = new Path();
        float r = radius / (count - 1);//蜘蛛丝之间的间距
        for (int i = 0; i < count; i++) {
            float curR = (r * i) + 80;//当前半径
            webPath.reset();
            for (int j = 0; j < count; j++) {
                float x = (float) (curR * Math.sin(angle / 2 + angle * j));
                float y = (float) (curR * Math.cos(angle / 2 + angle * j));
                if (j == 0) {
                    webPath.moveTo(x, y);
                } else {
                    webPath.lineTo(x, y);
                }
                mainPaint.setColor(Color.parseColor("#a7dbff"));//虚线颜色
                mainPaint.setPathEffect(new DashPathEffect(new float[]{10f, 6f}, 0f));//设置虚线
                mainPaint.setStrokeWidth(4);
                if (i == count - 1) {//当绘制最后一环时绘制连接线
                    linePath.reset();
                    linePath.moveTo(0, 0);
                    linePath.lineTo(x, y);
                    canvas.drawPath(linePath, mainPaint);
                }
            }
            webPath.close();
            if (i == count - 1) { //最后一环线加粗
                mainPaint.setPathEffect(new DashPathEffect(new float[]{8f, 0f}, 0f));//虚线改为实线
                mainPaint.setColor(Color.parseColor("#5b6aff"));//最后一环颜色
                mainPaint.setStrokeWidth(6);
            }
            canvas.drawPath(webPath, mainPaint);
        }
    }

    /**
     * 绘制文本
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        textPaint.setTextSize(sp2px(getContext(), textSize));
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;
        for (int i = 0; i < count; i++) {
            float x = (float) ((radius + fontHeight * 2) * Math.sin(angle / 2 + angle * i));
            float y = (float) ((radius + fontHeight * 2) * Math.cos(angle / 2 + angle * i));
            String title = dataList.get(i).getTitle();
            float dis = textPaint.measureText(title);//文本长度
            float sx = x - dis / 2;
            switch (i) {
                case 0:
                case 4:
                    y += 40;
                    break;
                case 1:
                    sx += 40;
                    break;
                case 2:
                    y -= 6;
                    break;
                case 3:
                    sx -= 40;
                    break;
            }
            canvas.drawText(title, sx, y, textPaint);
        }
    }

    /**
     * 绘制区域
     *
     * @param canvas
     */
    private void drawRegion(Canvas canvas) {
        valuePaint.setStrokeWidth(dip2px(getContext(), valueLineWidth));
        Path path = new Path();
        valuePaint.setAlpha(255);
        path.reset();
        //设置path路径
        for (int i = 0; i < count; i++) {
            double percent = dataList.get(i).getPercentage() / maxValue;
            float x = (float) (radius * Math.sin(angle / 2 + angle * i) * percent);
            float y = (float) (radius * Math.cos(angle / 2 + angle * i) * percent);
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }
        path.close();
//        valuePaint.setStyle(Paint.Style.STROKE);
//        canvas.drawPath(path, valuePaint); //绘制圆点之间的连线
        //设置覆盖区域颜色渐变
        //start color   fbadf1
        //过度color 2、ef61c8  3、e439b5
        //end color c3369c
        LinearGradient linearGradient = new LinearGradient(150, 50, 150, 300, new int[]{
//                Color.rgb(253,226,253),
                Color.rgb(251, 173, 241),
                Color.rgb(239, 97, 200),
                Color.rgb(228, 57, 181),
                Color.rgb(195, 54, 156)},
                new float[]{0, .3F, .6F, .9F}, Shader.TileMode.CLAMP);
//        //new float[]{},中的数据表示相对位置，将150,50,150,300，划分10个单位，.3，.6，.9表示它的绝对位置。300到400，将直接画出rgb（0,232,210）
        valuePaint.setShader(linearGradient);
        valuePaint.setAlpha(200);
        //绘制填充区域
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(path, valuePaint);
        //绘制圆点
        for (int i = 0; i < count; i++) {
            double percent = dataList.get(i).getPercentage() / maxValue;
            float x = (float) (radius * Math.sin(angle / 2 + angle * i) * percent);
            float y = (float) (radius * Math.cos(angle / 2 + angle * i) * percent);
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
            //绘制小圆点
            canvas.drawCircle(x, y, dip2px(getContext(), valuePointRadius), dotPaint);
        }
    }


    private boolean isDataListValid() {
        return dataList != null && dataList.size() >= 3;
    }


    public void setDataList(List<RadarData> dataList) {
        if (dataList == null || dataList.size() < 3) {
            throw new RuntimeException("The number of data can not be less than 3");
        } else {
            this.dataList = dataList;
            count = dataList.size();//圈数等于数据个数，默认为6
            angle = (float) (Math.PI * 2 / count);
            invalidate();
        }
    }


    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}

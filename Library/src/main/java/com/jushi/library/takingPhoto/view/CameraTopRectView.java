package com.jushi.library.takingPhoto.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.jushi.library.R;


/**
 * 在自定义拍照界面显示的参照矩形框，并且根据该矩形框获取框内图片内容
 * create time 2019/9/4
 *
 * @author JuShi
 */
public class CameraTopRectView extends View {

    private int panelWidth;
    private int panelHeght;
    private int viewWidth;
    private int viewHeight;
    private int rectWidth;
    private int rectHeght;
    private int rectTop;
    private int rectLeft;
    private int rectRight;
    private int rectBottom;
    private int lineLen;
    private int lineWidht;
    private static final int LINE_WIDTH = 5;
    private static final int TOP_BAR_HEIGHT = 50;
    private static final int BOTTOM_BTN_HEIGHT = 66;
    private static final int LEFT_PADDING = 10;
    private static final int RIGHT_PADDING = 10;
    private static final String TIPS = "请将证件置于上面矩形区域内进行拍摄";
    private Paint linePaint;
    private Paint wordPaint;
    private Rect rect;
    private int baseline;

    private Rect leftRect;
    private Rect topRect;
    private Rect rightRect;
    private Rect bottomRect;
    private Paint shadowRectPaint;

    private final int VERTICAL = 1;
    private final int HORIZONTAL = 2;
    private int orientation = HORIZONTAL;

    public CameraTopRectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CameraTopRectView);
        orientation = typedArray.getInt(R.styleable.CameraTopRectView_morientation, HORIZONTAL);
        init();
        initRect();
    }


    private void init() {
        panelWidth = getContext().getResources().getDisplayMetrics().widthPixels;
        panelHeght = getContext().getResources().getDisplayMetrics().heightPixels;
        viewHeight = panelHeght - dip2px(TOP_BAR_HEIGHT + BOTTOM_BTN_HEIGHT);
        viewWidth = panelWidth;
        switch (orientation) {
            case HORIZONTAL: //横屏
                rectWidth = panelWidth - panelWidth / 4;
                rectHeght = panelHeght - panelHeght / 8; // 相对于此view
                rectTop = panelHeght / 8;
                rectLeft = panelWidth / 7;
                break;
            case VERTICAL: //竖屏
                rectWidth = panelWidth - 100;
                rectHeght = panelHeght - panelHeght / 8 ; // 相对于此view
                rectTop = panelHeght / 5;
                rectLeft = 100;
                break;
        }
        rectBottom = rectHeght;
        rectRight = rectWidth;
        lineLen = panelWidth / 8;
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.GREEN);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(LINE_WIDTH);// 设置线宽
        linePaint.setAlpha(255);
        wordPaint = new Paint();
        wordPaint.setAntiAlias(true);
        wordPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        wordPaint.setStrokeWidth(3);
        wordPaint.setTextSize(50);
        rect = new Rect(rectLeft, rectBottom, rectRight, panelHeght);
        baseline = panelHeght - rectTop / 2;
        wordPaint.setTextAlign(Paint.Align.CENTER);
    }

    private void initRect() {
        leftRect = new Rect(0, 0, rectLeft, panelHeght);
        topRect = new Rect(rectLeft, 0, panelWidth, rectTop);
        rightRect = new Rect(rectRight, rectTop, panelWidth, panelHeght);
        bottomRect = new Rect(rectLeft, rectBottom, rectRight, panelHeght);
        shadowRectPaint = new Paint();
        shadowRectPaint.setColor(Color.parseColor("#7f121415"));
        shadowRectPaint.setStyle(Paint.Style.FILL);
        shadowRectPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (orientation == HORIZONTAL) {
            wordPaint.setColor(Color.TRANSPARENT);
            canvas.drawRect(rect, wordPaint);
            wordPaint.setColor(Color.WHITE);
            canvas.drawText(TIPS, rect.centerX(), baseline, wordPaint);
        }
        canvas.drawLine(rectLeft, rectTop, rectLeft + lineLen, rectTop, linePaint);
        canvas.drawLine(rectRight - lineLen, rectTop, rectRight, rectTop, linePaint);
        canvas.drawLine(rectLeft, rectTop, rectLeft, rectTop + lineLen, linePaint);
        canvas.drawLine(rectRight, rectTop, rectRight, rectTop + lineLen, linePaint);
        canvas.drawLine(rectLeft, rectBottom, rectLeft + lineLen, rectBottom, linePaint);
        canvas.drawLine(rectRight - lineLen, rectBottom, rectRight, rectBottom, linePaint);
        canvas.drawLine(rectLeft, rectBottom - lineLen, rectLeft, rectBottom, linePaint);
        canvas.drawLine(rectRight, rectBottom - lineLen, rectRight, rectBottom, linePaint);
        //画上下左右四个阴影（遮罩）矩形
        canvas.drawRect(leftRect, shadowRectPaint);
        canvas.drawRect(topRect, shadowRectPaint);
        canvas.drawRect(rightRect, shadowRectPaint);
        canvas.drawRect(bottomRect, shadowRectPaint);
    }

    private int dip2px(int dip) {
        return (int) (getContext().getResources().getDisplayMetrics().density * dip + 0.5f);
    }

    public int getRectLeft() {
        return rectLeft;
    }

    public int getRectTop() {
        return rectTop;
    }

    public int getRectRight() {
        return rectRight;
    }

    public int getRectBottom() {
        return rectBottom;
    }

    public int getViewWidth() {
        return viewWidth;
    }

    public int getViewHeight() {
        return viewHeight;
    }
}


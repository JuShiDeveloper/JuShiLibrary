package com.jushi.library.customView.scaleImageView;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;

/**
 * @author jushi
 * <p>
 * careate time：2019/04/24
 * <p>
 * 功能：
 * 1、手势缩放图片
 * 2、放大后单指触摸拖动图片
 * 3、双击放大缩小
 */
public class ScaleImageView extends AppCompatImageView implements View.OnTouchListener,
        ScaleGestureDetector.OnScaleGestureListener, ViewTreeObserver.OnGlobalLayoutListener {

    /**
     * --------手指控制图片缩放的变量------------
     **/
    private boolean mOnce = false;
    //初始化时缩放的值
    private float initScale = 0.0f;
    //双击放大时到达的值
    private float midScale = 0.0f;
    //放大的最大值
    private float maxScale = 0.0f;
    private Matrix mScaleMatrix;
    //多点触控
    private ScaleGestureDetector mScaleGestureDetector;
    /**
     * --------移动相关的变量-----------------------------------
     **/
    //记录上一次触控点的数量
    private int lastPointCount = 0;
    //最后一次的位置
    private float lastX = 0.0f;
    private float lastY = 0.0f;
    private int touchSlop = 0;
    private boolean isCanDrag = false;
    private boolean isCheckLeftAndRight = false;
    private boolean isCheckTopAndBottom = false;
    /**
     * -----------双击放大缩小的变量----------------------------
     **/
    private GestureDetector mGestureDetector;
    private boolean isAutoScale = false;
    private View.OnClickListener clickListener;
    private Context context;

    public ScaleImageView(Context context) {
        super(context);
        init(context, null);
    }

    public ScaleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, null);
    }

    public ScaleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        mScaleMatrix = new Matrix();
        setScaleType(ScaleType.FIT_CENTER);
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        setOnTouchListener(this);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mGestureDetector = new GestureDetector(context, new GestureListener());
    }

    /**
     * 如果在同一个页面使用同一个ScaleImageView显示不同的图片，
     * 则在设置图片之前须先调用此方法
     */
    public void reset() {
        mScaleMatrix = new Matrix();
        setScaleType(ScaleType.FIT_CENTER);
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        setOnTouchListener(this);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mGestureDetector = new GestureDetector(context, new GestureListener());
        initScale = 0.0f;
        maxScale = 0.0f;
        touchSlop = 0;
        lastPointCount = 0;
        mOnce = false;
        isAutoScale = false;
        isCanDrag = false;
        isCheckLeftAndRight = false;
        isCheckTopAndBottom = false;
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
        this.clickListener = l;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event)) return true;
        mScaleGestureDetector.onTouchEvent(event);
        float x = 0.0f;
        float y = 0.0f;
        //获得触控屏幕点的数量
        int pointCount = event.getPointerCount();
        for (int i = 0; i < pointCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }
        x /= pointCount;
        y /= pointCount;
        if (lastPointCount != pointCount) {
            isCanDrag = false;
            lastX = x;
            lastY = y;
        }
        lastPointCount = pointCount;
        RectF rect = getMatrixRectF();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (rect.width() > getWidth() + 0.01 || rect.height() > getHeight() + 0.01) {
                    if (getParent() instanceof ViewPager) {
                        //父控件禁止拦截当前view的触摸事件
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (rect.width() > getWidth() + 0.01 || rect.height() > getHeight() + 0.01) {
                    if (getParent() instanceof ViewPager) {
                        //父控件禁止拦截当前view的触摸事件
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }
                float dx = x - lastX;
                float dy = y - lastY;
                if (!isCanDrag) {
                    isCanDrag = isMoveAction(dx, dy);
                }
                if (isCanDrag) {
                    isCheckLeftAndRight = true;
                    isCheckTopAndBottom = true;
                    if (rect.width() < getWidth()) {
                        isCheckLeftAndRight = false;
                        dx = 0f;
                    }
                    if (rect.height() < getHeight()) {
                        isCheckTopAndBottom = false;
                        dy = 0f;
                    }
                    mScaleMatrix.postTranslate(dx, dy);
                    checkBorderWhenTranslate();
                    setImageMatrix(mScaleMatrix);
                }
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                lastPointCount = 0;
                break;
        }
        return true;
    }

    /**
     * 有没有移动
     */
    private boolean isMoveAction(float dx, float dy) {
        return Math.sqrt((dx * dx + dy * dy)) > touchSlop;
    }

    /**
     * 平移的时候检查边界
     */
    private void checkBorderWhenTranslate() {
        RectF rect = getMatrixRectF();
        float dx = 0.0f;
        float dy = 0.0f;
        if (rect.top > 0 && isCheckTopAndBottom) {
            dy = -rect.top;
        }
        if (rect.bottom < getHeight() && isCheckTopAndBottom) {
            dy = getHeight() - rect.bottom;
        }
        if (rect.left > 0 && isCheckLeftAndRight) {
            dx = -rect.left;
        }
        if (rect.right < getWidth() && isCheckLeftAndRight) {
            dx = getWidth() - rect.right;
        }
        mScaleMatrix.postTranslate(dx, dy);
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getCurrentScale();
        //获得手指触控后的缩放值
        float scaleFactor = detector.getScaleFactor();
        if (getDrawable() == null) return true;
        //放大或缩小的缩放范围控制
        if ((scale < maxScale && scaleFactor > 1.0f) || (scale > initScale && scaleFactor < 1.0f)) {
            if (scale * scaleFactor < initScale) {
                scaleFactor = initScale / scale;
            }
            if (scale * scaleFactor > maxScale) {
                scaleFactor = maxScale / scale;
            }
        }
        mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
        checkBorderWhenScale();
        setImageMatrix(mScaleMatrix);
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    /**
     * 获取ImageView控件加载完成的图片
     */
    @Override
    public void onGlobalLayout() {
        if (mOnce) return;
        Drawable drawable = getDrawable();
        int drawableWidth = drawable.getIntrinsicWidth();
        int drawableHeight = drawable.getIntrinsicHeight();
        float scale = 1.0f;
        if (drawableWidth > getWidth() && drawableHeight < getHeight()) {
            scale = getWidth() * 1.0f / drawableWidth;
        }
        if (drawableWidth < getWidth() && drawableHeight > getHeight()) {
            scale = getHeight() * 1.0f / drawableHeight;
        }
        if ((drawableWidth > getWidth() && drawableHeight > getHeight())) {
            scale = Math.min(getWidth() * 1.0f / drawableWidth, getHeight() * 1.0f / drawableHeight);
        }
        if (drawableWidth < getWidth() && drawableHeight < getHeight()) {
            scale = Math.min(getWidth() * 1.0f / drawableWidth, getHeight() * 1.0f / drawableHeight);
        }
        initScale = scale;
        midScale = scale * 2;
        maxScale = scale * 4;
        int dx = getWidth() / 2 - drawableWidth / 2;
        int dy = getHeight() / 2 - drawableHeight / 2;
        mScaleMatrix.postTranslate(dx, dy);
        mScaleMatrix.postScale(initScale, initScale, ((float) getWidth() / 2), ((float) getHeight() / 2));
        setScaleType(ScaleType.MATRIX);
        setImageMatrix(mScaleMatrix);
        mOnce = true;
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (isAutoScale) return true;
            float x = e.getX();
            float y = e.getY();
            //如果当前的缩放大小小于2倍,放大
            if (getCurrentScale() < midScale) {
                postDelayed(new AutoScaleRunnable(midScale, x, y), 16);
                isAutoScale = true;
            } else {//如果当前缩放大小大于2倍，缩小
                postDelayed(new AutoScaleRunnable(initScale, x, y), 16);
                isAutoScale = true;
            }
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (clickListener != null)
                clickListener.onClick(ScaleImageView.this);
            return true;
        }
    }

    /**
     * 获得当前的缩放大小
     */
    private float getCurrentScale() {
        float[] values = new float[9];
        mScaleMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    /**
     * 自动缩放
     */
    private class AutoScaleRunnable implements Runnable {
        private float targetScale = 0.0f;
        private float x = 0.0f;
        private float y = 0.0f;
        private final float BIGGER = 1.07f;
        private final float SMALL = 0.93f;
        private float tempScale = 0.0f;

        public AutoScaleRunnable(float targetScale, float x, float y) {
            this.targetScale = targetScale;
            this.x = x;
            this.y = y;
        }

        @Override
        public void run() {
            tempScale = (getCurrentScale() < targetScale) ? BIGGER : SMALL;
            mScaleMatrix.postScale(tempScale, tempScale, x, y);
            checkBorderWhenScale();
            setImageMatrix(mScaleMatrix);
            float currentScale = getCurrentScale();
            //继续缩放
            if ((tempScale > 1.0f && currentScale < targetScale) || (tempScale < 1.0f && currentScale > targetScale)) {
                postDelayed(this, 16);
            } else {
                float scale = targetScale / currentScale;
                mScaleMatrix.postScale(scale, scale, x, y);
                checkBorderWhenScale();
                setImageMatrix(mScaleMatrix);
                isAutoScale = false;
            }
        }
    }

    /**
     * 缩放时检查边界及位置控制
     */
    private void checkBorderWhenScale() {
        RectF rect = getMatrixRectF();
        float dx = 0.0f;
        float dy = 0.0f;
        if (rect.width() >= getWidth()) {
            if (rect.left > 0) {
                dx = -rect.left;
            }
            if (rect.right < getWidth()) {
                dx = getWidth() - rect.right;
            }
        }
        if (rect.height() >= getHeight()) {
            if (rect.top > 0) {
                dy = -rect.top;
            }
            if (rect.bottom < getHeight()) {
                dy = getHeight() - rect.bottom;
            }
        }
        if (rect.width() < getWidth()) {
            dx = getWidth() / 2f - rect.right + rect.width() / 2f;
        }
        if (rect.height() < getHeight()) {
            dy = getHeight() / 2f - rect.bottom + rect.height() / 2f;
        }
        mScaleMatrix.postTranslate(dx, dy);
    }

    private RectF getMatrixRectF() {
        RectF rect = new RectF();
        Drawable d = getDrawable();
        if (d != null) {
            rect.set(0f, 0f, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            mScaleMatrix.mapRect(rect);
        }
        return rect;
    }
}

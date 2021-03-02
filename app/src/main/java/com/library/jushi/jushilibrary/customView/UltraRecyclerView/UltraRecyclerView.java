package com.library.jushi.jushilibrary.customView.UltraRecyclerView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.animation.Interpolator;
import android.widget.OverScroller;

import java.lang.reflect.Field;

public class UltraRecyclerView extends RecyclerView {

    private LinearSmoothScroller mSmoothScroller;

    public UltraRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public UltraRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public UltraRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mSmoothScroller = new LinearSmoothScroller(context);
    }

    private void setScrollSpeed(int duration, Interpolator interpolator) {
        try {
            Field mViewFlinger = RecyclerView.class.getDeclaredField("mViewFlinger");
            mViewFlinger.setAccessible(true);
            Class viewFlingerClass = Class.forName("android.support.v7.widget" +
                    ".RecyclerView$ViewFlinger");
            Field mScroller = viewFlingerClass.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            Field mInterpolator = viewFlingerClass.getDeclaredField("mInterpolator");
            mInterpolator.setAccessible(true);
            Field mDecelerateInterpolator = LinearSmoothScroller.class.getDeclaredField(
                    "mDecelerateInterpolator");
            mDecelerateInterpolator.setAccessible(true);
            mInterpolator.set(mViewFlinger.get(this),
                    mDecelerateInterpolator.get(mSmoothScroller));
            if (duration >= 0) {
                UltraOverScroller overScroller = new UltraOverScroller(getContext(), interpolator);
                overScroller.setScrollDuration(duration);
                mScroller.set(mViewFlinger.get(this), overScroller);
            } else {
                OverScroller overScroller = new OverScroller(getContext(), interpolator);
                mScroller.set(mViewFlinger.get(this), overScroller);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void smoothScrollToPosition(int position, int speed, Interpolator interpolator) {
        if (getLayoutManager() != null) {
            mSmoothScroller.setTargetPosition(position);
            setScrollSpeed(speed, interpolator);
            getLayoutManager().startSmoothScroll(mSmoothScroller);
        }
    }

}

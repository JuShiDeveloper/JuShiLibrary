package com.jushi.library.customView.calendarview.listener;

import android.view.View;

import com.jushi.library.customView.calendarview.bean.DateBean;


/**
 * 日期点击接口
 */
public interface OnSingleChooseListener {
    /**
     * @param view
     * @param date
     */
    void onSingleChoose(View view, DateBean date);
}

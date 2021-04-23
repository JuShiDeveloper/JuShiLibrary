package com.jushi.library.customView.calendarview.listener;


import com.jushi.library.customView.calendarview.bean.StartStopBean;

/**
 * 选择起止时间接口
 */
public interface OnStartStopChooseListener {

    /**
     * 选择起止时间
     *
     * @param startDate
     * @param endDate
     */
    void onStartStopChoose(StartStopBean startDate, StartStopBean endDate);
}

package com.jushi.library.customView.calendarview.bean;

import android.view.View;

/**
 * 起止日期
 */
public class StartStopBean {
    private DateBean dateBean;
    private int day;
    private View view;

    public DateBean getDateBean() {
        return dateBean;
    }

    public void setDateBean(DateBean dateBean) {
        this.dateBean = dateBean;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    @Override
    public String toString() {
        return "StartStopBean{" +
                "dateBean=" + dateBean +
                ", day=" + day +
                ", view=" + view +
                '}';
    }
}

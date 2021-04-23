package com.library.jushi.jushilibrary

import android.util.Log
import com.jushi.library.base.BaseFragmentActivity
import com.jushi.library.customView.calendarview.utils.CalendarUtil
import kotlinx.android.synthetic.main.activity_calender_layout.*
import java.util.ArrayList

class CalendarViewActivity : BaseFragmentActivity() {
    override fun getLayoutResId(): Int {
        setSystemBarStatus(true, true, false)
        return R.layout.activity_calender_layout
    }

    override fun initView() {
        val list: MutableList<String> = ArrayList()
        list.add("2021.4.11")
        list.add("2021.4.21")

        val cDate: IntArray = CalendarUtil.getCurrentDate()
        calendar.setStartEndDate("2021.1", "2050.12")
                .setDisableStartEndDate("2021.1.1", "2050.12.12")
                .setInitDate("${cDate[0]}.${cDate[1]}")
                .setSingleDate("${cDate[0]}.${cDate[1]}.${cDate[2]}")
                .setMultiDate(list)
                .init()
        setShowDate(cDate)
    }

    override fun initData() {
    }

    private fun setShowDate(cDate: IntArray) {
        tv_year.text = "${cDate[0]}年"
        tv_month.text = "${cDate[1]}月"
    }

    override fun setListener() {
        calendar.setOnStartStopChooseListener { startDate, endDate ->
            Log.v(
                    "yufei",
                    "start Date = " + startDate.getDateBean().getSolar().get(0)
                            .toString() + "年" + startDate.getDateBean().getSolar().get(1)
                            .toString() + "月" + startDate.getDateBean().getSolar().get(2)
                            .toString() + "日"
            )
            Log.v(
                    "yufei",
                    "end Date = " + endDate.getDateBean().getSolar().get(0)
                            .toString() + "年" + endDate.getDateBean().getSolar().get(1)
                            .toString() + "月" + endDate.getDateBean().getSolar().get(2).toString() + "日"
            )
        }
        last_month.setOnClickListener { v -> calendar.lastMonth() }
        current_month.setOnClickListener { v -> calendar.today() }
        next_month.setOnClickListener { v -> calendar.nextMonth() }
        calendar.setOnPagerChangeListener { date ->
            setShowDate(date)
        }
        tv_year.setOnClickListener { v -> calendar.toSpecifyDate(2022, 1, 1) }
    }
}
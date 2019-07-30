package com.library.jushi.jushilibrary

import android.util.Log
import com.jushi.library.base.BaseFragmentActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseFragmentActivity() {

    override fun getLayoutResId(): Int{
        setSystemBarStatus(true,true)
        return R.layout.activity_main
    }

    override fun initView() {
        setSystemBarViewLayoutParamsL(v_system_bar)
        Log.v("yufei", "activity initView()")
    }

    override fun initData() {
//        Log.v("yufei", DateUtil.compare("2012-12-01 12:12:12", "2012-12-01 12:12:11").toString())
//        Log.v("yufei", DateUtil.getDateYMD(DateUtil.dateCurrentLong()))
//        Log.v("yufei", DateUtil.getDateYMDHMS(DateUtil.dateCurrentLong()))
//        Log.v("yufei", DateUtil.getDateYMDHM(DateUtil.dateCurrentLong()))
//        Log.v("yufei", DateUtil.getDateLong(Date()).toString())
//        Log.v("yufei", DateUtil.getDateLong("2012-12-01 12:12:12").toString())
        Log.v("yufei", "activity initData()")
    }

    override fun setListener() {
        Log.v("yufei", "activity setListener()")
    }

}

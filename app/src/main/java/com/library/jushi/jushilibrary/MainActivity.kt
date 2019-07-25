package com.library.jushi.jushilibrary

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jushi.library.base.BaseFragmentActivity
import com.jushi.library.compression.utils.DateUtil
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : BaseFragmentActivity() {

    override fun getLayoutResId(): Int{
        setSystemBarStatus(true,true)
        return R.layout.activity_main
    }

    override fun initView() {
        setSystemBarViewLayoutParamsL(v_system_bar)
    }

    override fun initData() {
        Log.v("yufei", DateUtil.compare("2012-12-01 12:12:12", "2012-12-01 12:12:11").toString())
        Log.v("yufei", DateUtil.getDateYMD(DateUtil.dateCurrentLong()))
        Log.v("yufei", DateUtil.getDateYMDHMS(DateUtil.dateCurrentLong()))
        Log.v("yufei", DateUtil.getDateYMDHM(DateUtil.dateCurrentLong()))
        Log.v("yufei", DateUtil.getDateLong(Date()).toString())
        Log.v("yufei", DateUtil.getDateLong("2012-12-01 12:12:12").toString())
    }

    override fun setListener() {

    }

}

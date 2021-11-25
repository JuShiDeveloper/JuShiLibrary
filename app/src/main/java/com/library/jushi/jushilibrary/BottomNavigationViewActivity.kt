package com.library.jushi.jushilibrary

import android.support.v4.app.Fragment
import com.jushi.library.base.BaseFragmentActivity
import com.jushi.library.customView.bottomNavgationView.TabInfo
import kotlinx.android.synthetic.main.activity_bottom_navigation_layout.*
import java.util.ArrayList

class BottomNavigationViewActivity : BaseFragmentActivity() {

    private var pages:ArrayList<TabInfo> = arrayListOf()

    override fun getLayoutResId(): Int {
        setSystemBarStatus(true, true, true)
        return R.layout.activity_bottom_navigation_layout
    }

    override fun initView() {

    }

    override fun initData() {
        pages.add(TabInfo("TAB1",TestFragment(),R.drawable.home_selector))
        pages.add(TabInfo("TAB2",TestFragment(),R.drawable.workbench_selector))
        pages.add(TabInfo("",TestFragment(),0))
        pages.add(TabInfo("TAB4",TestFragment(),R.drawable.mine_selector))
        pages.add(TabInfo("TAB5",TestFragment(),R.drawable.report_selector))
        bottomNavigationView.initViewInfo(supportFragmentManager,pages,R.id.fragment_content)
    }

    override fun setListener() {
        bottomNavigationView.setOnTabSelectListener { index,tabText ->
            var text = "点击Fragment $tabText"
            if (index==2){
                text = "点击Fragment TAB3"
            }
            (pages[index].fragment as TestFragment).setText(text)
        }
    }
}

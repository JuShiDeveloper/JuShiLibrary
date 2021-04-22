package com.library.jushi.jushilibrary

import android.support.v4.app.Fragment
import com.jushi.library.base.BaseFragmentActivity
import kotlinx.android.synthetic.main.activity_bottom_navigation_layout.*
import java.util.ArrayList

class BottomNavigationViewActivity : BaseFragmentActivity() {

    private val fragmentArray = ArrayList<Fragment>()
    override fun getLayoutResId(): Int {
        setSystemBarStatus(true, true, true)
        return R.layout.activity_bottom_navigation_layout
    }

    override fun initView() {
        fragmentArray.add(TestFragment())
        fragmentArray.add(TestFragment())
        fragmentArray.add(TestFragment())
        fragmentArray.add(TestFragment())
        bottomNavigationView.setFragmentLayoutId(R.id.fragment_content)
        bottomNavigationView.setFragmentsArray(supportFragmentManager, fragmentArray)
    }

    override fun initData() {
    }

    override fun setListener() {
        bottomNavigationView.setOnTabSelectListener { index ->
            (fragmentArray[index] as TestFragment).setText("点击Fragment $index")
        }
    }
}

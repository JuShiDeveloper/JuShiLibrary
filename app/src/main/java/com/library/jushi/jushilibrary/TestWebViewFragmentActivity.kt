package com.library.jushi.jushilibrary

import android.annotation.SuppressLint
import com.jushi.library.base.BaseFragmentActivity

/**
 * 测试使用BaseWebViewFragment示例的页面
 */
class TestWebViewFragmentActivity: BaseFragmentActivity() {

    val fragment = WebViewFragment()
    override fun getLayoutResId(): Int {
        setSystemBarStatus(true,true,true)
        return R.layout.activity_test_webview_fragment_layout
    }

    @SuppressLint("CommitTransaction")
    override fun initView() {
        val tran = supportFragmentManager.beginTransaction()
        tran.add(R.id.frameLayout,fragment)
        tran.commit()
    }

    override fun initData() {
    }

    override fun setListener() {
    }

    override fun onBackPressed() {
        if (fragment.canBackPressed()){
            super.onBackPressed()
        }
    }
}
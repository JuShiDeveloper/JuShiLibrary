package com.library.jushi.jushilibrary

import com.jushi.library.base.BaseWebViewFragment

/**
 * BaseWebViewFragment使用示例
 */
class WebViewFragment: BaseWebViewFragment() {

    override fun onJavascriptInterfaceName(): String = "Android"

    override fun onJavascriptInterfaceObject(): Any = this

    override fun onWebUrl(): String {
        return "http://www.baidu.com"
    }
    override fun needShowProgressBar(): Boolean = true

    override fun showNavigationBar(): Boolean = true

}
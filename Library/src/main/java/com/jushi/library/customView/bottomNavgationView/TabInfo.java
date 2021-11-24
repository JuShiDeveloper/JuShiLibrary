package com.jushi.library.customView.bottomNavgationView;

import android.support.v4.app.Fragment;

/**
 * 底部tab信息类
 */
public class TabInfo {
    private String tabText; //tab文字信息
    private Fragment fragment; //对应的fragment页面
    private int drawableResId; //tab图标 drawable xml文件id （xml包含选中和未选中图标）

    public TabInfo(String tabText, Fragment fragment, int drawableResId) {
        this.tabText = tabText;
        this.fragment = fragment;
        this.drawableResId = drawableResId;
    }

    public String getTabText() {
        return tabText;
    }

    public void setTabText(String tabText) {
        this.tabText = tabText;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public int getDrawableResId() {
        return drawableResId;
    }

    public void setDrawableResId(int drawableResId) {
        this.drawableResId = drawableResId;
    }

}

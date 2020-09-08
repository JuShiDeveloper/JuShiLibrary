package com.library.jushi.jushilibrary;

import com.jushi.library.base.BaseFragmentActivity;
import com.jushi.library.customView.navigationbar.NavigationBar;
import com.jushi.library.viewinject.FindViewById;

public class NavigationBarActivity extends BaseFragmentActivity {
    @FindViewById(R.id.NavigationBar_5)
    private NavigationBar navigationBar5;

    @FindViewById(R.id.NavigationBar_6)
    private NavigationBar navigationBar6;

    @FindViewById(R.id.NavigationBar_8)
    private NavigationBar navigationBar8;

    @FindViewById(R.id.NavigationBar_9)
    private NavigationBar navigationBar9;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_navigation_bar_layout;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        navigationBar5.setOnBackButtonClickListener(v -> {
            showToast("返回按钮被点击");
        });
        navigationBar5.setOnCloseButtonListener(v -> {
            showToast("关闭按钮被点击");
        });
        navigationBar5.setOnRightButtonListener(v -> {
            showToast("右边按钮被点击");
        });

        navigationBar6.setOnBackButtonClickListener(v -> {
            showToast("返回按钮被点击");
        });
        navigationBar6.setOnCloseButtonListener(v -> {
            showToast("关闭按钮被点击");
        });
        navigationBar6.setOnRightButtonListener(v -> {
            showToast("右边按钮被点击");
        });

        navigationBar8.setOnSearchEditClickListener(v -> {
            showToast("点击搜索框");
        });

        navigationBar9.setOnSearchEditClickListener(v -> {
            showToast("点击搜索框，setEnabled为true，点击事件不生效");
        });
    }
}

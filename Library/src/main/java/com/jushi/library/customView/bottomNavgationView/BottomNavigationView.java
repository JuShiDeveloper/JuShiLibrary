package com.jushi.library.customView.bottomNavgationView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jushi.library.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 底部tab导航栏 用于在页面底部显示  首页、消息、资讯、我的等5个tab
 * created by wyf on 2021-11-24
 */
public class BottomNavigationView extends LinearLayout {
    private OnTabSelectListener onTabSelectListener;
    private int fragmentLayoutId;
    private FragmentManager fragmentManager;
    private Fragment currentFragment;
    private int tabTextSelectColor;
    private int tabTextUnSelectColor;
    private int textSize;
    private List<TabView> tabViews = new ArrayList<>();
    private List<TabInfo> tabInfos;
    private int currentIndex = 0;

    public BottomNavigationView(Context context) {
        this(context, null);
    }

    public BottomNavigationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        initView();
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BottomNavigationView);
        setTabSelectTextColor(array.getColor(R.styleable.BottomNavigationView_tab_select_text_color, Color.BLACK));
        setTabUnSelectTextColor(array.getColor(R.styleable.BottomNavigationView_tab_unselect_text_color, Color.GRAY));
        textSize = array.getDimensionPixelSize(R.styleable.BottomNavigationView_tab_text_size, 30);
    }

    private void setTabUnSelectTextColor(int color) {
        this.tabTextUnSelectColor = color;
    }

    private void setTabSelectTextColor(int color) {
        this.tabTextSelectColor = color;
    }

    private void initView() {
        setOrientation(HORIZONTAL);
        setPadding(0, 0, 0, 20);
    }

    private void setListener() {
        for (int i = 0; i < tabViews.size(); i++) {
            int finalI = i;
            tabViews.get(i).setOnClickListener(v -> {
                setSelectTab(finalI);
            });
        }
    }

    /**
     * 设置当前选中的tab
     *
     * @param index
     */
    public void setCurrentIndex(int index) {
        currentIndex = index;
        setSelectTab(index);
    }

    private void setSelectTab(int index) {
        if (tabInfos == null || tabViews.size() == 0) return;
        if (onTabSelectListener != null)
            onTabSelectListener.onTabSelect(index, tabViews.get(index).getText());
        for (int i = 0; i < tabViews.size(); i++) {
            tabViews.get(i).setSelected(index == i);
            tabViews.get(i).setTextColor(index == i ? tabTextSelectColor : tabTextUnSelectColor);
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (currentFragment == null) {
            transaction.add(fragmentLayoutId, tabInfos.get(index).getFragment()).commit();
        }
        if (currentFragment != null) {
            if (!tabInfos.get(index).getFragment().isAdded()) {
                transaction.hide(currentFragment).add(fragmentLayoutId, tabInfos.get(index).getFragment()).commit();
            } else {
                transaction.hide(currentFragment).show(tabInfos.get(index).getFragment()).commit();
            }
        }
        currentFragment = tabInfos.get(index).getFragment();
    }

    /**
     * 初始化地步tab按钮
     *
     * @param fragmentManager  FragmentManager
     * @param pages            页面信息
     * @param fragmentLayoutId 显示fragment的布局容器id
     */
    public void initViewInfo(FragmentManager fragmentManager, List<TabInfo> pages, int fragmentLayoutId) {
        this.tabInfos = pages;
        this.fragmentLayoutId = fragmentLayoutId;
        this.fragmentManager = fragmentManager;
        tabViews.clear();
        removeAllViews();
        initTabView();
        setSelectTab(currentIndex);
        setListener();
    }

    private void initTabView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        params.gravity = Gravity.CENTER;
        for (TabInfo tabInfo : tabInfos) {
            TabView tabView = new TabView(getContext());
            tabView.setLayoutParams(params);
            tabView.setTextSize(textSize);
            tabView.setTextColor(tabTextUnSelectColor);
            tabView.setText(tabInfo.getTabText());
            tabView.setTabIconDrawableRes(tabInfo.getDrawableResId());
            tabViews.add(tabView);
            addView(tabView);
        }
    }

    public void setOnTabSelectListener(OnTabSelectListener onTabSelectListener) {
        this.onTabSelectListener = onTabSelectListener;
    }

    public interface OnTabSelectListener {
        void onTabSelect(int index, String tabText);
    }

}

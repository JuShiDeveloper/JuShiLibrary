package com.jushi.library.customView.bottomNavgationView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jushi.library.R;

import java.util.ArrayList;
import java.util.List;

public class BottomNavigationView extends RelativeLayout {

    private List<LinearLayout> tabs;
    private OnTabSelectListener onTabSelectListener;
    private int fragmentLayoutId;
    private List<Fragment> fragmentList;
    private FragmentManager fragmentManager;
    private Fragment curremtFragment;
    private int tabTextSelectColor;
    private int tabTextUnSelectColor;

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
        setListener();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BottomNavigationView);
        setTabSelectTextColor(array.getColor(R.styleable.BottomNavigationView_tab_select_text_color, Color.BLACK));
        setTabUnSelectTextColor(array.getColor(R.styleable.BottomNavigationView_tab_unselect_text_color, Color.GRAY));
        setTabTextSize(array.getDimensionPixelSize(R.styleable.BottomNavigationView_tab_text_size, 30));
    }

    private void setTabTextSize(int dimensionPixelSize) {
        for (int i = 0; i < tabs.size(); i++) {
            ((TextView) tabs.get(i).getChildAt(1)).setTextSize(TypedValue.COMPLEX_UNIT_PX, dimensionPixelSize);
        }
    }

    private void setTabUnSelectTextColor(int color) {
        this.tabTextUnSelectColor = color;
    }

    private void setTabSelectTextColor(int color) {
        this.tabTextSelectColor = color;
    }

    private void initView() {
        View.inflate(getContext(), R.layout.view_bottom_navigation_layout, this);
        tabs = new ArrayList<>();
        tabs.add(findViewById(R.id.ll_tab_1));
        tabs.add(findViewById(R.id.ll_tab_2));
        tabs.add(findViewById(R.id.ll_tab_3));
        tabs.add(findViewById(R.id.ll_tab_4));
    }

    private void setListener() {
        for (int i = 0; i < tabs.size(); i++) {
            int finalI = i;
            tabs.get(i).setOnClickListener(v -> {
                setSelectTab(finalI);
            });
        }
    }

    public void setSelectTab(int index) {
        if (onTabSelectListener != null)
            onTabSelectListener.onTabSelect(index);
        for (int i = 0; i < tabs.size(); i++) {
            tabs.get(i).getChildAt(0).setSelected(index == i);
            ((TextView) tabs.get(i).getChildAt(1)).setTextColor(index == i ? tabTextSelectColor : tabTextUnSelectColor);
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (curremtFragment == null) {
            transaction.add(fragmentLayoutId, fragmentList.get(index)).commit();
        }
        if (curremtFragment != null) {
            if (!fragmentList.get(index).isAdded()) {
                transaction.hide(curremtFragment).add(fragmentLayoutId, fragmentList.get(index)).commit();
            } else {
                transaction.hide(curremtFragment).show(fragmentList.get(index)).commit();
            }
        }
        curremtFragment = fragmentList.get(index);
    }

    public void setFragmentLayoutId(int fragmentLayoutId) {
        this.fragmentLayoutId = fragmentLayoutId;
    }

    public void setFragmentsArray(FragmentManager fragmentManager, List<Fragment> fragmentsArray) {
        this.fragmentManager = fragmentManager;
        fragmentList = fragmentsArray;
        setSelectTab(0);
    }

    public void setOnTabSelectListener(OnTabSelectListener onTabSelectListener) {
        this.onTabSelectListener = onTabSelectListener;
    }

    public interface OnTabSelectListener {
        void onTabSelect(int index);
    }

}

package com.jushi.library.customView.bottomNavgationView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jushi.library.R;

/**
 * 底部tab视图
 */
class TabView extends LinearLayout {

    private ImageView tabIcon;
    private TextView tabText;

    public TabView(Context context) {
        super(context);
        init(null, 0);
    }

    public TabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public TabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        View.inflate(getContext(), R.layout.tab_view_layout, this);
        tabIcon = findViewById(R.id.iv_tab_icon);
        tabText = findViewById(R.id.tv_tab_text);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        tabIcon.setSelected(selected);
    }

    public void setTextColor(int color){
        tabText.setTextColor(color);
    }

    public String getText(){
        return tabText.getText().toString();
    }

    public void setTextSize(int size){
        tabText.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    public void setText(String text){
        tabText.setText(text);
    }

    /**
     * 设置tab的图标
     * @param drawableResId  包含选中与未选中状态icon的drawable xml文件资源id
     */
    public void setTabIconDrawableRes(int drawableResId){
        tabIcon.setBackgroundResource(drawableResId);
    }
}

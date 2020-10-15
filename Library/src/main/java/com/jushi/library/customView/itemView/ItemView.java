package com.jushi.library.customView.itemView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jushi.library.R;

/**
 * 自定义封装的选项卡view
 * 功能属性：
 * 1、设置左边图标；
 * 2、设置选项卡标题；
 * 3、设置右边图标；
 * 4、设置右边文案。
 * <p> 例如在个人中心界面 设置、安全、关于等功能可使用该封装控件
 * create by jushi on 2019/05/06
 */
public class ItemView extends RelativeLayout {

    private ImageView ivLeftIcon;
    private ImageView ivRightIcon;
    private TextView tvTitle;
    private TextView tvRightText;

    public ItemView(Context context) {
        this(context, null);
    }

    public ItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(attrs);
    }

    private void initialize(AttributeSet attrs) {
        initView();
        initAttrs(attrs);
    }

    private void initView() {
        setClickable(true);
        setFocusable(true);
        View.inflate(getContext(), R.layout.view_item_layout, this);
        ivLeftIcon = findViewById(R.id.item_view_drawable_left);
        ivRightIcon = findViewById(R.id.item_view_drawable_right);
        tvTitle = findViewById(R.id.item_view_title);
        tvRightText = findViewById(R.id.item_view_content);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.ItemView);
        setLeftIcon(array.getResourceId(R.styleable.ItemView_itemLeftIcon, 0));
        setRightIcon(array.getResourceId(R.styleable.ItemView_itemRightIcon, 0));
        setItemTitleText(array.getString(R.styleable.ItemView_itemTitleText));
        setItemTitleTextColor(array.getColor(R.styleable.ItemView_itemTitleTextColor, Color.BLACK));
        setItemTitleTextSize(array.getDimensionPixelSize(R.styleable.ItemView_itemTitleTextSize, 24));
        setItemRightText(array.getString(R.styleable.ItemView_itemRightText));
        setItemRightTextColor(array.getColor(R.styleable.ItemView_itemRightTextColor, Color.parseColor("#333333")));
        setItemRightTextSize(array.getDimensionPixelSize(R.styleable.ItemView_itemRightTextSize, 24));
        array.recycle();
    }

    public void setItemRightTextSize(int size) {
        tvRightText.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    public void setItemRightTextColor(int color) {
        tvRightText.setTextColor(color);
    }

    public void setItemRightText(String text) {
        tvRightText.setText(text);
    }

    public void setItemTitleTextSize(int size) {
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    public void setItemTitleTextColor(int color) {
        tvTitle.setTextColor(color);
    }

    public void setItemTitleText(String string) {
        tvTitle.setText(string);
    }

    public void setRightIcon(int resourceId) {
        ivRightIcon.setImageResource(resourceId);
    }

    public void setRightIcon(Bitmap bitmap) {
        ivRightIcon.setImageBitmap(bitmap);
    }

    public void setLeftIcon(int resourceId) {
        ivLeftIcon.setImageResource(resourceId);
    }

    public void setLeftIcon(Bitmap bitmap) {
        ivLeftIcon.setImageBitmap(bitmap);
    }

}

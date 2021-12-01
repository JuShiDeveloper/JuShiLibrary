package com.jushi.library.customView.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jushi.library.R;
import com.jushi.library.customView.statusBarView.StatusBarView;
import com.jushi.library.utils.LogUtil;

/**
 * 自定义页面导航栏
 * 功能：
 * 1、返回上一个页面按钮；
 * 2、关闭当前页面按钮（文字/图标）；
 * 3、页面标题显示；
 * 4、搜索框功能；
 * 5、右边功能按钮（文字/图标）；
 * create by wyf on 2020/09/07
 */
public class NavigationBar extends LinearLayout implements ViewTreeObserver.OnGlobalLayoutListener {

    private LinearLayout llBack;
    private ImageView ivBackBtn;
    private TextView tvCloseBtn;
    private ImageView ivCloseBtn;
    private TextView tvTitle;
    private TextView tvRightBtn;
    private ImageView ivRightBtn;
    private FrameLayout flCloseBtnContainer;
    private FrameLayout flRightBtnContainer;
    private EditText editText;
    private FrameLayout flTitleContainer;

    private int function = -1;
    public static final int NONE = 0;
    public static final int FUNCTION_BACK_BUTTON = 2;
    public static final int FUNCTION_LEFT_CLOSE_TITLE = 4;
    public static final int FUNCTION_LEFT_CLOSE_ICON = 8;
    public static final int FUNCTION_TITLE = 16;
    public static final int FUNCTION_RIGHT_TEXT_BUTTON = 32;
    public static final int FUNCTION_RIGHT_ICON_BUTTON = 64;
    public static final int FUNCTION_SEARCH = 128;

    private boolean canEditClick = false;

    private View.OnClickListener backButtonClickListener;
    private View.OnClickListener closeButtonClickListener;
    private View.OnClickListener rightButtonClickListener;
    private View.OnClickListener searchEditClickListener;

    public NavigationBar(Context context) {
        this(context, null);
    }

    public NavigationBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(attrs);
    }

    private void initialize(AttributeSet attrs) {
        initView();
        setClickListener();
        initAttribute(attrs);
    }

    private void initView() {
        setOrientation(LinearLayout.VERTICAL);
        View.inflate(getContext(), R.layout.view_navigation_bar_layout, this);
        llBack = findViewById(R.id.ll_navigation_bar_back);
        ivBackBtn = findViewById(R.id.iv_navigation_bar_back_btn);
        tvCloseBtn = findViewById(R.id.tv_navigation_bar_text_close);
        ivCloseBtn = findViewById(R.id.iv_navigation_bar_icon_close);
        tvTitle = findViewById(R.id.tv_navigation_bar_title);
        tvRightBtn = findViewById(R.id.tv_navigation_bar_right_text_btn);
        ivRightBtn = findViewById(R.id.iv_navigation_bar_right_icon_btn);
        flCloseBtnContainer = findViewById(R.id.fl_navigation_bar_close_container);
        flRightBtnContainer = findViewById(R.id.fl_navigation_bar_right_btn_container);
        editText = findViewById(R.id.et_navigation_bar_text);
        flTitleContainer = findViewById(R.id.fl_title_container);
    }

    private void setClickListener() {
        getViewTreeObserver().addOnGlobalLayoutListener(this);
        llBack.setOnClickListener(v -> navigationBarBackButtonClick(v));
        flCloseBtnContainer.setOnClickListener(v -> navigationBarCloseButtonClick(v));
        flRightBtnContainer.setOnClickListener(v -> navigationBarRightButtonClick(v));
        editText.setOnClickListener(v -> searchEditViewClick(v));
    }

    private void initAttribute(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.NavigationBar);
        setFunction(array.getInt(R.styleable.NavigationBar_function, FUNCTION_BACK_BUTTON | FUNCTION_TITLE));
        setBackButtonImageResource(array.getResourceId(R.styleable.NavigationBar_backButtonIconResource, 0));
        setCloseButtonText(array.getString(R.styleable.NavigationBar_closeButtonText));
        setCloseButtonImageResource(array.getResourceId(R.styleable.NavigationBar_closeButtonIconResource, 0));
        setTitleText(array.getString(R.styleable.NavigationBar_titleText));
        setCloseButtonTextColor(array.getColor(R.styleable.NavigationBar_closeButtonTextColor, Color.GRAY));
        setTitleTextColor(array.getColor(R.styleable.NavigationBar_titleTextColor, Color.parseColor("#333333")));
        setCloseButtonTextSize(array.getDimensionPixelSize(R.styleable.NavigationBar_closeButtonTextSize, 24));
        setTitleTextSize(array.getDimensionPixelSize(R.styleable.NavigationBar_titleTextSize, 24));
        setRightButtonText(array.getString(R.styleable.NavigationBar_rightButtonText));
        setRightButtonTextColor(array.getColor(R.styleable.NavigationBar_rightButtonTextColor, Color.GRAY));
        setRightButtonTextSize(array.getDimensionPixelSize(R.styleable.NavigationBar_rightButtonTextSize, 24));
        setRightButtonImageResource(array.getResourceId(R.styleable.NavigationBar_rightButtonIconResource, 0));
        setSearchBackgroundResource(array.getResourceId(R.styleable.NavigationBar_searchBackgroundResource, 0));
        setSearchHint(array.getString(R.styleable.NavigationBar_searchHint));
        setSearchHintColor(array.getColor(R.styleable.NavigationBar_searchHintColor, Color.parseColor("#c4c7cc")));
        setSearchTextSize(array.getDimensionPixelSize(R.styleable.NavigationBar_searchTextSize, 24));
        setSearchTextColor(array.getColor(R.styleable.NavigationBar_searchTextColor, Color.BLACK));
        setSearchEditEnable(array.getBoolean(R.styleable.NavigationBar_searchEditEnable, false));
        setSearchEditFocusable(array.getBoolean(R.styleable.NavigationBar_searchEditFocusable, false));
        boolean isImmersiveStatusBar = array.getBoolean(R.styleable.NavigationBar_isImmersiveStatusBar, false);
        int statusBarColor = array.getColor(R.styleable.NavigationBar_statusBarBackgroundColor, Color.TRANSPARENT);
        initStatusBarView(isImmersiveStatusBar, statusBarColor);
        array.recycle();
    }

    @Override
    public void onGlobalLayout() {//让标题居中显示
        int paddingWidth = Math.max(llBack.getWidth()+flCloseBtnContainer.getWidth(),flRightBtnContainer.getWidth());
        int offset = ((llBack.getWidth()+flCloseBtnContainer.getWidth())-flRightBtnContainer.getWidth())/2;
        flTitleContainer.setPadding(paddingWidth+offset,0,paddingWidth-offset,0);
    }

    /**
     * 初始化
     *
     * @param isImmersiveStatusBar 是否沉浸式状态栏
     * @param color                状态栏背景色
     */
    private void initStatusBarView(boolean isImmersiveStatusBar, int color) {
        if (!isImmersiveStatusBar) return;
        StatusBarView statusBar = new StatusBarView(getContext());
        addView(statusBar, 0);
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    private int getStatusBarHeight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        return resourceId > 0 ? getResources().getDimensionPixelSize(resourceId) : 0;
    }

    private void setFunction(int function) {
        if (this.function == function) return;
        this.function = function;
        ivBackBtn.setVisibility(isAddFunction(FUNCTION_BACK_BUTTON) ? VISIBLE : GONE);
        ivCloseBtn.setVisibility(isAddFunction(FUNCTION_LEFT_CLOSE_ICON) ? VISIBLE : GONE);
        //同时设置关闭按钮文字及图标时，图标优先
        tvCloseBtn.setVisibility(!isAddFunction(FUNCTION_LEFT_CLOSE_ICON) && isAddFunction(FUNCTION_LEFT_CLOSE_TITLE) ? VISIBLE : GONE);
        tvTitle.setVisibility(isAddFunction(FUNCTION_TITLE) ? VISIBLE : GONE);
        ivRightBtn.setVisibility(isAddFunction(FUNCTION_RIGHT_ICON_BUTTON) ? VISIBLE : GONE);
        //同时设置右边按钮文字及图标时，图标优先
        tvRightBtn.setVisibility(!isAddFunction(FUNCTION_RIGHT_ICON_BUTTON) && isAddFunction(FUNCTION_RIGHT_TEXT_BUTTON) ? VISIBLE : GONE);
        //同时设置标题及搜索功能时，标题优先
        editText.setVisibility(!isAddFunction(FUNCTION_TITLE) && isAddFunction(FUNCTION_SEARCH) ? VISIBLE : GONE);
    }

    private boolean isAddFunction(int function) {
        return (this.function & function) == function;
    }

    /**
     * 添加某个功能
     *
     * @param function
     */
    public void addFunction(int function) {
        setFunction(this.function | function);
    }

    /**
     * 移除某个功能
     *
     * @param function
     */
    public void removeFunction(int function) {
        setFunction(this.function & (~function));
    }

    /**
     * 设置返回按钮图片资源文件
     *
     * @param resId
     */
    public void setBackButtonImageResource(int resId) {
        ivBackBtn.setImageResource(resId);
    }

    /**
     * 设置关闭按钮文字
     *
     * @param text
     */
    public void setCloseButtonText(String text) {
        tvCloseBtn.setText(text);
    }

    /**
     * 设置关闭按钮文字颜色
     *
     * @param color
     */
    public void setCloseButtonTextColor(int color) {
        tvCloseBtn.setTextColor(color);
    }

    /**
     * 设置关闭按钮文字大小
     *
     * @param size
     */
    public void setCloseButtonTextSize(int size) {
        tvCloseBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    /**
     * 设置关闭按钮图片资源文件
     *
     * @param resId
     */
    public void setCloseButtonImageResource(int resId) {
        ivCloseBtn.setImageResource(resId);
    }

    /**
     * 设置标题
     *
     * @param text
     */
    public void setTitleText(String text) {
        tvTitle.setText(text);
    }

    /**
     * 设置标题文字颜色
     *
     * @param color
     */
    public void setTitleTextColor(int color) {
        tvTitle.setTextColor(color);
    }

    /**
     * 设置标题文字大小
     *
     * @param size
     */
    public void setTitleTextSize(int size) {
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    /**
     * 设置右边按钮文字
     *
     * @param text
     */
    public void setRightButtonText(String text) {
        tvRightBtn.setText(text);
    }

    /**
     * 设置右边按钮文字颜色
     *
     * @param color
     */
    public void setRightButtonTextColor(int color) {
        tvRightBtn.setTextColor(color);
    }

    /**
     * 设置右边按钮文字大小
     *
     * @param size
     */
    public void setRightButtonTextSize(int size) {
        tvRightBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    /**
     * 设置右边按钮图片资源文件
     *
     * @param resId
     */
    public void setRightButtonImageResource(int resId) {
        ivRightBtn.setImageResource(resId);
    }

    /**
     * 设置中间搜索框背景
     *
     * @param resId
     */
    public void setSearchBackgroundResource(int resId) {
        editText.setBackgroundResource(resId);
    }

    /**
     * 设置搜索框提示文案
     *
     * @param hint
     */
    public void setSearchHint(String hint) {
        editText.setHint(hint);
    }

    /**
     * 设置搜索框提示文案颜色
     *
     * @param color
     */
    public void setSearchHintColor(int color) {
        editText.setHintTextColor(color);
    }

    /**
     * 设置搜索框输入文字大小
     *
     * @param size
     */
    public void setSearchTextSize(int size) {
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    /**
     * 设置搜索框输入文字大小
     *
     * @param color
     */
    public void setSearchTextColor(int color) {
        editText.setTextColor(color);
    }

    /**
     * 设置是否启用搜索输入框编辑
     *
     * @param enable
     */
    public void setSearchEditEnable(boolean enable) {
        canEditClick = !enable;
        if (canEditClick) return;
        editText.setEnabled(enable);
    }

    /**
     * 设置搜索输入框是否获得焦点
     *
     * @param focusable
     */
    public void setSearchEditFocusable(boolean focusable) {
        editText.setFocusable(focusable);
    }

    /**
     * 返回按钮点击事件
     *
     * @param v
     */
    private void navigationBarBackButtonClick(View v) {
        if (backButtonClickListener != null) {
            backButtonClickListener.onClick(v);
            return;
        }
        try {
            ((Activity) getContext()).onBackPressed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭按钮点击事件
     *
     * @param v
     */
    private void navigationBarCloseButtonClick(View v) {
        if (closeButtonClickListener != null) {
            closeButtonClickListener.onClick(v);
            return;
        }
        try {
            ((Activity) getContext()).onBackPressed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 右边按钮点击事件
     *
     * @param v
     */
    private void navigationBarRightButtonClick(View v) {
        if (rightButtonClickListener == null) return;
        rightButtonClickListener.onClick(v);
    }

    /**
     * 搜索框被点击，只有在 setSearchEditEnable 为false时才生效
     *
     * @param v
     */
    private void searchEditViewClick(View v) {
        if (!canEditClick || searchEditClickListener == null) return;
        searchEditClickListener.onClick(v);
    }

    /**
     * 获取输入的内容
     * xml中function为 SEARCH 并且searchEditEnable="true" 时，通过该方法获取输入的内容
     * @return
     */
    public String getInputText(){
        return editText.getText().toString();
    }

    public void setOnBackButtonClickListener(OnClickListener backButtonClickListener) {
        this.backButtonClickListener = backButtonClickListener;
    }

    public void setOnCloseButtonClickListener(OnClickListener closeButtonListener) {
        this.closeButtonClickListener = closeButtonListener;
    }

    public void setOnRightButtonClickListener(OnClickListener rightButtonListener) {
        this.rightButtonClickListener = rightButtonListener;
    }

    public void setOnSearchEditClickListener(OnClickListener searchEditClickListener) {
        this.searchEditClickListener = searchEditClickListener;
    }

}

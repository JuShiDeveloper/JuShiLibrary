package com.jushi.library.customView.editText;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jushi.library.R;

/**
 * 自定义输入框
 * 适用于注册、登录、密码找回等账号相关的页面，
 * 包含清除按钮、获取验证码按钮、密码显示与隐藏按钮
 * <p>
 * create by wyf on 2019/08/15
 */
public class CustomEditText extends RelativeLayout implements View.OnClickListener, TextWatcher {
    /**
     * 输入框
     */
    private EditText editText;
    /**
     * 清除内容按钮
     */
    private ImageView clearBtn;
    /**
     * 显示或隐藏密码按钮
     */
    private ImageView showPswBtn;
    /**
     * 获取验证码的文字按钮
     */
    private TextView authCodeBtn;

    private OnAuthCodeButtonClickListener authCodeButtomClickListener;
    private OnTextChangedListener onTextChangedListener;
    private int customEditTextType = -1;

    enum PasswordDisplayType {
        HIDDEN, DISPLAY
    }

    private PasswordDisplayType passwordDisplayType = PasswordDisplayType.DISPLAY;

    private int passwordHiddenResId = 0; //隐藏密码图片资源id
    private int passwordDisplayResid = 0; //显示密码图片资源id
    private int defaultTextSize = 42; //输入文字的默认大小,对应xml中设置的 14sp

    public CustomEditText(Context context) {
        this(context, null);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(attrs);
    }

    private void initialize(AttributeSet attrs) {
        initView();
        initAttrs(attrs);
        setListener();
        setClearButtonVisible(false);
    }

    private void initView() {
        View.inflate(getContext(), R.layout.custom_edittext_layout, this);
        editText = findViewById(R.id.et_input_content);
        clearBtn = findViewById(R.id.iv_clear_btn);
        showPswBtn = findViewById(R.id.iv_is_show_view);
        authCodeBtn = findViewById(R.id.tv_auth_code_btn);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomEditText);
        setEditTextDigits(typedArray.getString(R.styleable.CustomEditText_digits));
        setDrawableLeft(typedArray.getResourceId(R.styleable.CustomEditText_drawableLeft, 0));
        setHintText(typedArray.getString(R.styleable.CustomEditText_hint));
        setClearButtonDrawable(typedArray.getResourceId(R.styleable.CustomEditText_srcClearButton, 0));
        passwordHiddenResId = typedArray.getResourceId(R.styleable.CustomEditText_srcPasswordHidden, 0);
        passwordDisplayResid = typedArray.getResourceId(R.styleable.CustomEditText_srcPasswordDisplay, 0);
        setShowPasswordButtonDrawable(passwordHiddenResId);
        setAuthCodeButtonText(typedArray.getString(R.styleable.CustomEditText_authCodeHint));
        setAuthCodeButtonTextColor(typedArray.getColor(R.styleable.CustomEditText_authCodeTextColor, Color.BLUE));
        setAuthCodeButtonTextSize(typedArray.getDimensionPixelSize(R.styleable.CustomEditText_authCodeTextSize, defaultTextSize));
        setInputTextColor(typedArray.getColor(R.styleable.CustomEditText_inputTextColor, Color.BLACK));
        setHintTextColor(typedArray.getColor(R.styleable.CustomEditText_hintTextColor, Color.LTGRAY));
        setInputTextSize(typedArray.getDimensionPixelSize(R.styleable.CustomEditText_inputTextSize, defaultTextSize));
        setMaxLength(typedArray.getInt(R.styleable.CustomEditText_maxLength, 20));
        setAuthCodeButtonBackgroundColor(typedArray.getColor(R.styleable.CustomEditText_authCodeBackgroundColor, Color.TRANSPARENT));
        setAuthCodeButtonBackgroundResource(typedArray.getResourceId(R.styleable.CustomEditText_authCodeBackgroundResource, 0));
        setShowPasswordButtonVisible(typedArray.getBoolean(R.styleable.CustomEditText_isShowAuthCodeBtn, false));
        setEditTextBackgroundClor(typedArray.getColor(R.styleable.CustomEditText_editTextBackgroundColor, Color.parseColor("#bebebe")));
        setEditTextBackgroundResource(typedArray.getResourceId(R.styleable.CustomEditText_editTextBackgroundResource, 0));
        typedArray.recycle();
    }

    private void setListener() {
        clearBtn.setOnClickListener(this);
        showPswBtn.setOnClickListener(this);
        authCodeBtn.setOnClickListener(this);
        editText.addTextChangedListener(this);
    }

    /**
     * 设置清除按钮是否可见
     *
     * @param visible true-可见，false-不可见
     */
    private void setClearButtonVisible(boolean visible) {
        clearBtn.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * 设置获取验证码按钮是否可见（默认不可见，在设置了按钮点击事件监听后可见）
     *
     * @param visible true-可见，false-不可见
     */
    private void setAuthCodeButtonVisible(boolean visible) {
        authCodeBtn.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置显示/隐藏密码的按钮是否可见(默认不可见)
     *
     * @param visible true-可见，false-不可见
     */
    public void setShowPasswordButtonVisible(boolean visible) {
        showPswBtn.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置编辑框输入类型
     *
     * @param inputType InputType类中的值 （传入多个使用 | 隔开）
     */
    public void setEditTextInputType(int inputType) {
        editText.setInputType(inputType);
    }

    /**
     * 设置输入框可输入的字符
     *
     * @param digits
     */
    public void setEditTextDigits(String digits) {
        if (digits == null || digits.equals("")) return;
        editText.setKeyListener(DigitsKeyListener.getInstance(digits));
    }

    /**
     * 设置左边图标
     *
     * @param resId
     */
    public void setDrawableLeft(int resId) {
        if (resId == 0) return;
        Drawable drawable = getContext().getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        editText.setCompoundDrawables(drawable, null, null, null);
    }

    /**
     * 设置左边图标
     *
     * @param drawable
     */
    public void setDrawableLeft(Drawable drawable) {
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        editText.setCompoundDrawables(drawable, null, null, null);
    }

    /**
     * 设置输入提示信息
     *
     * @param hintText
     */
    public void setHintText(String hintText) {
        if (hintText == null || hintText.equals("")) return;
        editText.setHint(hintText);
    }

    public void setClearButtonDrawable(int resId) {
        if (resId == 0) return;
        clearBtn.setImageResource(resId);
    }

    /**
     * 设置控制密码显示/隐藏按钮图片资源
     *
     * @param resId 图片资源id
     */
    private void setShowPasswordButtonDrawable(int resId) {
        if (resId == 0) return;
        showPswBtn.setImageResource(resId);
    }

    /**
     * 设置获取验证码按钮的提示文字
     *
     * @param text
     */
    public void setAuthCodeButtonText(String text) {
        if (text == null || text.equals("")) return;
        authCodeBtn.setText(text);
    }

    /**
     * 设置获取验证码按钮的提示文字
     *
     * @param text
     */
    public void setAuthCodeButtonText(Spanned text) {
        if (text == null || text.equals("")) return;
        authCodeBtn.setText(text);
    }

    /**
     * 设置获取验证码按钮的使用状态
     *
     * @param enabled
     */
    public void setAuthCodeButtonEnabled(boolean enabled) {
        authCodeBtn.setEnabled(enabled);
    }

    public boolean isAuthCodeButtonEnabled(){
        return authCodeBtn.isEnabled();
    }

    /**
     * 设置获取验证码按钮文字颜色
     *
     * @param color
     */
    private void setAuthCodeButtonTextColor(int color) {
        authCodeBtn.setTextColor(color);
    }

    /**
     * 设置获取验证码按钮文字大小
     *
     * @param size
     */
    private void setAuthCodeButtonTextSize(float size) {
        authCodeBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    /**
     * 获取输入的内容
     *
     * @return 输入的内容
     */
    public String getInputText() {
        return editText.getText().toString();
    }

    /**
     * 设置输入文字颜色
     *
     * @param color
     */
    public void setInputTextColor(int color) {
        editText.setTextColor(color);
    }

    /**
     * 设置提示文字颜色
     *
     * @param color
     */
    public void setHintTextColor(int color) {
        editText.setHintTextColor(color);
    }

    /**
     * 设置输入文字显示的大小
     *
     * @param size
     */
    private void setInputTextSize(float size) {
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    /**
     * 设置可输入最大字符数
     *
     * @param maxLength
     */
    private void setMaxLength(int maxLength) {
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
    }

    /**
     * 设置获取验证码按钮背景颜色
     *
     * @param color
     */
    private void setAuthCodeButtonBackgroundColor(int color) {
        if (color == 0) return;
        authCodeBtn.setBackgroundColor(color);
    }

    /**
     * 设置获取验证码按钮背景资源
     *
     * @param resId 资源id
     */
    private void setAuthCodeButtonBackgroundResource(int resId) {
        if (resId == 0) return;
        authCodeBtn.setBackgroundResource(resId);
    }

    public void setEditTextBackgroundClor(int color) {
        if (color == 0) return;
        editText.setBackgroundColor(color);
    }

    public void setEditTextBackgroundResource(int resId) {
        if (resId == 0) return;
        editText.setBackgroundResource(resId);
    }


    public TextView getAuthCodeBtn() {
        return authCodeBtn;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();//清除按钮点击
//控制密码显示或隐藏的按钮点击
//获取验证码按钮点击
        if (i == R.id.iv_clear_btn) {
            editText.setText("");
        } else if (i == R.id.iv_is_show_view) {
            showPswBtnClickEvent();

        } else if (i == R.id.tv_auth_code_btn) {
            if (authCodeButtomClickListener != null)
                authCodeButtomClickListener.onAuthCodeButtonClick(v);
        }
    }

    /**
     * 处理显示/隐藏密码按钮点击事件，控制密码的显示或隐藏
     */
    private void showPswBtnClickEvent() {
        setShowPasswordButtonDrawable((passwordDisplayType == PasswordDisplayType.HIDDEN) ?
                passwordHiddenResId : passwordDisplayResid);
        passwordDisplayType = (passwordDisplayType == PasswordDisplayType.HIDDEN) ?
                PasswordDisplayType.DISPLAY : PasswordDisplayType.HIDDEN;
        //根据眼睛图标状态设置输入的密码显示或隐藏
        editText.setTransformationMethod((passwordDisplayType == PasswordDisplayType.HIDDEN) ?
                HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
        editText.setSelection(editText.getText().length());//设置光标始终显示在文字最右边
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        setClearButtonVisible(s.length() > 0);
        if (onTextChangedListener == null || s.length() == 0) return;
        onTextChangedListener.onTextChanged(s.toString(), customEditTextType);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * 设置获取验证码按钮点击事件监听
     *
     * @param authCodeButtomClickListener
     */
    public void setAuthCodeButtonClickListener(OnAuthCodeButtonClickListener authCodeButtomClickListener) {
        this.authCodeButtomClickListener = authCodeButtomClickListener;
        setAuthCodeButtonVisible(true);
    }

    /**
     * 设置文字改变监听
     *
     * @param onTextChangedListener
     * @param type                  type的值为使用者自己定义传入进来，用于区分同一个页面多个输入框的情况
     */
    public void setOnTextChangedListener(OnTextChangedListener onTextChangedListener, int type) {
        this.onTextChangedListener = onTextChangedListener;
        this.customEditTextType = type;
    }

    /**
     * 获取验证码按钮点击接口
     */
    public interface OnAuthCodeButtonClickListener {
        void onAuthCodeButtonClick(View view);
    }

    /**
     * 输入框文字改变回调接口
     */
    public interface OnTextChangedListener {
        void onTextChanged(String s, int type);
    }

}

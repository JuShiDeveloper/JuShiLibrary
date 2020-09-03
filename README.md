### JuShiLibrary功能说明

#### 一、base目录，包含application、activity、fragment、FloatWindowService等主要视图界面层基类。
#### 二、compression目录，图片压缩功能，基于luban图片压缩基础库封装，使用PictureCompression类名直接调用静态方法。
* 1、单张图片压缩

 单张图片压缩方法|参数说明
:--|:--|
compressionPicture(Context context, String path, final OnPictureCompressionListener listener)|参数 path ---> 要压缩的图片文件路径
compressionPicture(Context context, File file, final OnPictureCompressionListener listener)|参数 file ---> 要压缩的图片文件
compressionPicture(Context context, Uri uri, final OnPictureCompressionListener listener)|参数 uri ---> 要压缩的图片的uri

> 回调接口 OnPictureCompressionListener 说明：

回调方法|说明
:--|:--|
 onCompressStart() | 开始压缩；
onCompressSuccess(File file) | 压缩成功，参数为压缩后的图片文件；
 onCompressError(String msg) | 压缩失败，参数为失败信息；
* 2、多张图片压缩

多张图片压缩方法|说明
:--|:--|
 compressPictures(Context context, List<Object> photos, final OnCompressionPicturesListener listener)|参数 photos ---> 图片列表，列表类型可以是  File、String和Uri

> 回调接口 OnCompressionPicturesListener 说明：

回调方法|说明
:--|:--|
 onPictureFiles(List<File> pictures)|  压缩成功，参数为压缩后的图片文件列表；
 onError(String msg) | 压缩失败，参数为失败信息；

#### 三、customView目录，存放自定义及封装的view，方便在某些场景下直接使用。
* 1、dragScaleView目录，使用ImageView自定义实现双指缩放图片、单指触摸中心位置可拖动控件到父控件任意位置、单指点击上下左右四角并拖动可缩放图片等功能。
```
布局文件中使用
<com.jushi.library.customView.dragScaleView.DragScaleView
        android:layout_width="100dp"
        android:layout_height="100dp"
        android:layout_marginTop="10dp"
        android:background="@mipmap/ic_launcher"
        android:clickable="true" />
```
* 2、editText目录，自定义文本输入框，包含适用于注册、登录、密码找回等账号相关的输入框CustomEditText以及可拖动的文本输入框DragEditText。
> 1、 CustomEditText：适用于注册、登录、密码找回、短信验证码输入等账号相关的页面；

属性 | 说明
:--|:--|
digits |定义可输入字符属性，与EditText的属性使用相同
drawableLeft |与EditText的drawableLeft属性使用相同
hint | 与EditText的hint属性使用相同
hintTextColor | 与EditText的hintTextColor属性使用相同
srcClearButton |设置清除内容按钮的图片资源,与ImageView的src属性使用相同
srcPasswordDisplay | 设置密码显示状态的图片资源,与ImageView的src属性使用相同
srcPasswordHidden |设置密码隐藏状态的图片资源,与ImageView的src属性使用相同
authCodeHint |设置获取验证码按钮显示的文字
inputTextColor |设置输入文字的颜色
inputTextSize |设置输入文字的大小,与EditText的textSize属性使用相同
authCodeTextColor |设置获取验证码按钮文字颜色
authCodeTextSize| 设置获取验证码按钮文字大小
maxLength |设置可输入最大字符数
authCodeBackgroundColor | 获取验证码按钮背景色
authCodeBackgroundResource |获取验证码按钮背景资源文件
isShowAuthCodeBtn | 是否显示获取验证码按钮
editTextBackgroundColor| 输入框背景色
editTextBackgroundResource| 输入框背景图片等资源文件

```
布局文件使用：
<!--输入手机号-->
        <com.jushi.library.customView.editText.CustomEditText
            android:id="@+id/register_input_phone_number"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginLeft="30dp"
            android:layout_marginTop="20dp"
            android:layout_marginRight="30dp"
            android:padding="5dp"
            app:digits="1234567890"
            app:drawableLeft="@mipmap/ic_user_phonenum"
            app:editTextBackgroundResource="@drawable/bg_only_bottom_line_selector"
            app:hint="请输入手机号码"
            app:hintTextColor="#cccccc"
            app:inputTextColor="#000000"
            app:inputTextSize="16sp"
            app:maxLength="11"
            app:srcClearButton="@mipmap/ic_login_clear_content" />

        <!--输入验证码-->
        <com.jushi.library.customView.editText.CustomEditText
            android:id="@+id/register_input_auth_code"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginLeft="30dp"
            android:layout_marginTop="20dp"
            android:layout_marginRight="30dp"
            android:padding="5dp"
            app:authCodeBackgroundColor="#f7f7f7"
            app:authCodeHint="获取验证码"
            app:authCodeTextColor="#64c990"
            app:authCodeTextSize="16sp"
            app:digits="1234567890"
            app:drawableLeft="@mipmap/ic_user_auth_code"
            app:editTextBackgroundResource="@drawable/bg_only_bottom_line_selector"
            app:hint="请输入验证码"
            app:hintTextColor="#cccccc"
            app:inputTextColor="#000000"
            app:inputTextSize="16sp"
            app:maxLength="4"
            app:srcClearButton="@mipmap/ic_login_clear_content" />

        <!--设置6~20位密码-->
        <com.jushi.library.customView.editText.CustomEditText
            android:id="@+id/register_input_password"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginLeft="30dp"
            android:layout_marginTop="20dp"
            android:layout_marginRight="30dp"
            android:padding="5dp"
            app:digits="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
            app:drawableLeft="@mipmap/ic_user_password"
            app:editTextBackgroundResource="@drawable/bg_only_bottom_line_selector"
            app:hint="设置6~20位密码"
            app:hintTextColor="#cccccc"
            app:inputTextColor="#000000"
            app:inputTextSize="16sp"
            app:maxLength="20"
            app:srcClearButton="@mipmap/ic_login_clear_content"
            app:srcPasswordDisplay="@mipmap/ic_password_seeable"
            app:srcPasswordHidden="@mipmap/ic_password_unseeable" />

JAVA代码中获取对应输入控件做相应设置：
private void initCustomEditText() {
        inputPhoneNumber.setOnTextChangedListener(OnTextChangedListener, TYPE_INPUT_PHONE);
        inputAuthCode.setOnTextChangedListener(OnTextChangedListener, TYPE_INPUT_AUTH_CODE);
        inputPassword.setOnTextChangedListener(OnTextChangedListener, TYPE_INPUT_PASSWORD);
        inputPassword.setShowPasswordButtonVisible(true);
        inputPassword.setEditTextInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        inputAuthCode.setAuthCodeButtonClickListener(v -> {
            showToast("获取验证码");
        });
    }
OnTextChangedListener回调：
@Override
    public void onTextChanged(String s, int type) {
        switch (type) {
            case TYPE_INPUT_PHONE:  //电话号码输入框
                inputState[0] = s.length() == 11;
                inputAuthCode.setAuthCodeButtonEnabled(s.length() == 11);
                break;
            case TYPE_INPUT_AUTH_CODE: //验证码输入框
                inputState[1] = s.length() == 4;
                break;
            case TYPE_INPUT_PASSWORD: //密码输入框
                inputState[2] = s.length() >= 6;
                break;
        }
    }
```

> 2、DragEditText ：可拖动的文本输入框

属性|说明
:--|:--|
textSize|设置输入文字的大小,与EditText的textSize属性使用相同
text|设置显示的文本内容
textColor|文字颜色
hintText|输入框提示文本内容
hintColor|提示文本内容颜色
editDigits|定义可输入字符属性，与EditText的属性使用相同
backgroundResource|设置背景资源文件
editMaxLength|最大输入长度
lines|行数
gravity|输入内容对齐方式value = (left、right、top、bottom、center、center_horizontal、center_vertical)

```
布局文件使用：
<com.jushi.library.customView.editText.DragEditText
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:gravity="left"
        app:hintColor="#444444"
        app:hintText="测试拖动的输入框"
        app:lines="2"
        app:text=""
        app:textColor="#04c990"
        app:textSize="20sp" />
```
* 3、floatview目录，自定义继承RelativeLayout的FloatViewLayout的布局容器，包含拖动、手指离开后自动吸边的功能，适用于需要实现随意拖动视图的位置。

   属性|说明
   :--|:--|
   cacheXY|是否缓存拖动贴边之后的位置，默认值为true
   autoCalculateWelt|是否自动贴边，默认值为true
   animatorDuration|贴边动画时长，默认值250，单位毫秒
 ```
 布局文件中使用：
<com.jushi.library.customView.floatview.FloatViewLayout
        android:id="@+id/test_float"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:background="@android:color/holo_blue_bright"
        app:animatorDuration="1000"
        app:autoCalculateWelt="true"
        app:cacheXY="true">

        <ImageView
            android:id="@+id/iv_test"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_centerHorizontal="true"
            android:src="@mipmap/ic_launcher" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_below="@+id/iv_test"
            android:layout_marginTop="5dp"
            android:text="拖我点击我"
            android:textColor="#000000" />
    </com.jushi.library.customView.floatview.FloatViewLayout>
   ```
   * 4、mzbanner目录，轮播图控件封装，具体说明[请点击跳转](https://github.com/pinguo-zhouwei/MZBannerView)
   * 5、roundimage目录，基于ImageView自定义的圆角图片控件SelectableRoundedImageView，可单独设置上下左右四个圆角等。

   属性|说明
   :--|:--|
   riv_corner_radius|圆角半径
   sriv_left_top_corner_radius|左上角圆角半径
   sriv_right_top_corner_radius|右上角圆角半径
   sriv_left_bottom_corner_radius|左下角圆角半径
   sriv_right_bottom_corner_radius|右下角圆角半径
   sriv_border_width|描边宽度
   sriv_border_color|描边颜色
   * 6、scaleImageView目录，基于ImageView自定义的ScaleImageView控件，该自定义控件使用Kotlin代码实现，包含手势缩放图片、放大后单指触摸拖动图片、双击放大缩小等功能。
   ```
   布局文件中使用
   <com.jushi.library.customView.scaleImageView.ScaleImageView
        android:layout_width="200dp"
        android:layout_height="200dp"
        android:layout_marginTop="10dp"
        android:src="@mipmap/ic_launcher" />
   ```

* 7、slideTabStrip目录，导航栏特效，主要是导航栏字体大小和颜色的渐变特效，详细说明[请点击跳转](https://github.com/ta893115871/PagerSlidingTabStrip)
     ```
     布局文件中使用
     <com.jushi.library.customView.slideTabStrip.PagerSlidingTabStrip
            android:id="@+id/PagerSlidingTabStrip"
            android:layout_width="match_parent"
            android:layout_height="48dp"
            app:pstsDividerColor="#f7f7f7"
            app:pstsIndicatorColor="#09cc9f"
            app:pstsIndicatorHeight="2dp"
            app:pstsIndicatorWrap="true"
            app:pstsScaleZoomMax="0.1"
            app:pstsShouldExpand="true"
            app:pstsTextSelectedColor="#09CC9F"
            app:pstsUnderlineColor="#ebebeb" />

	JAVA代码中获取到控件后设置
	    pagerSlidingTabStrip.setViewPager(mViewPager);
        pagerSlidingTabStrip.setTextColor(Color.parseColor("#333333"));
        pagerSlidingTabStrip.setTextSize(16);
     ```
 * 8、wheelview目录，自定义3D滚轮控件WheelView，该控件从[日期选择器](https://github.com/Bigkoo/Android-PickerView)中抽离出来单独使用，具体说明[请点击跳转](https://github.com/Bigkoo/Android-PickerView)，xml布局使用示例[请点击跳转](https://github.com/JuShiDeveloper/JuShiLibrary/blob/master/app/src/main/res/layout/dialog_select_timg_layout.xml)查看，Java代码中调用示例[请点击跳转](https://github.com/JuShiDeveloper/JuShiLibrary/blob/master/app/src/main/java/com/library/jushi/jushilibrary/TestActivity.java)查看WheelView相关代码。

#### 四、database目录，数据库创建与管理简单封装。
> 1、在DoctorSQLiteOpenHelper类中做数据库创建、升级、建表等操作；
> 2、通过DatabaseManager类中submitDBTask(final DBTask<Data> dbTask)方法操作数据库表（增、删、改、查）
```
示例代码
private void testUseDataBase() {
        databaseManager.submitDBTask(new DatabaseManager.DBTask<String>() {
            @Override
            public String runOnDBThread(SQLiteDatabase sqLiteDatabase) {
                //执行sql 语句
                Log.v(MainActivity.class.getSimpleName(), "执行SQL语句");
                return null;
            }

            @Override
            public void runOnUIThread(String s) {

            }
        });
    }
```

#### 五、http目录，基于okhttp的网络请求封装。
* 1、http请求使用，自定义类继承BaseHttpRequester
```
代码示例:
get请求使用：
public class TestGETRequester extends BaseHttpRequester<String> {

    public TestGETRequester(@NonNull OnHttpResponseListener<String> listener) {
        super(listener);
    }

    @Override
    protected String onRequestRouter() {
        return "doctor/app/get_info_auth";
    }

    @Override
    protected String onDumpData(JSONObject jsonObject) throws JSONException {
        return jsonObject.getString("data");
    }

    @Override
    protected String onDumpDataError(JSONObject jsonObject) throws JSONException {
        return jsonObject.toString();
    }

    @Override
    protected void onParams(Map<String, Object> params){
        params.put("doc_id", 1008930);
    }
}

具体调用：
public class TestUseRequester implements OnHttpResponseListener<String>{
	public TestUseRequester(){
		new TestGETRequester(this).get();
		//post请求使用方式与TestGETRequester类一致，区别在于new出来的对象调用的是get()还是    	post()方法
		new TestPOSTRequester(this).post();
	}

	@Override //请求成功回调
    public void onHttpRequesterResponse(int code, String router, String s) {
        switch (router) {
            case "doctor/app/get_info_auth":
                Log.v(MainActivity.class.getSimpleName(), "测试GET请求成功！ code = " + code + "  result = " + s);
                break;
            case "doctor/app/password_verify":
                Log.v(MainActivity.class.getSimpleName(), "测试POST请求成功！code = " + code + "  result = " + s);
                break;
        }
    }

    @Override //请求失败回调
    public void onHttpRequesterError(int code, String router, String message) {
        switch (router) {
            case "doctor/app/get_info_auth":
                Log.v(MainActivity.class.getSimpleName(), "测试GET请求失败! code = " + code + "  message = " + message);
                break;
            case "doctor/app/password_verify":
                Log.v(MainActivity.class.getSimpleName(), "测试POST请求失败! code = " + code + "  message = " + message);
                break;
        }
    }
}

```
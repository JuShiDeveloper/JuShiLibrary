### JuShiLibrary功能说明

#### 一、base目录，包含application、activity、fragment、FloatWindowService等主要视图界面层基类。
#### 二、compression目录，图片压缩功能，基于luban图片压缩基础库封装，使用[PictureCompression](https://github.com/JuShiDeveloper/JuShiLibrary/blob/master/Library/src/main/java/com/jushi/library/compression/PictureCompression.java)类名直接调用静态方法。
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

属性|说明
:--|:--|
enabled_stroke|是否启用描边，默认为false，手指按下时显示
stroke_color|描边颜色，只有在启用描边的时候才生效
```
布局文件中使用
<com.jushi.library.customView.dragScaleView.DragScaleView
        android:layout_width="100dp"
        android:layout_height="100dp"
        android:layout_marginTop="10dp"
        android:src="@mipmap/ic_launcher"
        app:enabled_stroke="true"
        app:stroke_color="@android:color/holo_blue_light"/>
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
   * 6、scaleImageView目录，基于ImageView自定义的ScaleImageView控件，包含手势缩放图片、放大后单指触摸拖动图片、双击放大缩小等功能。
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
 * 9、navigationbar目录，自定义导航栏控件。

 属性|说明
 :--|:--|
 function|设置导航栏功能：NONE --- 不使用任何功能、BACK_BUTTON --- 返回按钮功能、LEFT_CLOSE_TITLE --- 关闭按钮文字、LEFT_CLOSE_ICON --- 关闭按钮图标、TITLE --- 导航栏标题、RIGHT_TEXT_BUTTON --- 最右边按钮文字、RIGHT_ICON_BUTTON --- 最右边按钮图标、SEARCH --- 搜索功能。
 backButtonIconResource|设置返回按钮图标资源文件
 closeButtonText|设置关闭按钮文字
 closeButtonTextColor|设置关闭按钮文字颜色
 closeButtonTextSize|设置关闭按钮文字大小
 closeButtonIconResource|设置关闭按钮图标资源文件
 titleText|设置导航栏标题
 titleTextSize|设置导航栏标题文字大小
 titleTextColor|设置导航栏标题文字颜色
rightButtonText|设置最右边按钮文字
rightButtonTextColor|设置最右边按钮文字颜色
rightButtonTextSize|设置最右边按钮文字大小
rightButtonIconResource|设置最右边按钮图标资源文件
searchBackgroundResource|设置搜索框背景资源文件
searchHint|设置搜索框提示文案
searchHintColor|设置搜索框提示文案颜色
searchTextSize|设置搜索框文字大小
searchTextColor|设置搜索框输入文字颜色
searchEditEnable|设置是否启用搜索框编辑模式，默认值false
searchEditFocusable|设置搜索框是否获得焦点，默认值false
isImmersiveStatusBar|是否沉浸式状态栏，默认值false
statusBarBackgroundColor|沉浸式状态栏背景色，isImmersiveStatusBar=true时生效，不设置则为透明
```
xml布局文件使用示例
<com.jushi.library.customView.navigationbar.NavigationBar
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        android:background="@android:color/holo_blue_dark"
        app:backButtonIconResource="@drawable/ic_arrow_back_black"
        app:closeButtonIconResource="@drawable/ic_close_black"
        app:closeButtonText="关闭"
        app:closeButtonTextColor="#f7f7f7"
        app:closeButtonTextSize="15sp"
        app:function="BACK_BUTTON|TITLE|LEFT_CLOSE_ICON|RIGHT_TEXT_BUTTON"
        app:rightButtonText="提交"
        app:rightButtonTextColor="#f9f9f9"
        app:rightButtonTextSize="15sp"
        app:titleText="自定义导航栏示例4"
        app:titleTextColor="#333333"
        app:titleTextSize="16sp" />

沉浸式状态栏示例：
<com.jushi.library.customView.navigationbar.NavigationBar
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@android:color/holo_blue_dark"
        app:backButtonIconResource="@drawable/ic_arrow_back_black"
        app:closeButtonIconResource="@drawable/ic_close_black"
        app:closeButtonText="关闭"
        app:closeButtonTextColor="#f7f7f7"
        app:closeButtonTextSize="15sp"
        app:function="BACK_BUTTON|TITLE|LEFT_CLOSE_ICON|RIGHT_TEXT_BUTTON"
        app:isImmersiveStatusBar="true"                        <!--是否沉浸式状态栏-->
        app:rightButtonText="提交"
        app:rightButtonTextColor="#f9f9f9"
        app:rightButtonTextSize="15sp"
        app:statusBarBackgroundColor="@color/colorAccent"    <!--状态栏颜色-->
        app:titleText="自定义导航栏示例4"
        app:titleTextColor="#333333"
        app:titleTextSize="16sp" />

        说明：isImmersiveStatusBar=true时需自行设置android:fitsSystemWindows才生效！
```
> NavigationBar更多布局文件使用示例[请点击跳转查看](https://github.com/JuShiDeveloper/JuShiLibrary/blob/master/app/src/main/res/layout/activity_navigation_bar_layout.xml)


#### 四、database目录，数据库创建与管理简单封装。
> 1、在DoctorSQLiteOpenHelper类中做数据库创建、升级、建表等操作；
>  \
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
		//post请求使用方式与TestGETRequester类一致，区别在于new出来的对象调用的是get()还是post()方法
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

* 2、文件下载封装 DownloadFileRequester。
```
使用示例：
private fun downLoadFile() { //文件下载
        Log.v("yufei", "文件下载")
        var url = "http://test.guijk.com/hfs/3015/5/1598580272286732/jpg/2/1008930/o"
        var savePath = externalCacheDir.path + "/download"
        var fileName = "testDownload.jpg"
        DownloadFileRequester().download(url, savePath, fileName, object : 	   DownloadFileRequester.OnDownloadListener {
            override fun onProgress(progress: Int) {
                Log.v("yufei", "$progress%")
            }

            override fun onSuccess() {
                Log.v("yufei", "onSucess")
            }

            override fun onError(msg: String?) {
                Log.v("yufei", "onError  $msg")
            }
        })
    }
```

* 3、文件上传封装 UploadFileRequester。
```
使用示例：
1、单文件上传
      UploadFileRequester().uploadFile(url, fileName, object : UploadFileRequester.OnUploadListener { //文件上传
                override fun onProgress(progress: Int) {
                    runOnUiThread { tv_progress.text = "$progress%" }
                    Log.v("yufei", "$progress%")
                }

                override fun onSuccess() {
                    Log.v("yufei", "onSucess")
                }

                override fun onError(msg: String?) {
                    Log.v("yufei", "onError  $msg")
                }
            })

2、上传多个文件
        UploadFileRequester().uploadFiles(url,fileNames) // fileNames---存有文件路径的list
```

#### 六、lottie目录，Lottie动画功能。
* 实现代码均由[lottie-android](https://github.com/airbnb/lottie-android)中抽离出来，为避免直接依赖（implementation 'com.airbnb.android:lottie:$lottieVersion'）可能出现的包冲突，具体使用说明[请点击跳转](https://github.com/airbnb/lottie-android)查看。
#### 七、router目录，Activity、Fragment路由器功能，多人开发同一项目时方便页面之间跳转使用。
* 1、[ActivityRouter](https://github.com/JuShiDeveloper/JuShiLibrary/blob/master/Library/src/main/java/com/jushi/library/router/ActivityRouter.java) ：使用ActivityRouter.startActivity()的多个重载方法跳转页面。
* 2、[FragmentRouter](https://github.com/JuShiDeveloper/JuShiLibrary/blob/master/Library/src/main/java/com/jushi/library/router/FragmentRouter.java) ：使用FragmentRouter.getFragment()方法获取Fragment对象。
* 3、[RouterList](https://github.com/JuShiDeveloper/JuShiLibrary/blob/master/Library/src/main/java/com/jushi/library/router/RouterList.java) ：路由器注册数据，以静态常量形式存放Activity、Fragment路径。
#### 八、systemBarUtils目录，状态栏设置工具类。
* 具体实现可查看[SystemBarUtil](https://github.com/JuShiDeveloper/JuShiLibrary/blob/master/Library/src/main/java/com/jushi/library/systemBarUtils/SystemBarUtil.java)
#### 九、takingPhoto目录，获取本地图片帮助功能。
* 1、使用[PictureHelper](https://github.com/JuShiDeveloper/JuShiLibrary/blob/master/Library/src/main/java/com/jushi/library/takingPhoto/PictureHelper.java)类中gotoCamera(Activity activity, int requestCode)方法调用相机拍照获取图片；
* 2、使用[PictureHelper](https://github.com/JuShiDeveloper/JuShiLibrary/blob/master/Library/src/main/java/com/jushi/library/takingPhoto/PictureHelper.java)类中gotoPhotoAlbum(Activity activity, int requestCode)方法打开相册选择图片；
* 3、使用[PictureHelper](https://github.com/JuShiDeveloper/JuShiLibrary/blob/master/Library/src/main/java/com/jushi/library/takingPhoto/PictureHelper.java)类中gotoClipActivity(Activity activity, Uri uri, int requestCode)方法打开图片裁剪界面；
#### 十、utils目录，常用工具类。
* 包含：手机号、密码校验工具([CalibratorUtil](https://github.com/JuShiDeveloper/JuShiLibrary/blob/master/Library/src/main/java/com/jushi/library/utils/CalibratorUtil.java))，身份证号校验工具([IdCardUtil](https://github.com/JuShiDeveloper/JuShiLibrary/blob/master/Library/src/main/java/com/jushi/library/utils/IdCardUtil.java))，root权限检测工具([CheckRoot](https://github.com/JuShiDeveloper/JuShiLibrary/blob/master/Library/src/main/java/com/jushi/library/utils/CheckRoot.java))，日期、时间工具([DateUtil](https://github.com/JuShiDeveloper/JuShiLibrary/blob/master/Library/src/main/java/com/jushi/library/utils/DateUtil.java))，设备工具([DeviceUtils](https://github.com/JuShiDeveloper/JuShiLibrary/blob/master/Library/src/main/java/com/jushi/library/utils/DeviceUtils.java))，MD5、SHA1加密工具([Encoder](https://github.com/JuShiDeveloper/JuShiLibrary/blob/master/Library/src/main/java/com/jushi/library/utils/Encoder.java))，文件工具类([FileUtil](https://github.com/JuShiDeveloper/JuShiLibrary/blob/master/Library/src/main/java/com/jushi/library/utils/FileUtil.java))，高斯模糊工具类([GaussUtils](https://github.com/JuShiDeveloper/JuShiLibrary/blob/master/Library/src/main/java/com/jushi/library/utils/GaussUtils.java))，手机系统判断工具类([OSUtils](https://github.com/JuShiDeveloper/JuShiLibrary/blob/master/Library/src/main/java/com/jushi/library/utils/OSUtils.java))，执行周期性任务(定时器)工具类([PeriodicTask](https://github.com/JuShiDeveloper/JuShiLibrary/blob/master/Library/src/main/java/com/jushi/library/utils/PeriodicTask.java))，屏幕相关尺寸工具类([ScreenUtils](https://github.com/JuShiDeveloper/JuShiLibrary/blob/master/Library/src/main/java/com/jushi/library/utils/ScreenUtils.java))，SHA1签名检测工具类([SHA1SignCheck](https://github.com/JuShiDeveloper/JuShiLibrary/blob/master/Library/src/main/java/com/jushi/library/utils/SHA1SignCheck.java))等。
#### 十一、viewinject目录，IOC 注解,支持findViewById使用方法，通过注解的方式获取控件。
* 使用[ViewInjecter](https://github.com/JuShiDeveloper/JuShiLibrary/blob/master/Library/src/main/java/com/jushi/library/viewinject/ViewInjecter.java)类中的inject(Activity activity)方法，该方法有多个重载的方法。
```
Activity中使用示例：
public class TestActivity extends AppCompatActivity{
   @FindViewById(R.id.rl_select_time_view)
    private RelativeLayout rlDialogView;
    @FindViewById(R.id.rl_select_time_dialog_layout)
    private RelativeLayout llLayoutView;
    @FindViewById(R.id.wv_select_start_hour)
    private WheelView wvSelectStartHour;
    @FindViewById(R.id.wv_select_start_minute)
    private WheelView wvSelectStartMinute;
    @FindViewById(R.id.wv_select_end_hour)
    private WheelView wvSelectEndHour;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewInjecter.inject(this);
    }
}

Fragment中使用示例：
public abstract class BaseFragment extends Fragment {
    private View rootView;
   @FindViewById(R.id.rl_select_time_view)
    private RelativeLayout rlDialogView;
    @FindViewById(R.id.rl_select_time_dialog_layout)
    private RelativeLayout llLayoutView;
    @FindViewById(R.id.wv_select_start_hour)
    private WheelView wvSelectStartHour;
    @FindViewById(R.id.wv_select_start_minute)
    private WheelView wvSelectStartMinute;
    @FindViewById(R.id.wv_select_end_hour)
    private WheelView wvSelectEndHour;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = xxxxx；
        ViewInjecter.inject(this, rootView);
        return rootView;
    }
  }
```
#### 十二、watermark目录，给图片添加水印功能。
* [Watermark](https://github.com/JuShiDeveloper/JuShiLibrary/blob/master/Library/src/main/java/com/jushi/library/watermark/Watermark.java)为添加水印的主要实现，可添加的水印类型包括：文字水印、图片水印，具体使用方法是[请点击跳转](https://github.com/JuShiDeveloper/JuShiLibrary/blob/master/Library/src/main/java/com/jushi/library/watermark/Watermark.java)查看注释详解。

#### 十三、websocket目录，WebSocket封装。
* 自定义类继承WSBaseManager，重写onMessage(String message)、onError(String error)、onHeartbeatTime()、onHearbeatContent()四个方法，调用sendStringMessage(msg)方法发送消息。
```
使用示例：
public class TestWebSocketManager extends WSBaseManager {

    @Override
    protected void onMessage(String message) {
        //接收到消息
    }

    @Override
    protected void onError(String error) {
        // 出错
    }

    @Override
    protected long onHeartbeatTime() {
        return 500; //发送心跳包时间，单位毫秒
    }

    @Override
    protected JSONObject onHearbeatContent() { //发送的心跳包内容
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("key","value");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    public void connectWS(String url){
        //初始化并连接WebSocket
        initWs(url);
    }

    public void sendMsg(String msg){
        //发送消息
        sendStringMessage(msg);
    }
}
```
#### 十四、zxing目录，zxing封装。
* 实例化ScanManager管理类来使用二维码、条形码扫描及生成功能；
   二维码、条形码扫描示例[请点击跳转](https://github.com/JuShiDeveloper/JuShiLibrary/blob/master/app/src/main/java/com/library/jushi/jushilibrary/ScanCodeActivity.kt)，二维码、条形码生成示例[请点击跳转](https://github.com/JuShiDeveloper/JuShiLibrary/blob/master/app/src/main/java/com/library/jushi/jushilibrary/CreateQRCodeActivity.kt)。

#### 十五、amap目录，高德地图位置点选功能，类似微信发送位置功能。
1、修改manifest.xml中meta-data的value值

```
<meta-data
            android:name="com.amap.api.v2.apikey"
            android:value="你申请的高德地图apikey" />
```
2、使用
```
跳转位置选择页面
startActivityForResult(new Intent(this, SelectLocationActivity.class), 121)

获取到页面返回选择的位置信息
String location = data.getStringExtra(SelectLocationActivity.EXTRA_LOCATION);
{"address":"阳关大道XXXX","districtName":"XX区","cityName":"XX市","province":"520000","city":"520100","latitude":26.621906,"district":"520115","provinceName":"贵州省","longitude":106.649174}
```
#### 十六、bluetooth目录，蓝牙服务功能，蓝牙连接状态监听、蓝牙扫描管理、蓝牙连接/断开连接、蓝牙通信(收发消息)。
蓝牙服务功能主要使用BluetoothFuncManager类进行管理，该类提供的函数如下：
|函数名称|说明  |
|:--|:--|
|void registerReceiver(Activity activity) | 注册蓝牙相关广播 |
|void unregisterReceiver()|取消注册广播|
|void checkConnectedDevice()|检查已连接的蓝牙设备数量|
|boolean bluetoothIsEnabled()|蓝牙是否开启|
|void requestOpenBluetooth()|请求开启蓝牙|
|void doDiscovery()|扫描蓝牙设备|
|void cancelDiscovery()|停止扫描|
|void connect(BluetoothDevice device)|连接设备|
|void disConnect()|断开连接|
|void write(String msg)|发送消息|
|void addBluetoothConnectListener(BluetoothConnectListener listener)|添加蓝牙连接监听|
|void removeBluetoothConnectListener(BluetoothConnectListener listener)|移除蓝牙连接监听|
|void addBluetoothFoundListener(BluetoothFoundListener listener)|添加蓝牙扫描结果监听|
|void removeBluetoothFoundListener(BluetoothFoundListener listener)|移除蓝牙扫描结果监听|
|void addOnReceivedMessageListener(OnMessageListener listener)|添加消息监听|
|void removeOnReceivedMessageListener(OnMessageListener listener)|移除消息监听|
#### 十七、manager目录
1、NetworkManager
网络变化管理器，负责管理网络变化的通知，如果需要知道当前手机的网络连接类型，调用getNetWorkType函数 如果需要时刻监听网络的断开与连接，请调用addOnNetworkChangeListener。

2、SdManager
SD卡管理，项目中用到SD卡存储时，使用该类统一管理。

3、UserManager
用户信息管理类，根据项目实际需求做相应修改。
#### 十八、share_data目录,数据缓存工具
ShareSparse SP数据缓存类，SharedPreferences缓存管理。


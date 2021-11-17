package com.library.jushi.jushilibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jushi.library.amap.SelectLocationActivity;
import com.jushi.library.base.BaseFragmentActivity;
import com.jushi.library.base.BaseApplication.Manager;
import com.jushi.library.customView.bottomNavgationView.BottomNavigationView;
import com.jushi.library.customView.editText.CustomEditText;
import com.jushi.library.customView.itemView.ItemView;
import com.jushi.library.customView.messageInput.MessageInputView;
import com.jushi.library.customView.mzbanner.MZBannerView;
import com.jushi.library.customView.mzbanner.holder.MZViewHolder;
import com.jushi.library.customView.radar.RadarData;
import com.jushi.library.customView.radar.RadarView;
import com.jushi.library.customView.slideTabStrip.PagerSlidingTabStrip;
import com.jushi.library.customView.wheelview.WheelAdapter;
import com.jushi.library.customView.wheelview.WheelView;
import com.jushi.library.database.DatabaseManager;
import com.jushi.library.http.OnHttpResponseListener;
import com.jushi.library.manager.NetworkManager;
import com.jushi.library.takingPhoto.PictureHelper;
import com.jushi.library.takingPhoto.view.CircleImageView;
import com.jushi.library.utils.LogUtil;
import com.jushi.library.viewinject.FindViewById;
import com.library.jushi.jushilibrary.calculator.CalculatorActivity;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class TestActivity extends BaseFragmentActivity implements OnHttpResponseListener<String>, NetworkManager.OnNetworkChangeListener, CustomEditText.OnTextChangedListener {
    private final int REQUEST_CODE_OPEN_ALBUM = 0x101;
    private final int REQUEST_CODE_CLIP_PHOTO = 0x102;
    private final int REQUEST_CODE_OPEN_CAMERA = 0x103;


    private TestGETRequester t;
    @FindViewById(R.id.btn_start)
    private Button btnStart;
    @FindViewById(R.id.btn_cancel)
    private Button btnCancel;
    @FindViewById(R.id.btn_navigation_bar)
    private Button btnNavigationBar;

    @Manager
    private NetworkManager networkManager;
    @Manager
    private DatabaseManager databaseManager;

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
    @FindViewById(R.id.wv_select_end_minute)
    private WheelView wvSelectEndMinute;
    private List<String> hourList = new ArrayList<>();
    private List<String> minuteList = new ArrayList<>();
    private String startHour;
    private String startMinute;
    private String endHour;
    private String endMinute;

    private String[] startArr;
    private String[] endArr;

    private int curStartHour;
    private int curStartMinute;
    private int curEndHour;
    private int curEndMintue;

    @FindViewById(R.id.PagerSlidingTabStrip)
    private PagerSlidingTabStrip pagerSlidingTabStrip;
    @FindViewById(R.id.inquiry_list_ViewPager)
    private ViewPager mViewPager;
    private String[] titles = {"图文问诊", "视频问诊", "电话问诊"};
    private List<Fragment> pages = new ArrayList<>();

    @FindViewById(R.id.MZBannerView)
    private MZBannerView mzBannerView;
    private List<Integer> bannerPages = new ArrayList<>();

    @FindViewById(R.id.register_input_phone_number)
    private CustomEditText inputPhoneNumber;
    @FindViewById(R.id.register_input_auth_code)
    private CustomEditText inputAuthCode;
    @FindViewById(R.id.register_input_password)
    private CustomEditText inputPassword;
    private final int TYPE_INPUT_PHONE = 0;
    private final int TYPE_INPUT_AUTH_CODE = 1;
    private final int TYPE_INPUT_PASSWORD = 2;
    //保存手机号/验证码输入正确时的状态，默认false,输入正确时修改位true
    private boolean[] inputState = {false, false, false};

    @FindViewById(R.id.btn_scan)
    private Button btnScan;
    @FindViewById(R.id.create_code)
    private Button createCode;

    @FindViewById(R.id.webview)
    private Button btnWeb;

    @FindViewById(R.id.RadarView)
    private RadarView radarView;
    @FindViewById(R.id.RadarView1)
    private RadarView radarView1;

    @FindViewById(R.id.open_album)
    private Button openAlbum;
    @FindViewById(R.id.CircleImageView)
    private CircleImageView imageView;
    @FindViewById(R.id.open_camera)
    private Button openCamera;
    private File imageFile;

    @FindViewById(R.id.ItemView1)
    private ItemView itemView1;
    @FindViewById(R.id.ItemView2)
    private ItemView itemView2;

    @FindViewById(R.id.camera)
    private Button btnCamera;
    @FindViewById(R.id.Storage)
    private Button btnStorage;
    @FindViewById(R.id.audio)
    private Button btnAudio;
    @FindViewById(R.id.location)
    private Button btnLocation;
    @FindViewById(R.id.refresh)
    private Button btnRefresh;
    @FindViewById(R.id.calculator)
    private Button btnCalculator;
    @FindViewById(R.id.test_RV)
    private Button btnTestRV;
    @FindViewById(R.id.alert_window)
    private Button btnAlertWindow;

    @FindViewById(R.id.test_bottom_nav)
    private Button btnBottomNav;
    @FindViewById(R.id.test_calender_view)
    private Button btnCalender;
    @FindViewById(R.id.test_map_view)
    private Button btnMap;


    @Override
    protected int getLayoutResId() {
        setSystemBarStatus(true, true, false);
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
//        App.getInstance().getManager(DatabaseManager.class);
        startService(new Intent(this, TestFloatWindowService.class));

        mViewPager.setAdapter(pagerAdapter);
        pagerSlidingTabStrip.setViewPager(mViewPager);
        pagerSlidingTabStrip.setTextColor(Color.parseColor("#333333"));
        pagerSlidingTabStrip.setTextSize(16);

        initBannerView();
        initCustomEditText();
        initRadarView();

    }

    private void initRadarView() {
        String[] skillName = {"管理", "设计", "维护", "巡检", "开发"};
        List<RadarData> data = new ArrayList<>();
        for (int i = 1; i < skillName.length + 1; i++) {
            data.add(new RadarData(skillName[i - 1], i * 20));
        }
        radarView.setDataList(data);

        String[] skillName1 = {"管理", "设计", "维护", "巡检", "开发", "运营"};
        List<RadarData> data1 = new ArrayList<>();
        for (int i = 1; i < skillName1.length + 1; i++) {
            data1.add(new RadarData(skillName1[i - 1], i * 20));
        }
        radarView1.setDataList(data1);

    }

    private void initBannerView() {
        mzBannerView.setBannerPageClickListener((view, position) -> {
            showToast("点击 " + position);
        });
        bannerPages.add(R.mipmap.ic_launcher);
        bannerPages.add(R.mipmap.ic_launcher_round);
        mzBannerView.setIndicatorVisible(false);
        mzBannerView.setDuration(1500);
        mzBannerView.setPages(bannerPages, () -> new MZViewHolder() {
            ImageView imageView = null;

            @Override
            public View createView(Context context) {
                LinearLayout view = new LinearLayout(TestActivity.this);
                imageView = new ImageView(TestActivity.this);
                view.addView(imageView);
                return view;
            }

            @Override
            public void onBind(Context context, int position, Object data) {
                imageView.setImageResource(bannerPages.get(position));
            }
        });
        mzBannerView.start();
    }

    private void initCustomEditText() {
        inputPhoneNumber.setOnTextChangedListener(this, TYPE_INPUT_PHONE);
        inputAuthCode.setOnTextChangedListener(this, TYPE_INPUT_AUTH_CODE);
        inputPassword.setOnTextChangedListener(this, TYPE_INPUT_PASSWORD);
        inputPassword.setShowPasswordButtonVisible(true);
        inputPassword.setEditTextInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        inputAuthCode.setAuthCodeButtonClickListener(v -> {
            showToast("获取验证码");
        });
    }

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


    @Override
    protected void initData() {
        t = new TestGETRequester(this);
        new TestPOSTRequester(this).post();
        testUseDataBase();
        initWheelView();
        new TestWebSocketManager().initWs("wss://test-dr.hlwyy.cn:8282");
    }

    private void initWheelView() {
        hourList.addAll(getListData(24));
        minuteList.addAll(getListData(60));
        startArr = "12:00".split(":");
        endArr = "23:59".split(":");

        curStartHour = hourList.indexOf(startArr[0].equals("24") ? "00" : startArr[0]);
        curStartMinute = minuteList.indexOf(startArr.length > 1 ? startArr[1] : "00");
        curEndHour = hourList.indexOf(endArr[0].equals("24") ? "00" : endArr[0]);
        curEndMintue = minuteList.indexOf(endArr.length > 1 ? endArr[1] : "00");

        wvSelectStartHour.setAdapter(new MyAdapter(hourList));
        wvSelectStartMinute.setAdapter(new MyAdapter(minuteList));
        wvSelectEndHour.setAdapter(new MyAdapter(hourList));
        wvSelectEndMinute.setAdapter(new MyAdapter(minuteList));
    }

    @Override
    protected void setListener() {
        btnStart.setOnClickListener(v -> t.get());
        btnCancel.setOnClickListener(v -> t.cancel());
        networkManager.addOnNetworkChangeListener(this);

        wvSelectStartHour.setOnItemSelectedListener(index -> {
            startHour = hourList.get(index);
            curStartHour = index;
        });
        wvSelectStartMinute.setOnItemSelectedListener(index -> {
            startMinute = minuteList.get(index);
            curStartMinute = index;
        });
        wvSelectEndHour.setOnItemSelectedListener(index -> {
            endHour = hourList.get(index);
            curEndHour = index;
        });
        wvSelectEndMinute.setOnItemSelectedListener(index -> {
            endMinute = minuteList.get(index);
            curEndMintue = index;
        });

        btnNavigationBar.setOnClickListener(v -> {
            startActivity(new Intent(this, NavigationBarActivity.class));
        });

        btnScan.setOnClickListener(v -> {
            startActivity(new Intent(this, ScanCodeActivity.class));
        });

        createCode.setOnClickListener(v -> {
            startActivity(new Intent(this, CreateQRCodeActivity.class));
        });
        btnWeb.setOnClickListener(v -> {
            String url = "https://test-m.guijk.com/examination/index.html#/?uid=1012162&sid=84fec9a8e45846340fdf5c7c9f7ed66c&gender=3&cv=9012&ct=1";
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra(WebViewActivity.EXTRA_KEY_WEB_URL, url);
            startActivity(intent);
        });

        openAlbum.setOnClickListener(v -> {
            PictureHelper.gotoPhotoAlbum(this, REQUEST_CODE_OPEN_ALBUM);
        });
        openCamera.setOnClickListener(v -> {
            imageFile = PictureHelper.gotoCamera(this, REQUEST_CODE_OPEN_CAMERA);
        });
//        itemView1.setOnClickListener(v -> {
//            showToast("点击设置选项卡");
//        });
//        itemView2.setOnClickListener(v -> {
//        });

        btnCamera.setOnClickListener(v -> {
            if (checkCameraPermission()) {
                showToast("相机权限已打开");
            }
        });
        btnStorage.setOnClickListener(v -> {
            if (checkExternalStoragePermission()) {
                showToast("存储权限已打开");
            }
        });
        btnLocation.setOnClickListener(v -> {
            if (checkLocationPermission()) {
                showToast("定位权限已打开");
            }
        });
        btnAudio.setOnClickListener(v -> {
            if (checkRecordAudioPermission()) {
                showToast("录音权限已打开");
            }
        });
        btnAlertWindow.setOnClickListener(v -> {
            if (checkAlertWindowPermission()) {
                showToast("悬浮窗权限已打开");
            }
        });
        btnRefresh.setOnClickListener(v -> {
            startActivity(new Intent(this, RefreshSimpleActivity.class));
        });
        btnCalculator.setOnClickListener(v -> {
            startActivity(new Intent(this, CalculatorActivity.class));
        });

        btnTestRV.setOnClickListener(v -> {
            startActivity(new Intent(this, UltraRecyclerViewActivity.class));
        });

        btnBottomNav.setOnClickListener(v -> startActivity(new Intent(this, BottomNavigationViewActivity.class)));
        btnCalender.setOnClickListener(v -> startActivity(new Intent(this, CalendarViewActivity.class)));
        btnMap.setOnClickListener(v -> startActivityForResult(new Intent(this, SelectLocationActivity.class), 121));
    }

    private List<String> getListData(int max) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            if (i < 10) {
                list.add("0" + i);
            } else {
                list.add("" + i);
            }
        }
        return list;
    }


    private class MyAdapter implements WheelAdapter {

        private List<String> list;

        public MyAdapter(List<String> list) {
            this.list = list;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        public Object getItem(int index) {
            return list.get(index);
        }

        @Override
        public int indexOf(Object o) {
            return list.indexOf(o);
        }
    }

    private FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            return getFragment(position);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        private Fragment getFragment(int position) {
            if (position == 0 && pages.size() == 0) {
                pages.add(new TestFragment());
            }
            if (position == 1 && pages.size() == 1) {
                pages.add(new TestFragment());
            }
            if (position == 2 && pages.size() == 2) {
                pages.add(new TestFragment());
            }
            return pages.get(position);
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        mzBannerView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (t != null)
            t.cancel();
    }

    @Override
    public void onHttpRequesterResponse(int code, String router, String message, String s) {
        switch (router) {
            case "doctor/app/get_info_auth":
                Log.v(MainActivity.class.getSimpleName(), "测试GET请求成功！ code = " + code + "  result = " + s);
                break;
            case "doctor/app/password_verify":
                Log.v(MainActivity.class.getSimpleName(), "测试POST请求成功！code = " + code + "  result = " + s);
                break;
        }
    }

    @Override
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

    @Override
    public void onNetworkChange(int networkType) {
        // networkType != 1 为有网
        Log.v(MainActivity.class.getSimpleName(), "网络类型：" + networkType);
    }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        switch (requestCode) {
            case REQUEST_CODE_OPEN_ALBUM:
                PictureHelper.gotoClipActivity(this, data.getData(), REQUEST_CODE_CLIP_PHOTO);
                break;
            case REQUEST_CODE_CLIP_PHOTO:
                LogUtil.v("yufei", data.getData().getPath());
                imageView.setImageURI(data.getData());
                break;
            case REQUEST_CODE_OPEN_CAMERA:
                LogUtil.v("yufei", imageFile.getPath());
                PictureHelper.gotoClipActivity(this, Uri.fromFile(imageFile), REQUEST_CODE_CLIP_PHOTO);
                break;
            case 121:
                LogUtil.v("选择位置信息: " + data.getStringExtra(SelectLocationActivity.EXTRA_LOCATION));
                break;
        }
    }

    @Override
    protected void onCameraPermissionOpened() {
        super.onCameraPermissionOpened();
        LogUtil.v("yufei", "onCameraPermissionOpened");
    }

    @Override
    protected void onLocationPermissionOpened() {
        super.onLocationPermissionOpened();
        LogUtil.v("yufei", "onLocationPermissionOpened");
    }

    @Override
    protected void onExternalStoragePermissionOpened() {
        super.onExternalStoragePermissionOpened();
        LogUtil.v("yufei", "onExternalStoragePermissionOpened");
    }

    @Override
    protected void onRecordAudioPermissionOpened() {
        super.onRecordAudioPermissionOpened();
        LogUtil.v("yufei", "onRecordAudioPermissionOpened");
    }

    @Override
    protected void onAlertWindowPermissionOpened() {
        super.onAlertWindowPermissionOpened();
        LogUtil.v("yufei", "onAlertWindowPermissionOpened");
    }
}

package com.library.jushi.jushilibrary;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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

import com.jushi.library.base.BaseFragmentActivity;
import com.jushi.library.base.Manager;
import com.jushi.library.customView.editText.CustomEditText;
import com.jushi.library.customView.mzbanner.MZBannerView;
import com.jushi.library.customView.mzbanner.holder.MZViewHolder;
import com.jushi.library.customView.slideTabStrip.PagerSlidingTabStrip;
import com.jushi.library.customView.wheelview.WheelAdapter;
import com.jushi.library.customView.wheelview.WheelView;
import com.jushi.library.database.DatabaseManager;
import com.jushi.library.http.OnHttpResponseListener;
import com.jushi.library.utils.NetworkManager;
import com.jushi.library.viewinject.FindViewById;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends BaseFragmentActivity implements OnHttpResponseListener<String>, NetworkManager.OnNetworkChangeListener, CustomEditText.OnTextChangedListener {
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

    @Override
    protected int getLayoutResId() {
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

        createCode.setOnClickListener(v->{
            startActivity(new Intent(this, CreateQRCodeActivity.class));
        });
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
        t.cancel();
    }

    @Override
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

}

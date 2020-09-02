package com.library.jushi.jushilibrary;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jushi.library.base.BaseFragmentActivity;
import com.jushi.library.base.Manager;
import com.jushi.library.customView.floatview.FloatViewLayout;
import com.jushi.library.customView.slideTabStrip.PagerSlidingTabStrip;
import com.jushi.library.customView.wheelview.WheelAdapter;
import com.jushi.library.customView.wheelview.WheelView;
import com.jushi.library.database.DatabaseManager;
import com.jushi.library.http.OnHttpResponseListener;
import com.jushi.library.utils.NetworkManager;
import com.jushi.library.viewinject.FindViewById;
import com.jushi.library.viewinject.ViewInjecter;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends BaseFragmentActivity implements OnHttpResponseListener<String>, NetworkManager.OnNetworkChangeListener {
    private TestGETRequester t;
    @FindViewById(R.id.btn_start)
    private Button btnStart;
    @FindViewById(R.id.btn_cancel)
    private Button btnCancel;
    @FindViewById(R.id.test_float)
    private FloatViewLayout floatViewLayout;
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
    }

    @Override
    protected void initData() {
        t = new TestGETRequester(this);
        new TestPOSTRequester(this).post();
        testUseDataBase();
        initWheelView();
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
        floatViewLayout.setOnClickListener(v -> {
            showToast("点击");
            startActivity(new Intent(this, MainActivity.class));
        });

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

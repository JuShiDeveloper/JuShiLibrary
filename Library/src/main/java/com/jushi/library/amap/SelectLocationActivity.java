package com.jushi.library.amap;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.RotateAnimation;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.jushi.library.R;
import com.jushi.library.base.BaseFragmentActivity;
import com.jushi.library.customView.navigationbar.NavigationBar;
import com.jushi.library.utils.LogUtil;

/**
 * 选择位置地图界面（高德地图）
 * 类似微信发送位置功能
 * created by wyf on 2021-11-17
 */
public class SelectLocationActivity extends BaseFragmentActivity implements AMapLocationListener, GeocodeSearch.OnGeocodeSearchListener, AMap.OnCameraChangeListener, LocationsAdapter.OnItemClickListener, PoiSearch.OnPoiSearchListener {
    public static final String EXTRA_LOCATION = "extra_location";
    private AppCompatImageView ivLocation;
    private MapView mapView;
    private AMap aMap;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private TextView tvMyLocation,tvConfirmBtn;
    private RecyclerView recyclerView;
    private NavigationBar searchView;
    private LinearLayout llCurrLocation;
    private ImageView ivSelect;
    private LocationsAdapter locationsAdapter;
    private GeocodeSearch geocoderSearch;
    private RegeocodeQuery query;
    private PoiSearch poiSearch;
    private JSONObject locationJson;
    private boolean isSelect = false;
    private LatLng latLng;//当前所在位置

    @Override
    protected int getLayoutResId() {
        setSystemBarStatus(true,true,false);
        return R.layout.activity_map_layout;
    }

    @Override
    protected void initView() {
        ivLocation = findViewById(R.id.iv_location);
        mapView = findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        tvMyLocation = findViewById(R.id.tv_my_location);
        searchView = findViewById(R.id.search_view);
        recyclerView = findViewById(R.id.rv_locations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        locationsAdapter = new LocationsAdapter(this);
        recyclerView.setAdapter(locationsAdapter);
        llCurrLocation = findViewById(R.id.ll_curr_location);
        ivSelect = findViewById(R.id.iv_select);
        tvConfirmBtn = findViewById(R.id.tv_confirm);
    }

    @Override
    protected void initData() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        setLocationStyle();//设置定位蓝点
        setUiSettings();// 控件交互 缩放按钮、指南针、定位按钮、比例尺等
        getLocation();//获取位置信息
        initSearch();
    }

    private void initSearch() {
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        poiSearch = new PoiSearch(this, null);
        poiSearch.setOnPoiSearchListener(this);
        locationJson = new JSONObject();
        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        query = new RegeocodeQuery(new LatLonPoint(0, 0), 1000, GeocodeSearch.AMAP);
    }

    private void setLocationStyle() {//设置定位蓝点
        //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        // 连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色    不显示范围圆圈
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色 不显示范围圆圈
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
//        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        //设置缩放级别
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
    }

    private void setUiSettings() {// 控件交互 缩放按钮、指南针、定位按钮、比例尺等
        // 控件交互 缩放按钮、指南针、定位按钮、比例尺等
        UiSettings mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象
        mUiSettings.setZoomControlsEnabled(false);
        mUiSettings.setMyLocationButtonEnabled(false); //显示默认的定位按钮  false-不显示默认定位按钮
        aMap.setMyLocationEnabled(true);// 可触发定位并显示当前位置
        mUiSettings.setScaleControlsEnabled(true);//控制比例尺控件是否显示
        mUiSettings.setLogoPosition(AMapOptions.LOGO_MARGIN_LEFT);//设置logo位置
    }

    private void getLocation() {//获取位置信息
        //获取位置信息
        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(SelectLocationActivity.this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(-1000);
        mLocationOption.setOnceLocation(true);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        //启动定位
        mlocationClient.startLocation();
    }

    @Override
    public void setListener() {
        //地图移动监听
        aMap.setOnCameraChangeListener(this);
        locationsAdapter.setOnItemClickListener(this);
        tvMyLocation.setOnClickListener(v -> {
            aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng,15,0,0)));
            ivSelect.setVisibility(View.VISIBLE);
            locationsAdapter.setCurrentSelect(-1);
        });
        searchView.setOnRightButtonClickListener(v->{
            hideSoftInput();
            PoiSearch.Query query = new PoiSearch.Query(searchView.getInputText(), "", "");
            query.setPageSize(30);// 设置每页最多返回多少条poiitem
            query.setPageNum(1);//设置查询页码
            poiSearch.setQuery(query);
            poiSearch.searchPOIAsyn();
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                hideSoftInput();
            }
        });
        llCurrLocation.setOnClickListener(v->aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng,15,0,0))));
        tvConfirmBtn.setOnClickListener(v->setResult());
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        LogUtil.v("onCameraChangeFinish = " + cameraPosition.toString());
//        setMarker(cameraPosition.target);
        animTranslate();
        if (this.isSelect)return;
        getGeocodeSearch(cameraPosition.target);
    }

    @Override
    public void onItemClick(PoiItem poiItem) { //点击位置列表项
        this.isSelect = true;
        ivSelect.setVisibility(View.INVISIBLE);
        poiSearch.searchPOIIdAsyn(poiItem.getPoiId());// 异步搜索
        //地图移动到搜索内容位置区域
        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                new LatLng(poiItem.getLatLonPoint().getLatitude(),poiItem.getLatLonPoint().getLongitude()),15,0,0)));
    }

    private Marker centerMarker;

    private void setMarker(LatLng target) {
        if (centerMarker != null) centerMarker.remove();
        centerMarker = aMap.addMarker(new MarkerOptions().position(target).title("").snippet(""));
        Animation animation = new RotateAnimation(centerMarker.getRotateAngle() - 180, centerMarker.getRotateAngle(), 0, 0, 0);
//        Animation animation = new TranslateAnimation(target);
        animation.setDuration(360L);
        animation.setInterpolator(new LinearInterpolator());
        centerMarker.setAnimation(animation);
        centerMarker.startAnimation();
    }

    private AnimatorSet animatorSet;

    public void animTranslate() {
        if (animatorSet != null) {
            animatorSet.start();
            return;
        }
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(ivLocation, "scaleX", 1, 0.5f, 1).setDuration(300)
                , ObjectAnimator.ofFloat(ivLocation, "scaleY", 1, 0.5f, 1).setDuration(300));
    }

    //逆地理编码获取当前位置信息
    private void getGeocodeSearch(LatLng targe) {
        query.setPoint(new LatLonPoint(targe.latitude, targe.longitude));
        geocoderSearch.getFromLocationAsyn(query);
        locationJson.put("latitude", targe.latitude);
        locationJson.put("longitude", targe.longitude);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null && amapLocation.getErrorCode() == AMapLocation.LOCATION_SUCCESS) {
            latLng = new LatLng(amapLocation.getLatitude(),amapLocation.getLongitude());
            setMapCenter(amapLocation);
        } else {
            //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
            LogUtil.v("AmapError", "location Error, ErrCode:" + amapLocation.getErrorCode() + ", errInfo:" + amapLocation.getErrorInfo());
        }
        mlocationClient.stopLocation();
        mlocationClient.onDestroy();
    }

    private void setMapCenter(AMapLocation amapLocation) {
        aMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                new CameraPosition(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude())
                        , 15, 0, 0)), 300, null); //设置地图中心点
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        if (i != 1000) return;
        locationsAdapter.setData(regeocodeResult.getRegeocodeAddress().getPois());
        SpannableStringBuilder msp = new SpannableStringBuilder("当前位置：\n" + regeocodeResult.getRegeocodeAddress().getFormatAddress());
        msp.setSpan(new ForegroundColorSpan(Color.parseColor("#5396FF")), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvMyLocation.setText(msp);
        locationJson.put("address", regeocodeResult.getRegeocodeAddress().getFormatAddress());
        poiSearch.searchPOIIdAsyn(regeocodeResult.getRegeocodeAddress().getPois().get(0).getPoiId());// 异步搜索
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (i != 1000) return;
        locationsAdapter.setData(poiResult.getPois());
        poiSearch.searchPOIIdAsyn(poiResult.getPois().get(0).getPoiId());// 异步搜索
        //地图移动到搜索内容位置区域
        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                new LatLng(poiResult.getPois().get(0).getLatLonPoint().getLatitude(),
                        poiResult.getPois().get(0).getLatLonPoint().getLongitude()),15,0,0)));
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
        initLocationInfo(poiItem);
    }

    private void initLocationInfo(PoiItem poiItem) {
        String cityCode = poiItem.getAdCode().substring(0, 4) + "00";
        locationJson.put("district", poiItem.getAdCode());
        locationJson.put("districtName", poiItem.getAdName());
        locationJson.put("city", cityCode);
        locationJson.put("cityName", poiItem.getCityName());
        locationJson.put("province", poiItem.getProvinceCode());
        locationJson.put("provinceName", poiItem.getProvinceName());
        locationsAdapter.setAdministrativeDivision(poiItem.getProvinceName()+poiItem.getCityName()+poiItem.getAdName());
        if (isSelect) {
            locationJson.put("address", poiItem.getSnippet() + poiItem.getTitle());
            locationJson.put("latitude", poiItem.getLatLonPoint().getLatitude());
            locationJson.put("longitude", poiItem.getLatLonPoint().getLongitude());
        }
        this.isSelect = false;
//        LogUtil.v(locationJson.toString());
    }

    private void setResult() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_LOCATION, locationJson.toJSONString());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}

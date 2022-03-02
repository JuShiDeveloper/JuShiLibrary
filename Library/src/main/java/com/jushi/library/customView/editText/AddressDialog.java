package com.jushi.library.customView.editText;

import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jushi.library.R;
import com.jushi.library.customView.wheelview.OnItemSelectedListener;
import com.jushi.library.customView.wheelview.WheelAdapter;
import com.jushi.library.customView.wheelview.WheelView;
import com.jushi.library.utils.LogUtil;
import com.nineoldandroids.animation.ObjectAnimator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 选择省市区底部弹窗
 * created by wyf on 2021-12-06
 */
public class AddressDialog extends Dialog {

    private View bgView;
    private LinearLayout llContainer;
    private WheelView wvProvince;
    private WheelView wvCity;
    private WheelView wvArea;
    private List<Address> addressData;
    private TextView tvCancel, tvConfirm;
    private int provinceIndex;
    private int cityIndex;
    private int areaIndex;
    private AddressInfo info = new AddressInfo();
    private OnSelectAddressListener listener;

    public AddressDialog(@NonNull Context context) {
        super(context, R.style.Translucent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_address_layout);
        initData();
        initView();
        setData();
        setListener();
    }

    private void initView() {
        bgView = findViewById(R.id.address_dialog_bg);
        llContainer = findViewById(R.id.ll_address_container);
        wvProvince = findViewById(R.id.wv_province);
        wvCity = findViewById(R.id.wv_city);
        wvArea = findViewById(R.id.wv_area);
        tvCancel = findViewById(R.id.tv_cancel);
        tvConfirm = findViewById(R.id.tv_confirm);
        wvProvince.setCurrentItem(0);
        wvCity.setCurrentItem(0);
        wvArea.setCurrentItem(0);
    }

    private void initData() {
        AssetManager assetManager = getContext().getAssets();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open("address_data.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
            addressData = JSONObject.parseArray(stringBuilder.toString(), Address.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setListener() {
        bgView.setOnClickListener(v -> dismiss());
        tvCancel.setOnClickListener(v -> dismiss());
        wvProvince.setOnItemSelectedListener(index -> {
            provinceIndex = index;
            wvCity.setAdapter(new Adapter(addressData.get(index).getChildren()));
            wvArea.setAdapter(new Adapter(addressData.get(provinceIndex).getChildren().get(0).getChildren()));
        });
        wvCity.setOnItemSelectedListener(index -> {
            cityIndex = index;
            wvArea.setAdapter(new Adapter(addressData.get(provinceIndex).getChildren().get(cityIndex).getChildren()));
        });
        wvArea.setOnItemSelectedListener(index -> areaIndex = index);
        tvConfirm.setOnClickListener(v -> {
            info.setProvinceName(addressData.get(provinceIndex).getLabel());
            info.setProvinceCode(addressData.get(provinceIndex).getValue());
            info.setCityName(addressData.get(provinceIndex).getChildren().get(cityIndex).getLabel());
            info.setCityCode(addressData.get(provinceIndex).getChildren().get(cityIndex).getValue());
            info.setAreaName(addressData.get(provinceIndex).getChildren().get(cityIndex).getChildren().get(areaIndex).getLabel());
            info.setAreaCode(addressData.get(provinceIndex).getChildren().get(cityIndex).getChildren().get(areaIndex).getValue());
            if (listener != null)
                listener.onAddressSelected(info);
            dismiss();
        });
    }


    private void setData() {
        wvProvince.setAdapter(new Adapter(addressData));
        wvCity.setAdapter(new Adapter(addressData.get(0).getChildren()));
        wvArea.setAdapter(new Adapter(addressData.get(0).getChildren().get(0).getChildren()));
    }

    @Override
    public void show() {
        super.show();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(llContainer, "translationY", dipToPx(180), 0);
        objectAnimator.setDuration(300);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();

        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(bgView, "alpha", 0.0f, 0.8f);
        objectAnimator1.setDuration(300);
        objectAnimator1.start();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(llContainer, "translationY", 0, dipToPx(185));
        objectAnimator.setDuration(300);
        objectAnimator.start();

        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(bgView, "alpha", 0.8f, 0f);
        objectAnimator1.setDuration(200);
        objectAnimator1.start();
    }

    private int dipToPx(float dip) {
        return (int) (getContext().getResources().getDisplayMetrics().density * dip + 0.5f);
    }

    public void setOnSelectAddressListener(OnSelectAddressListener listener) {
        this.listener = listener;
    }

    public interface OnSelectAddressListener {
        void onAddressSelected(AddressInfo addressInfo);
    }

    private class Adapter implements WheelAdapter {

        private List<Address> data;

        public Adapter(List<Address> data) {
            this.data = data;
        }

        @Override
        public int getItemsCount() {
            return data.size();
        }

        @Override
        public Object getItem(int index) {
            return data.get(index).getLabel();
        }

        @Override
        public int indexOf(Object o) {
            int index = 0;
            for (int i = 0; i < data.size(); i++) {
                if (o.equals(data.get(i).label)) {
                    index = i;
                }
            }
            return index;
        }
    }

    private static class Address {
        private List<Address> children;
        private String label;
        private int value;

        public List<Address> getChildren() {
            return children;
        }

        public void setChildren(List<Address> children) {
            this.children = children;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public static class AddressInfo {
        private String provinceName;
        private int provinceCode;
        private String cityName;
        private int cityCode;
        private String areaName;
        private int areaCode;

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public void setProvinceCode(int provinceCode) {
            this.provinceCode = provinceCode;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public void setCityCode(int cityCode) {
            this.cityCode = cityCode;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public void setAreaCode(int areaCode) {
            this.areaCode = areaCode;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public int getProvinceCode() {
            return provinceCode;
        }

        public String getCityName() {
            return cityName;
        }

        public int getCityCode() {
            return cityCode;
        }

        public String getAreaName() {
            return areaName;
        }

        public int getAreaCode() {
            return areaCode;
        }
    }
}

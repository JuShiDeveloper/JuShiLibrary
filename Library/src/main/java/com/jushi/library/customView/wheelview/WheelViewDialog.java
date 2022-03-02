package com.jushi.library.customView.wheelview;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jushi.library.R;
import com.jushi.library.utils.LogUtil;
import com.jushi.library.viewinject.ViewInjecter;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.List;

/**
 * 滚轮控件底部弹窗
 * created by wyf on 2021-10-25
 */
public class WheelViewDialog extends Dialog {

    private View bgView;
    private TextView tvCancel;
    private TextView tvConfirm;
    private LinearLayout llContainer;
    private WheelView wheelView;

    private List<WheelInfo> infos;
    private Adapter adapter;
    private int currentSelectIndex = 0;
    private OnConfirmClickListener listener;

    public WheelViewDialog(@NonNull Context context) {
        super(context, R.style.Translucent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_wheel_view);
        initView();
        setListener();
    }

    @SuppressLint("CutPasteId")
    private void initView() {
        bgView = findViewById(R.id.layout_nation_picker_view);
        tvCancel = findViewById(R.id.tv_cancel);
        tvConfirm = findViewById(R.id.tv_confirm);
        llContainer = findViewById(R.id.ll_container);
        wheelView = findViewById(R.id.wv_select);
        wheelView.setCurrentItem(currentSelectIndex);
    }

    private void setListener() {
        tvCancel.setOnClickListener(v -> this.dismiss());
        tvConfirm.setOnClickListener(v -> {
            this.dismiss();
            if (listener == null) return;
            listener.onConfirmClick(infos.get(currentSelectIndex));
        });
        bgView.setOnClickListener(v -> this.dismiss());
        wheelView.setOnItemSelectedListener(index -> this.currentSelectIndex = index);
    }

    public void setData(List<WheelInfo> infos) {
        this.infos = infos;
        adapter = new Adapter(this.infos);
    }

    public void setOnConfirmClickListener(OnConfirmClickListener listener) {
        this.listener = listener;
    }

    public WheelInfo getWheelInfo(){
        return infos.get(currentSelectIndex);
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
        wheelView.setAdapter(adapter);
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

    private class Adapter implements WheelAdapter {

        private List<WheelInfo> infos;

        public Adapter(List<WheelInfo> infos) {
            this.infos = infos;
        }

        @Override
        public int getItemsCount() {
            return infos.size();
        }

        @Override
        public Object getItem(int index) {
            return infos.get(index).getName();
        }

        @Override
        public int indexOf(Object o) {
            int index = 0;
            for (int i = 0; i < infos.size(); i++) {
                if (infos.get(i).getName().equals(o)) {
                    index = i;
                }
            }
            return index;
        }
    }

    public interface OnConfirmClickListener {
        void onConfirmClick(WheelInfo wheelInfo);
    }
}

package com.library.jushi.jushilibrary;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jushi.library.base.BaseFragment;
import com.jushi.library.viewinject.FindViewById;

public class TestFragment extends BaseFragment {
    @FindViewById(R.id.text)
    private TextView textView;

    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    protected void initViewWidget() {
        Log.v("yufei", "fragment initViewWidget()");
    }

    @Override
    protected void initData() {
        Log.v("yufei", "fragment initialize()");
    }

    @Override
    protected void setListener() {
        Log.v("yufei", "fragment setListener()");
    }

    public void setText(String text) {
        if (textView != null)
            textView.setText(text);
    }
}

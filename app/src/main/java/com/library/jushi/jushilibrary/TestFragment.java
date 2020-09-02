package com.library.jushi.jushilibrary;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jushi.library.base.BaseFragment;

public class TestFragment extends BaseFragment {
    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test, container,false);
    }

    @Override
    protected void initViewWidget() {
        Log.v("yufei","fragment initViewWidget()");
    }

    @Override
    protected void initData() {
        Log.v("yufei","fragment initialize()");
    }

    @Override
    protected void setListener() {
        Log.v("yufei","fragment setListener()");
    }
}

package com.jushi.library.router;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.jushi.library.base.BaseFragment;
import com.jushi.library.utils.LogUtil;


/**
 * Describe:路由器
 * create by wyf 2018/05/05
 */
public class FragmentRouter {

    public static @Nullable
    Fragment getFragment(String name) {
        BaseFragment fragment = null;
        try {
            Class fragmentClass = Class.forName(name);
            fragment = (BaseFragment) fragmentClass.newInstance();
        } catch (Exception e) {
            LogUtil.v("Router", "The fragment cannot be found");
        }
        return fragment;
    }
}

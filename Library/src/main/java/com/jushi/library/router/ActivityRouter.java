package com.jushi.library.router;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jushi.library.utils.Logger;

/**
 * Describe:路由器
 * create by wyf 2018/05/05
 */
public class ActivityRouter {


    private static void startActivity(Context context, Class clazz, Bundle bundle) {
        Intent intent = getIntent(context, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    private static Intent getIntent(Context context, Class clazz) {
        return new Intent(context, clazz);
    }

    /**
     * 带参数得跳转
     *
     * @param context
     * @param name
     * @param bundle
     */
    public static void startActivityForName(Context context, String name, Bundle bundle) {
        try {
            Class clazz = Class.forName(name);
            startActivity(context, clazz, bundle);
        } catch (ClassNotFoundException e) {
            Logger.v("Router", "The class cannot be found");
        }
    }

    /**
     * 不带参数得跳转
     *
     * @param context
     * @param name
     */
    public static void startActivityForName(Context context, String name) {
        try {
            Class clazz = Class.forName(name);
            startActivity(context, clazz, null);
        } catch (ClassNotFoundException e) {
            Logger.v("Router", "The class cannot be found");
        }
    }
}

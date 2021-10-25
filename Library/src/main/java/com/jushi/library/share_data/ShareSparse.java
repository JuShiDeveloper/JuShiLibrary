package com.jushi.library.share_data;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;


/**
 * SP数据缓存类
 */
public class ShareSparse {
    /**
     * USER类型
     */
    public static final String USER = "user";

    /**
     * 点击通知栏消息
     */
    public static final String CLICK_NOTICE = "click_notice";

    private SharedPreferences sp;
    private Context context;
    private static ShareSparse shareSparse;

    private ShareSparse(Context context) {
        this.context = context;
        sp = context.getSharedPreferences("ShareSparse", Context.MODE_PRIVATE);
    }

    public static ShareSparse getInstance(Context context) {
        if (shareSparse == null) {
            shareSparse = new ShareSparse(context);
        }
        return shareSparse;
    }

    public void putValue(String key, Object value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value.toString());
        editor.apply();
    }

    public Object getValue(String key) {
        String saveInfo = sp.getString(key, "");
        if (TextUtils.isEmpty(saveInfo)) return null;
        if (key.equals(USER)) {
            try {
                return JSONObject.parseObject(saveInfo, UserInfo.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            return saveInfo;
        }
        return null;
    }

    public void putValue(String key, boolean value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getValue(String key, boolean defVue) {
        return sp.getBoolean(key, defVue);
    }
}

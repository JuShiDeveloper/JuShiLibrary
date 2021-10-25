package com.jushi.library.manager;

import com.alibaba.fastjson.JSONObject;
import com.jushi.library.base.BaseApplication;
import com.jushi.library.base.BaseManager;
import com.jushi.library.share_data.ShareSparse;
import com.jushi.library.share_data.UserInfo;
import com.jushi.library.utils.LogUtil;

import java.util.List;

/**
 * 用户信息管理类
 * 登录信息
 * created by wyf on 2021-09-18
 */
public class UserManager extends BaseManager {
    private ShareSparse shareSparse;
    private UserInfo userInfo;

    @Override
    public void onManagerCreate(BaseApplication application) {
        shareSparse = ShareSparse.getInstance(application);
        Object saveInfo = shareSparse.getValue(ShareSparse.USER);
        userInfo = saveInfo == null ? null : (UserInfo) saveInfo;
    }

    public void saveUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
        LogUtil.v("保存用户信息: "+JSONObject.toJSON(userInfo));
        shareSparse.putValue(ShareSparse.USER, JSONObject.toJSON(userInfo));
    }

    public void clearUserInfo(){
        this.userInfo = null;
        shareSparse.putValue(ShareSparse.USER, "");
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void uploadAvatar(String avatar){
        userInfo.setAvatar(avatar);
        saveUserInfo(userInfo);
    }

    public UserInfo.OrganizationBean getOrganization() {
        return userInfo == null ? null : userInfo.getOrganization();
    }

    public List<UserInfo.RolesBean> getRoles() {
        return userInfo == null ? null : userInfo.getRoles();
    }

    public List<UserInfo.MenusBean> getMenus() {
        return userInfo == null ? null : userInfo.getMenus();
    }
}

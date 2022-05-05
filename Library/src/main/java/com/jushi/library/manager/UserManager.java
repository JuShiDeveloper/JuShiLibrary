package com.jushi.library.manager;

import android.support.annotation.IntDef;

import com.alibaba.fastjson.JSONObject;
import com.jushi.library.base.BaseApplication;
import com.jushi.library.base.BaseManager;
import com.jushi.library.share_data.ShareSparse;
import com.jushi.library.share_data.UserInfo;
import com.jushi.library.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息管理类
 * 登录信息
 * created by wyf on 2021-09-18
 */
public class UserManager extends BaseManager {
    //证件类型;0-其它，1-个人身份证人像面，2-个人身份证国徽面，3-企业身份证人像面，4-企业身份证国徽面，5-企业营业执照
    public static final int CER_TYPE_0 = 0;
    public static final int CER_TYPE_1 = 1;
    public static final int CER_TYPE_2 = 2;
    public static final int CER_TYPE_3 = 3;
    public static final int CER_TYPE_4 = 4;
    public static final int CER_TYPE_5 = 5;

    //资料审核状态   -1未提交资料，0-未审核，1-待审核，2-审核通过，3-审核不通过
    public static final int VERIFY_STATE_NONE = -1;
    public static final int VERIFY_STATE_NO = 0;
    public static final int VERIFY_STATE_WAIT = 1;
    public static final int VERIFY_STATE_PASS = 2;
    public static final int VERIFY_STATE_NO_PASS = 3;

    private ShareSparse shareSparse;
    private UserInfo userInfo;
    private UserInfo.UserEmployeeRolesBean currentShop;//当前所在店铺
    private String currentRoles = "";//当前角色  批发商、采购商、员工其中一种
    private List<String> shopRolesName = new ArrayList<>();
    private OnUserInfoClearListener userInfoClearListener;

    @Override
    public void onManagerCreate(BaseApplication application) {
        shareSparse = ShareSparse.getInstance(application);
        Object saveInfo = shareSparse.getValue(ShareSparse.USER);
        userInfo = saveInfo == null ? null : (UserInfo) saveInfo;
        initCurrentShop();
    }

    public void initCurrentShop() {
        if (userInfo != null && userInfo.getUserEmployeeRoles().size() > 0) {
            setCurrentShop(userInfo.getUserEmployeeRoles().get(0));
        }
    }

    public void saveUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
        LogUtil.v("保存用户信息: " + JSONObject.toJSON(userInfo));
        shareSparse.putValue(ShareSparse.USER, JSONObject.toJSON(userInfo));
    }

    public void clearUserInfo() {
        this.userInfo = null;
        this.currentShop = null;
        shareSparse.putValue(ShareSparse.USER, "");
        if (userInfoClearListener!=null){
            userInfoClearListener.onUserInfoClear();
        }
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void uploadAvatar(String avatar) {
        userInfo.setAvatar(avatar);
        saveUserInfo(userInfo);
    }

    public List<UserInfo.CertificatesBean> getCertificates() {
        return userInfo.getCertificates();
    }

    public UserInfo.UserEmployeeRolesBean getCurrentShop() {
        return currentShop;
    }

    public Boolean hasShop() {
        return currentShop != null||userInfo.getUserEmployeeRoles().size()>0;
    }

    public void setCurrentShop(UserInfo.UserEmployeeRolesBean currentShop) {
        this.currentShop = currentShop;
        this.userInfo.setCurrentShop(currentShop);
        initShopRolesName();
    }

    private void initShopRolesName() {//所在店铺角色名称
        shopRolesName.clear();
        if (currentShop==null)return;
        for (UserInfo.UserEmployeeRolesBean.RolesBean bean:currentShop.getRoles()){
            shopRolesName.add(bean.getName());
        }
    }

    public String getBossId() { //返回当前店铺老板id
        return currentShop == null ? "" : currentShop.getShop().getId() + "";
    }

    public int getCurrentShopIndex() {
        if (userInfo.getUserEmployeeRoles() == null || userInfo.getUserEmployeeRoles().size() == 0)
            return -1;
        if (currentShop == null) return 0;
        return userInfo.getUserEmployeeRoles().indexOf(currentShop);
    }

    public String getCurrentRoles() {
        return currentRoles;
    }

    public void setCurrentRoles(String currentRoles) {
        this.currentRoles = currentRoles;
    }

    /**
     * 判断所在店铺角色是否存在
     * @param shopRolesName
     * @return
     */
    public Boolean hasShopRoles(String shopRolesName){
        return this.shopRolesName.contains(shopRolesName);
    }

    /**
     * 获取证件图片实体
     *
     * @param picturePaths 图片上传到服务器返回的路径
     * @param type         图片类型
     * @return
     */
    public UserInfo.CertificatesBean getCertificate(String picturePaths, @CertificateType int type) {
        UserInfo.CertificatesBean bean = new UserInfo.CertificatesBean();
        bean.setName(getCertificateName(type));
        bean.setPicturePaths(picturePaths);
        bean.setType(type);
        return bean;
    }

    private String getCertificateName(int type) {
        switch (type) {//证件类型;0-其它，1-个人身份证人像面，2-个人身份证国徽面，3-企业身份证人像面，4-企业身份证国徽面，5-企业营业执照
            case CER_TYPE_0:
                return "其他";
            case CER_TYPE_1:
                return "个人身份证人像面";
            case CER_TYPE_2:
                return "个人身份证国徽面";
            case CER_TYPE_3:
                return "企业身份证人像面";
            case CER_TYPE_4:
                return "企业身份证国徽面";
            case CER_TYPE_5:
                return "企业营业执照";
        }
        return "其他";
    }

    public void setOnUserInfoClearListener(OnUserInfoClearListener listener){
        this.userInfoClearListener = listener;
    }

    public interface OnUserInfoClearListener{
        void onUserInfoClear();
    }

    @IntDef({CER_TYPE_0, CER_TYPE_1, CER_TYPE_2, CER_TYPE_3, CER_TYPE_4, CER_TYPE_5})
    @interface CertificateType {
    }

}

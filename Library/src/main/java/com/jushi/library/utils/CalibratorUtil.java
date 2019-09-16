package com.jushi.library.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * 校验器工具类
 * create time 2019/8/5
 *
 * @author JuShi
 */
public class CalibratorUtil {
    /**
     * 判断字符串是否符合手机号码格式
     * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188,198
     * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186,166
     * 电信号段: 133,149,153,170,173,177,180,181,189
     *
     * @param phoneNo 待检测的字符串
     * @return
     */

    public static boolean isMobileNO(String phoneNo) {
        // "[1]"代表下一位为数字可以是几，"[0-9]"代表可以为0-9中的一个，"[5,7,9]"表示可以是5,7,9中的任意一位,
        // [^4]表示除4以外的任何一个,\\d{9}"代表后面是可以是0～9的数字，有9位。
        String regex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8])|(19[8,9])|(166))\\d{8}$";
        if (TextUtils.isEmpty(phoneNo)) {
            return false;
        }
        return phoneNo.matches(regex);
    }

    /**
     * 手机号验证，除了10、11、12开头的，其他都开放通过验证
     *
     * @param phoneNo 待检测的字符串
     * @return true-表示验证通过 , false-表示不通过
     */
    public static boolean isPhoneNo(String phoneNo) {
        if (TextUtils.isEmpty(phoneNo)) {
            return false;
        }
        String regex = "^(1[^[0,1,2]])\\d{9}$";
        return phoneNo.matches(regex);
    }

    /**
     * 密码校验规则校验 （大小写敏感，即把A和a看作两个不同的字符）
     * 数字、字母、特殊字符两种及以上的组合 （大小写算不同种类）
     *
     * @param password 待检测的字符串
     * @return
     */
    public static boolean passwordRuleJudgeCaseSensitive(String password) {
        if (TextUtils.isEmpty(password)) {
            return false;
        }
        Pattern p = Pattern.compile("^(?![A-Z]*$)(?![a-z]*$)(?![0-9]*$)(?![^a-zA-Z0-9]*$)\\S+$");
        return p.matcher(password).find();
    }

    /**
     * 密码校验 （大小写不敏感，即把A和a看作两个相同的字符）
     * 数字、字母、特殊字符两种及以上的组合 （大小写算同一种类）
     *
     * @param password 待检测的字符串
     * @return
     */
    public static boolean passwordRuleJudgeCaseInsensitive(String password) {
        String singleRegex = "[a-zA-Z]{6,20}|[0-9]{6,20}|[\\x21-\\x2F\\x3A-\\x40\\x5B-\\x60\\x7A-\\x7E]{6,20}";
        boolean isSingle = password.matches(singleRegex);
        if (isSingle) return false;
        String regex = "[a-zA-Z0-9\\x21-\\x2F\\x3A-\\x40\\x5B-\\x60\\x7A-\\x7E]{6,20}";
        boolean matches = password.matches(regex);
        return matches;
    }

}

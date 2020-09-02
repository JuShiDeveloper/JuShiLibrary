package com.jushi.library.utils;

import android.text.TextUtils;
import android.text.format.Time;


import java.util.regex.Pattern;


/**
 * 身份证信息判断工具类
 * Created by wyf on 2018-09-23.
 */
public class IdCardUtil {

    /**
     * 判断身份证是否符合输入要求
     *
     * @param cardNo 身份证输入（必须18位前17位为数字，最后一位符合0-9|X范围）
     * @return true 符合输入要求
     */
    private static boolean isCardInputLegal(String cardNo) {
        String regStart = "^[0-9]*?[0-9|X]$";
        return Pattern.compile(regStart).matcher(cardNo).find();
    }

    /**
     * 验证身份证号码的正确性
     *
     * @param cardNo 身份证号
     * @return true 身份证合法
     */
    public static boolean isIdCard(String cardNo) {
        if (TextUtils.isEmpty(cardNo)) {
            return false;
        }

        if (cardNo.length() == 15) {
            return is15IdCard(cardNo);
        }

        if (cardNo.length() != 18) {
            return false;
        }

        if (!isCardInputLegal(cardNo)) {
            return false;
        }

        int[] intArr = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        int sum = 0;
        for (int i = 0; i < intArr.length; i++) {
            sum += Character.digit(cardNo.charAt(i), 10) * intArr[i];
        }
        int mod = sum % 11;
        int[] intArr2 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] intArr3 = {1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2};
        String matchDigit = "";
        for (int i = 0; i < intArr2.length; i++) {
            int j = intArr2[i];
            if (j == mod) {
                matchDigit = String.valueOf(intArr3[i]);
                if (intArr3[i] > 57) {
                    matchDigit = String.valueOf((char) intArr3[i]);
                }
            }
        }
        if (matchDigit.equals(cardNo.substring(cardNo.length() - 1))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证15位身份证
     *
     * @param cardNo 身份证号
     * @return
     */
    private static boolean is15IdCard(String cardNo) {
        int year, month, day;
        year = Integer.parseInt(cardNo.substring(6, 8)) + 1900;
        month = Integer.parseInt(cardNo.substring(8, 10));
        day = Integer.parseInt(cardNo.substring(10, 12));
        if (month <= 0 || month > 12) {
            return false;
        }

        return isDayInMonth(day, month, year);
    }

    private static boolean isDayInMonth(int day, int month, int year) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                if (day < 31) {
                    return true;
                } else {
                    return false;
                }

            case 4:
            case 6:
            case 9:
            case 11:
                if (day < 30) {
                    return true;
                } else {
                    return false;
                }
            case 2:
                if (doubleYear(year)) {
                    if (day < 29) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    if (day < 28) {
                        return true;
                    } else {
                        return false;
                    }
                }
        }
        return false;
    }

    private static boolean doubleYear(int year) {
        return (year % 4 == 0 && year % 100 != 0 || year % 400 == 0);
    }

    /**
     * 根据身份证获取年龄
     *
     * @param cardNo 身份证号
     */
    public static int getAge(String cardNo) {
        int temp1 = Integer.valueOf(cardNo.substring(6, 10));
        int temp2 = Integer.valueOf(cardNo.substring(10, 12));
        int temp3 = Integer.valueOf(cardNo.substring(12, 14));
        Time time = new Time("GMT+8");
        time.setToNow();
        int year = time.year;
        int month = time.month;
        int day = time.monthDay;
        int age = year - temp1 - 1;
        if (month > temp2) {
            age = age + 1;
        }
        if (month == temp2) {
            if (day >= temp3) {
                age = age + 1;
            }
        }
        return age;
    }

    /**
     * 根据生日获取年龄
     *
     * @param birthday 身份证号
     */
    public static int getAgeFromBirthday(String birthday) {
        int temp1 = Integer.valueOf(birthday.substring(0, 4));
        int temp2 = Integer.valueOf(birthday.substring(4, 6));
        int temp3 = Integer.valueOf(birthday.substring(6, 8));
        Time time = new Time("GMT+8");
        time.setToNow();
        int year = time.year;
        int month = time.month + 1;
        int day = time.monthDay;
        int age = year - temp1 - 1;

        if (month > temp2) {
            age = age + 1;
        }
        if (month == temp2) {
            if (day >= temp3) {
                age = age + 1;
            }
        }
        return age;
    }

    /**
     * 根据身份证获取生日
     *
     * @param cardNo 身份证号
     */
    public static String getBirthday(String cardNo) {
        String temp1 = cardNo.substring(6, 10);
        String temp2 = cardNo.substring(10, 12);
        String temp3 = cardNo.substring(12, 14);
        return temp1 + temp2 + temp3;
    }

    public static String getBirthdayString(String cardNo) {
        String temp1 = cardNo.substring(6, 10);
        String temp2 = cardNo.substring(10, 12);
        String temp3 = cardNo.substring(12, 14);
        return temp1 + "-" + temp2 + "-" + temp3;
    }

    /**
     * 根据身份证获取性别
     *
     * @param cardNo 身份证号
     * @return 0 男， 1：女，2：未知
     */
    public static int getGender(String cardNo) {
        String sex = cardNo.substring(cardNo.length() - 2, cardNo.length() - 1);
        if (Integer.valueOf(sex) % 2 == 1)
            return 0; // 男
        if (Integer.valueOf(sex) % 2 == 0)
            return 1;
        return 2;
    }

}

package com.jushi.library.utils;

import android.annotation.SuppressLint;
import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期、时间工具类
 * create time 2019/7/25 从原来的kotlin类替换成Java类
 */
@SuppressLint("SimpleDateFormat")
public class DateUtil {

    /**
     * 时间日期格式化到日月年
     */
    public static String dateFormatMDY = "MM/dd/yyyy";
    /**
     * 时间日期格式化到年月日时分秒.
     */
    public static String dateFormatYMDHMS = "yyyy-MM-dd HH:mm:ss";
    /**
     * 时间日期格式化到年月日.
     */
    public static String dateFormatYMD = "yyyy-MM-dd";
    /**
     * 时间日期格式化到年月.
     */
    public static String dateFormatYM = "yyyy-MM";
    /**
     * 时间日期格式化到年月日时分.
     */
    public static String dateFormatYMDHM = "yyyy-MM-dd HH:mm";
    /**
     * 时间日期格式化到月日.
     */
    public static String dateFormatMD = "MM/dd";
    /**
     * 时分秒.
     */
    public static String dateFormatHMS = "HH:mm:ss";
    /**
     * 时分.
     */
    public static String dateFormatHM = "HH:mm";

    /**
     * 系统当前时间
     * <br></br>时间日期格式化到年月日时分秒
     *
     * @return
     */
    public static String dateYMDHMS() {
        return new SimpleDateFormat(dateFormatYMDHMS).format(new Date());
    }

    /**
     * 系统当前时间
     * <br></br>时间日期格式化到年月日时分
     *
     * @return
     */
    public static String dateYMDHM() {
        return new SimpleDateFormat(dateFormatYMDHM).format(new Date());
    }

    /**
     * 系统当前时间
     * <br></br>时间日期格式化到年月日
     *
     * @return
     */
    public static String dateYMD() {
        return new SimpleDateFormat(dateFormatYMD).format(new Date());
    }

    /**
     * 系统当前时间
     * <br></br>时间日期格式化到时分秒
     *
     * @return
     */
    public static String dateHMS() {
        return new SimpleDateFormat(dateFormatHMS).format(new Date());
    }

    /**
     * 系统当前时间
     * <br></br>时间日期格式化到时分
     *
     * @return
     */
    public static String dateHM() {
        return new SimpleDateFormat(dateFormatHM).format(new Date());
    }

    /**
     * 系统当前时间戳
     *
     * @return
     */
    public static long dateCurrentLong() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前日期，将字符串转换为整数yyyyMMdd
     *
     * @return
     */
    public static int dateCurrentIntYMD() {
        return Integer.parseInt(DateFormat.format("yyyyMMdd", new Date()).toString());
    }

    /**
     * 根据时间获取到Long的时间
     *
     * @param date
     * @return
     */
    public static long getDateLong(Date date) {
        return date.getTime();
    }

    /**
     * 根据long时间以及自定义format格式获取到日期
     *
     * @param date   时间戳
     * @param format 格式类型 （如：yyyy-MM-dd、yyyy/MM/dd、yyyy-MM-dd HH:mm:ss、等）
     * @return
     */
    public static String getDateFormatCustom(long date, String format) {
        return new SimpleDateFormat(format).format(new Date(date));
    }

    /**
     * 根据时间获取到Long的时间
     *
     * @param date
     * @return
     */
    public static long getDateLong(String date) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormatYMDHMS);
        try {
            return getDateLong(format.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 根据long 转为为响应格式的时间
     * <br></br>时间日期格式化到年月日时分秒
     *
     * @return
     */
    public static String getDateYMDHMS(long date) {
        return new SimpleDateFormat(dateFormatYMDHMS).format(new Date(date));
    }

    /**
     * 根据long 转为为响应格式的时间
     * <br></br>时间日期格式化到年月日
     *
     * @return
     */
    public static String getDateYMD(long date) {
        return new SimpleDateFormat(dateFormatYMD).format(new Date(date));
    }

    /**
     * 根据long 转为为响应格式的时间
     * <br></br>时间日期格式化到日月年
     *
     * @return
     */
    public static String getDateDMY(long date) {
        return new SimpleDateFormat(dateFormatMDY).format(new Date(date));
    }

    /**
     * 根据long 转为为响应格式的时间
     * <br></br>时间日期格式化到年月日时分
     *
     * @return
     */
    public static String getDateYMDHM(long date) {
        return new SimpleDateFormat(dateFormatYMDHM).format(new Date(date));
    }

    /**
     * 根据long 转为为响应格式的时间
     * <br></br>时间日期格式化到时分秒
     *
     * @return
     */
    public static String getDateHMS(long date) {
        return new SimpleDateFormat(dateFormatHMS).format(new Date(date));
    }

    /**
     * 两个时间比较
     *
     * @param date1
     * @param date2
     * @return 1：date1 在 date2 后, -1 date2 在 date1 后 ,0 相同
     */
    public static int compare(Long date1, Long date2) {
        int compare = 0;
        if (date1 > date2) {
            compare = 1;
        } else if (date1 < date2) {
            compare = -1;
        }
        return compare;
    }

    /**
     * 两个时间比较
     *
     * @param date1
     * @param date2
     * @return 1：date1 在 date2 后, -1 date2 在 date1 后 ,0 相同
     */
    public static int compare(Date date1, Date date2) {
        try {
            return compare(date1.getTime(), date2.getTime());
        } catch (Exception e) {
            return -2;
        }
    }

    /**
     * 两个时间比较
     *
     * @param date1
     * @param date2
     * @return 1：date1 在 date2 后, -1 date2 在 date1 后 ,0 相同
     */
    public static int compare(String date1, String date2) {
        Date mDate1 = null;
        Date mDate2 = null;
        try {
            SimpleDateFormat srcF = new SimpleDateFormat(dateFormatYMDHMS);
            SimpleDateFormat dstF = new SimpleDateFormat(dateFormatYMDHMS);
            mDate1 = srcF.parse(date1);
            mDate2 = dstF.parse(date2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return compare(mDate1, mDate2);
    }

    /**
     * 描述：判断是否是闰年()
     * <p>
     * (year能被4整除 并且 不能被100整除) 或者 year能被400整除,则该年为闰年.
     *
     * @param year 年代（如2012）
     * @return boolean 是否为闰年
     */
    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 400 != 0 || year % 400 == 0;
    }

    /**
     * 描述：获取偏移之后的Date.
     *
     * @param date 日期时间
     * @param day  偏移天(值大于0,表示+,值小于0,表示－)
     * @return Date 偏移之后的日期时间
     */
    public static Date getDateOffsetDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(date);
            calendar.add(Calendar.DATE, day);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendar.getTime();
    }

    /**
     * 描述：获取偏移之后的Date.
     *
     * @param date 日期时间
     * @param hour 偏移小时(值大于0,表示+,值小于0,表示－)
     * @return Date 偏移之后的日期时间
     */
    public static Date getDateOffsetHOUR(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(date);
            calendar.add(Calendar.HOUR, hour);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendar.getTime();
    }

    /**
     * 描述：获取偏移之后的Date.
     *
     * @param date   日期时间
     * @param minute 偏移分钟(值大于0,表示+,值小于0,表示－)
     * @return Date 偏移之后的日期时间
     */
    public static Date getDateOffsetMinute(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(date);
            calendar.add(Calendar.MINUTE, minute);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendar.getTime();
    }


    /**
     * 一个星期后
     * 描述：获取偏移之后的Date.
     *
     * @param date 日期时间
     * @return Date 偏移之后的日期时间
     */
    public static Date getDateOffsetWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(date);
            calendar.add(Calendar.DATE, 7);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendar.getTime();
    }

    /**
     * 描述：计算两个日期所差的天数.
     *
     * @param date1 第一个时间的毫秒表示
     * @param date2 第二个时间的毫秒表示
     * @return int 所差的天数
     */
    public static int getOffsetDay(Long date1, Long date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        //先判断是否同年
        int y1 = calendar1.get(Calendar.YEAR);
        int y2 = calendar2.get(Calendar.YEAR);
        int d1 = calendar1.get(Calendar.DAY_OF_YEAR);
        int d2 = calendar2.get(Calendar.DAY_OF_YEAR);
        int maxDays = 0;
        int day = 0;
        if (y1 - y2 > 0) {
            maxDays = calendar2.getActualMaximum(Calendar.DAY_OF_YEAR);
            day = d1 - d2 + maxDays;
        } else if (y1 - y2 < 0) {
            maxDays = calendar1.getActualMaximum(Calendar.DAY_OF_YEAR);
            day = d1 - d2 - maxDays;
        } else {
            day = d1 - d2;
        }
        return day;
    }

    /**
     * 描述：计算两个日期所差的小时数.
     *
     * @param date1 第一个时间的毫秒表示
     * @param date2 第二个时间的毫秒表示
     * @return int 所差的小时数
     */
    public static int getOffsetHour(Long date1, Long date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        int h1 = calendar1.get(Calendar.HOUR_OF_DAY);
        int h2 = calendar2.get(Calendar.HOUR_OF_DAY);
        int h = 0;
        int day = getOffsetDay(date1, date2);
        h = h1 - h2 + day * 24;
        return h;
    }

    /**
     * 描述：计算两个日期所差的分钟数.
     *
     * @param date1 第一个时间的毫秒表示
     * @param date2 第二个时间的毫秒表示
     * @return int 所差的分钟数
     */
    public static int getOffsetMinutes(Long date1, Long date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        int m1 = calendar1.get(Calendar.MINUTE);
        int m2 = calendar2.get(Calendar.MINUTE);
        int h = getOffsetHour(date1, date2);
        int m = 0;
        m = m1 - m2 + h * 60;
        return m;
    }

    /**
     * 获得GMT时间字符串 (格林威治标准时间)
     *
     * @param timemills 1970以来的毫秒数
     * @return
     */
    public static String getGMTTimeString(Long timemills) {
        Date date = new Date(timemills);
        return date.toGMTString();
    }

    /**
     * 根据活动结束日期获得活动剩余时间
     *
     * @param endTime 结束日期时间戳
     * @return 距离活动结束的剩余时间 （单位：秒）
     */
    public static Long getLeadTime(String endTime) {
        long nd = 1000 * 24 * 60 * 60;//一天的毫秒数
        long nh = 1000 * 60 * 60;//一小时的毫秒数
        long nm = 1000 * 60;//一分钟的毫秒数
        long ns = 1000;//一秒钟的毫秒数
        long diff = 0L;
        diff = Long.parseLong(endTime) - System.currentTimeMillis();
        long day = diff / nd;//计算差多少天
        long hour = diff % nd / nh;//计算差多少小时
        long min = diff % nd % nh / nm;//计算差多少分钟
        long sec = diff % nd % nh % nm / ns;//计算差多少
//        Log.v("yufei", day + "天" + hour + "小时" + min + "分钟" + sec + "秒");
        diff = diff / 1000;
        return diff;
    }

    /**
     * 根据活动结束日期获得活动剩余时间
     *
     * @param endTime 结束日期时间戳
     * @return 剩余时间 HH:mm:ss
     */
    public static String getLeadHMS(long endTime) {
        long nd = 1000 * 24 * 60 * 60;//一天的毫秒数
        long nh = 1000 * 60 * 60;//一小时的毫秒数
        long nm = 1000 * 60;//一分钟的毫秒数
        long ns = 1000;//一秒钟的毫秒数
        long diff = endTime - System.currentTimeMillis();
        long day = diff / nd;//计算差多少天
        long hour = diff % nd / nh;//计算差多少小时
        long min = diff % nd % nh / nm;//计算差多少分钟
        long sec = diff % nd % nh % nm / ns;//计算差多少
        String h = hour < 10 ? "0" + hour : hour + "";
        String m = min < 10 ? "0" + min : min + "";
        String s = sec < 10 ? "0" + sec : sec + "";
        return h + ":" + m + ":" + s;
    }

}

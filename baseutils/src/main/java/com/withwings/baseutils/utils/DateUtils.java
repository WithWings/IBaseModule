package com.withwings.baseutils.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具
 * 创建：WithWings 时间：2017/11/2.
 * Email:wangtong1175@sina.com
 */
public class DateUtils {

    /**
     * 英文简写如：2010
     */
    public static String FORMAT_Y = "yyyy";

    /**
     * 英文简写如：12:01
     */
    public static String FORMAT_HM = "HH:mm";

    /**
     * 英文简写如：1-12 12:01
     */
    public static String FORMAT_MDHM = "MM-dd HH:mm";

    /**
     * 英文简写（默认）如：2010-12-01
     */
    public static String FORMAT_YMD = "yyyy-MM-dd";

    /**
     * 英文全称  如：2010-12-01 23:15
     */
    public static String FORMAT_YMDHM = "yyyy-MM-dd HH:mm";

    /**
     * 英文全称  如：2010-12-01 23:15:06
     */
    public static String FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 精确到毫秒的完整时间    如：yyyy-MM-dd HH:mm:ss.S
     */
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";

    /**
     * 精确到毫秒的完整时间    如：yyyy-MM-dd HH:mm:ss.S
     */
    public static String FORMAT_FULL_SN = "yyyyMMddHHmmssS";

    /**
     * 中文简写  如：2010年12月01日
     */
    public static String FORMAT_YMD_CN = "yyyy年MM月dd日";

    /**
     * 中文简写  如：2010年12月01日  12时
     */
    public static String FORMAT_YMDH_CN = "yyyy年MM月dd日 HH时";

    /**
     * 中文简写  如：2010年12月01日  12时12分
     */
    public static String FORMAT_YMDHM_CN = "yyyy年MM月dd日 HH时mm分";

    /**
     * 中文全称  如：2010年12月01日  23时15分06秒
     */
    public static String FORMAT_YMDHMS_CN = "yyyy年MM月dd日  HH时mm分ss秒";

    /**
     * 精确到毫秒的完整中文时间
     */
    public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

    public static Calendar calendar;

    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static Date getCurrentDate() {
        return new Date();
    }

    public static Calendar getCurrentCalendar() {
        //  int year = c.get(Calendar.YEAR); 年
        return Calendar.getInstance();
    }

    /**
     * 格式化字符串，有默认格式
     *
     * @param aLong 毫秒值
     * @return 结果
     */
    public static String formatDate(long aLong) {
        String format = "yyyy-MM-dd hh:mm:ss";
        return formatDate(format, aLong);
    }

    /**
     * 格式化字符串，需要提供格式化的格式
     *
     * @param format 格式化的格式
     * @param aLong  毫秒值
     * @return 结果
     */
    public static String formatDate(String format, long aLong) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.CHINA);
        Date dateFormat = new Date(aLong);
        return simpleDateFormat.format(dateFormat);
    }

    /**
     * 字符串格式化为 Date
     *
     * @param str 字符串
     * @return Date
     */
    public static Date str2Date(String str) {
        return str2Date(str, null);
    }

    /**
     * 字符串转 Date
     *
     * @param str    字符串
     * @param format 格式
     * @return Date
     */
    public static Date str2Date(String str, String format) {
        if (str == null || str.length() == 0) {
            return null;
        }
        if (format == null || format.length() == 0) {
            format = FORMAT;
        }
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
            date = sdf.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 字符串转 Calendar 对象
     *
     * @param str 字符串
     * @return Calendar
     */
    public static Calendar str2Calendar(String str) {
        return str2Calendar(str, null);
    }


    /**
     * 字符串转 Calendar 对象
     *
     * @param str    字符串
     * @param format 格式
     * @return Calendar
     */
    public static Calendar str2Calendar(String str, String format) {

        Date date = str2Date(str, format);
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar;
    }

    /**
     * 日期转字符串
     *
     * @param calendar 日期
     * @return 字符串
     */
    public static String calendar2Str(Calendar calendar) {// yyyy-MM-dd HH:mm:ss
        return calendar2Str(calendar, null);
    }

    /**
     * 日期转字符串
     *
     * @param calendar 日期
     * @param format   格式
     * @return 字符串
     */
    public static String calendar2Str(Calendar calendar, String format) {
        if (calendar == null) {
            return null;
        }
        return date2Str(calendar.getTime(), format);
    }


    /**
     * 日期转字符串
     *
     * @param date 日期
     * @return 字符串
     */
    public static String date2Str(Date date) {// yyyy-MM-dd HH:mm:ss
        return date2Str(date, null);
    }


    /**
     * 日期转字符串
     *
     * @param date   日期
     * @param format 格式
     * @return 字符串
     */
    public static String date2Str(Date date, String format) {// yyyy-MM-dd HH:mm:ss
        if (date == null) {
            return null;
        }
        if (format == null || format.length() == 0) {
            format = FORMAT;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.CHINA);
        return simpleDateFormat.format(date);
    }


    /**
     * 在日期上增加数个整月
     *
     * @param date 日期
     * @param n    要增加的月数
     * @return 增加数个整月
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();

    }


    /**
     * 在日期上增加天数
     *
     * @param date 日期
     * @param n    要增加的天数
     * @return 增加之后的天数
     */
    public static Date addDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, n);
        return cal.getTime();

    }

    /**
     * 按默认格式的字符串距离今天的天数
     *
     * @param time 日期毫秒值
     * @return 按默认格式的字符串距离今天的天数
     */
    public static int countDays(long time) {
        long t = Calendar.getInstance().getTime().getTime();
        return (int) (t / 1000 - time / 1000) / 3600 / 24;
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param big 靠后日期
     * @param small 靠前的日期
     * @return 相差日期
     */
    public static int differentDaysByMillisecond(long big, long small) {
        return (int) ((big - small) / (1000 * 3600 * 24));
    }

}

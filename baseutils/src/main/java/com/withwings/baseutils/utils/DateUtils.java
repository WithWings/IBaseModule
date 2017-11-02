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

    public static Calendar calendar;

    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

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
    } public static Date str2Date(String str) {
        return str2Date(str, null);
    }

    /**
     * 字符串转 Date
     * @param str 字符串
     * @param format 格式
     * @return Date 对象
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
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }


    public static Calendar str2Calendar(String str) {
        return str2Calendar(str, null);
    }


    public static Calendar str2Calendar(String str, String format) {

        Date date = str2Date(str, format);
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        return c;
    }


    public static String date2Str(Calendar c) {// yyyy-MM-dd HH:mm:ss
        return date2Str(c, null);
    }


    public static String date2Str(Calendar c, String format) {
        if (c == null) {
            return null;
        }
        return date2Str(c.getTime(), format);
    }


    public static String date2Str(Date d) {// yyyy-MM-dd HH:mm:ss
        return date2Str(d, null);
    }


    public static String date2Str(Date d, String format) {// yyyy-MM-dd HH:mm:ss
        if (d == null) {
            return null;
        }
        if (format == null || format.length() == 0) {
            format = FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String s = sdf.format(d);
        return s;
    }

}

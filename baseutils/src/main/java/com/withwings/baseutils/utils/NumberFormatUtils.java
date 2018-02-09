package com.withwings.baseutils.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 数字格式化
 * 创建：WithWings 时间 2018/1/19
 * Email:wangtong1175@sina.com
 */
@SuppressWarnings({"unused", "WeakerAccess", "ConstantConditions", "SameParameterValue", "SuspiciousNameCombination", "UnusedReturnValue"})
public class NumberFormatUtils {

    /**
     * 千位分隔符保留两位小数
     *
     * @param number 金额
     * @return 格式化后的字符串
     */
    public static String moneyFormat(String number) {
        return formatNumber(number, "#,##0.00;(#)");
    }

    /**
     * 保留两位小数
     *
     * @param number 金额
     * @return 格式化后的字符串
     */
    public static String doubleFormat(String number) {
        return formatNumber(number, "######0.00");
    }

    public static String formatNumber(String number, String format) {
        BigDecimal bigDecimal = new BigDecimal(number);
        //,代表分隔符
        //0.后面的##代表位数 如果换成0 效果就是位数不足0补齐
        DecimalFormat decimalFormat = new DecimalFormat(format);
        return decimalFormat.format(bigDecimal);
    }

    public static String formatNumber(Object number, String format) {
        //,代表分隔符
        //0.后面的##代表位数 如果换成0 效果就是位数不足0补齐
        DecimalFormat decimalFormat = new DecimalFormat(format);
        return decimalFormat.format(number);
    }

}

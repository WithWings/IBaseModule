package com.withwings.baseutils.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SP存储
 * 创建：WithWings 时间 2018/2/9
 * Email:wangtong1175@sina.com
 */
@SuppressWarnings({"unused", "WeakerAccess", "SameParameterValue", "UnusedReturnValue"})
public class SPUtils {

    public static String PREFERENCE_KEY = "WithWings";

    private SPUtils() {
        throw new AssertionError();
    }

    /**
     * 存储 String
     * @param context 上下文
     * @param key key
     * @param value value
     * @return 保存状态
     */
    public static boolean putString(Context context, String key, String value) {
        SharedPreferences settings = context.getApplicationContext().getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * 获取存储的字符串
     * @param context 上下文
     * @param key key
     * @return 获得对应值，如果取不到返回 null
     */
    public static String getString(Context context, String key) {
        return getString(context, key, null);
    }

    /**
     * 获取存储的字符串
     * @param context 上下文
     * @param key key
     * @param defaultValue 默认值
     * @return 如果取不到返回默认值
     */
    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }

    /**
     * 存储 int
     * @param context 上下文
     * @param key key
     * @param value value
     * @return 保存是否成功
     */
    public static boolean putInt(Context context, String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * 取出存储的值
     * @param context 上下文
     * @param key key
     * @return 如果取不出返回 -1
     */
    public static int getInt(Context context, String key) {
        return getInt(context, key, -1);
    }

    /**
     * 取出存储的值
     * @param context 上下文
     * @param key key
     * @param defaultValue 默认值
     * @return 如果取不到返回默认值
     */
    public static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
        return settings.getInt(key, defaultValue);
    }

    /**
     * 存储 long 值
     * @param context 上下文
     * @param key key
     * @param value value
     * @return 存储是否成功
     */
    public static boolean putLong(Context context, String key, long value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * 取出存储的 long
     * @param context 上下文
     * @param key key
     * @return 取不出返回 -1
     */
    public static long getLong(Context context, String key) {
        return getLong(context, key, -1);
    }

    /**
     * 取出存储的 long
     * @param context 上下文
     * @param key key
     * @param defaultValue defaultValue
     * @return 取不到返回默认值
     */
    public static long getLong(Context context, String key, long defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
        return settings.getLong(key, defaultValue);
    }

    /**
     * 存储 float 数值
     * @param context 上下文
     * @param key key
     * @param value value
     * @return 存储是否成功
     */
    public static boolean putFloat(Context context, String key, float value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * 获得存储的 float
     * @param context 上下文
     * @param key key
     * @return 取不出返回 -1
     */
    public static float getFloat(Context context, String key) {
        return getFloat(context, key, -1);
    }

    /**
     * 取出存储的 float
     * @param context 上下文
     * @param key key
     * @param defaultValue 默认值
     * @return 取不出返回默认值
     */
    public static float getFloat(Context context, String key, float defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
        return settings.getFloat(key, defaultValue);
    }

    /**
     * 存储 boolean 值
     * @param context 上下文
     * @param key key
     * @param value value
     * @return 存储是否成功
     */
    public static boolean putBoolean(Context context, String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * 取出存储的 boolean
     * @param context 上下文
     * @param key key
     * @return 取不到返回 false
     */
    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    /**
     * 取出存储的 boolean
     * @param context 上下文
     * @param key key
     * @param defaultValue 默认值
     * @return 取不到返回默认值
     */
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }
}
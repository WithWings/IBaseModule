package com.withwings.baseutils.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Activity 跳转工具类
 * 创建：WithWings 时间 2018/3/5
 * Email:wangtong1175@sina.com
 */
@SuppressWarnings({"unused", "SameParameterValue", "WeakerAccess"})
public class ActivityUtils {

    /**
     * 跳转界面
     *
     * @param context       上下文
     * @param activityClass 跳转界面
     */
    public static void startActivity(Context context, Class<? extends Activity> activityClass) {
        startActivity(context, activityClass, null);
    }

    /**
     * 跳转界面
     *
     * @param context       上下文
     * @param activityClass 跳转界面
     * @param bundle        信息类
     */
    public static void startActivity(Context context, Class<? extends Activity> activityClass, Bundle bundle) {
        Intent intent = new Intent(context, activityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    /**
     * 打开某个界面 singleTask 效果
     *
     * @param context       上下文
     * @param activityClass 跳转界面
     */
    public void startActivityForOnlyOne(Context context, Class<? extends Activity> activityClass) {
        Intent intent = new Intent(context, activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    /**
     * 打开某个界面 singleTask 效果
     *
     * @param context       上下文
     * @param activityClass 跳转界面
     * @param bundle        信息类
     */
    public void startActivityForOnlyOne(Context context, Class<? extends Activity> activityClass, Bundle bundle) {
        Intent intent = new Intent(context, activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
}

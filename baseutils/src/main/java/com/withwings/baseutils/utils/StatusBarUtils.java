package com.withwings.baseutils.utils;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.DisplayMetrics;

/**
 * 状态栏工具类:已适配 8.0 通知栏 如果使用请确保 targetSdkVersion 已经指定到了26或者更高
 * 创建：WithWings 时间 2018/2/24
 * Email:wangtong1175@sina.com
 */
@SuppressWarnings({"unused", "DanglingJavadoc", "WeakerAccess", "SameParameterValue"})
public class StatusBarUtils {

    /**
     * 单个通知渠道注册：重复id会覆盖，新的 id 会新增，上次注册本次不注册并不会删除，但是如果是新用户则仍然没有该渠道，发送消息会 Toast 异常。
     *
     * @param context     上下文
     * @param channelId   渠道标记
     * @param channelName 渠道名
     * @param importance  渠道权限 ：
     *                    NotificationManager.IMPORTANCE_DEFAULT ：发出提示音显示在状态栏
     *                    NotificationManager.IMPORTANCE_NONE ：关闭，即使用户手动打开也仅相当于 IMPORTANCE_LOW
     *                    NotificationManager.IMPORTANCE_MIN ：仅发出提示音：比如创建界面角标的时候使用
     *                    NotificationManager.IMPORTANCE_LOW ：显示在状态栏
     *                    NotificationManager.IMPORTANCE_HIGH ：发出提示音显示在状态栏并在屏幕上弹出通知
     *                    NotificationManager.IMPORTANCE_UNSPECIFIED ：未指定，无法使用
     */
    @TargetApi(Build.VERSION_CODES.O)
    public static void createNotificationChannel(Context context, String channelId, String channelName, int importance) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    /**
     * 删除渠道，但是注意，这里是会给老用户展示删除记录的。
     *
     * @param context   上下文
     * @param channelId 渠道Id
     */
    @TargetApi(Build.VERSION_CODES.O)
    public static void deleteNotificationChannel(Context context, String channelId) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.deleteNotificationChannel(channelId);
        }
    }

    /**
     * 基本弹框，无法点击
     *
     * @param context   上下文
     * @param channelId 渠道id
     * @param title     标题
     * @param text      文字
     */
    public static void sendMessage(Context context, String channelId, String title, String text) {
        sendMessage(context, channelId, title, text, 0, null);
    }

    /**
     * 基本弹框，无法点击，带有数字
     *
     * @param context   上下文
     * @param channelId 渠道id
     * @param title     标题
     * @param text      文字
     * @param number    消息数
     */
    public static void sendMessage(Context context, String channelId, String title, String text, int number) {
        sendMessage(context, channelId, title, text, number, null);
    }

    /**
     * 基本弹框，可以点击，不带有数字
     *
     * @param context       上下文
     * @param channelId     渠道id
     * @param title         标题
     * @param text          文字
     * @param pendingIntent 点击动作
     */
    public static void sendMessage(Context context, String channelId, String title, String text, PendingIntent pendingIntent) {
        sendMessage(context, channelId, title, text, 0, pendingIntent);
    }

    /**
     * 基本弹框，可以点击，不带有数字
     *
     * @param context   上下文
     * @param channelId 渠道id
     * @param title     标题
     * @param text      文字
     * @param intent    点击动作未封装
     */
    public static void sendMessage(Context context, String channelId, String title, String text, Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        sendMessage(context, channelId, title, text, 0, pendingIntent);
    }

    /**
     * 发送消息
     *
     * @param context       上下文
     * @param channelId     渠道id
     * @param title         标题
     * @param text          文字
     * @param number        消息数
     * @param pendingIntent 点击动作
     */
    public static void sendMessage(Context context, String channelId, String title, String text, int number, PendingIntent pendingIntent) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId).setContentTitle(title).setContentText(text).setWhen(System.currentTimeMillis()).setSmallIcon(getIcon(context)).setLargeIcon(BitmapFactory.decodeResource(context.getResources(), getIcon(context))).setAutoCancel(true);
        if (number > 0) {
            builder.setNumber(number);
        }
        if (pendingIntent != null) {
            builder.setFullScreenIntent(pendingIntent, true);
        }
        Notification notification = builder.build();
        if (manager != null) {
            manager.notify(1, notification);
        }
    }


    /**
     * 获得当前APP ICON
     *
     * @param context 上下文
     * @return 图标
     */
    public static Drawable getIconFromPackageName(Context context) {
        return getIconFromPackageName(context.getPackageName(), context);
    }

    /**
     * 获得指定包名 ICON
     *
     * @param packageName 包名
     * @param context     上下文
     * @return 图标
     */
    public static Drawable getIconFromPackageName(String packageName, Context context) {
        PackageManager pm = context.getPackageManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            try {
                PackageInfo pi = pm.getPackageInfo(packageName, 0);
                Context otherAppCtx = context.createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY);
                int[] displayMetrics;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    displayMetrics = new int[]{DisplayMetrics.DENSITY_XXXHIGH, DisplayMetrics.DENSITY_XXHIGH, DisplayMetrics.DENSITY_XHIGH, DisplayMetrics.DENSITY_HIGH, DisplayMetrics.DENSITY_TV};
                } else {
                    displayMetrics = new int[]{DisplayMetrics.DENSITY_XHIGH, DisplayMetrics.DENSITY_HIGH, DisplayMetrics.DENSITY_TV};
                }
                for (int displayMetric : displayMetrics) {
                    try {
                        Drawable d = otherAppCtx.getResources().getDrawableForDensity(pi.applicationInfo.icon, displayMetric);
                        if (d != null) {
                            return d;
                        }
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                // Handle Error here
            }
        }
        ApplicationInfo appInfo;
        try {
            appInfo = pm.getApplicationInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
        return appInfo.loadIcon(pm);
    }


    public static int getIcon(Context context) {
        return getIcon(context.getPackageName(), context);
    }


    /**
     * 获得图标资源id
     *
     * @param packageName 哪怕，我们会
     * @param context     上下文
     * @return 图标
     */
    public static int getIcon(String packageName, Context context) {
        PackageManager pm = context.getPackageManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            try {
                PackageInfo pi = pm.getPackageInfo(packageName, 0);
                return pi.applicationInfo.icon;
            } catch (Exception e) {
                // Handle Error here
            }
        }
        return 0;
    }

    /*
     * Drawable → Bitmap
     */
    private static Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }
}

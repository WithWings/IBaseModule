package com.withwings.baseutils.utils;

import android.app.Notification;
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
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.DisplayMetrics;

/**
 * 状态栏工具类
 * 创建：WithWings 时间 2018/2/24
 * Email:wangtong1175@sina.com
 */
@SuppressWarnings({"unused", "DanglingJavadoc"})
public class StatusBarUtils {

    /**
     * 显示一个普通的通知
     *
     * @param context 上下文
     */
    public static void showNotification(Context context) {
        Notification notification = new NotificationCompat.Builder(context)
                /**设置通知左边的大图标**/
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), getIcon(context.getPackageName(), context)))
                /**设置通知右边的小图标**/
                .setSmallIcon(getIcon(context.getPackageName(), context))
                /**通知首次出现在通知栏，带上升动画效果的**/
                .setTicker("通知来了")
                /**设置通知的标题**/
                .setContentTitle("这是一个通知的标题")
                /**设置通知的内容**/
                .setContentText("这是一个通知的内容这是一个通知的内容")
                /**通知产生的时间，会在通知信息里显示**/
                .setWhen(System.currentTimeMillis())
                /**设置该通知优先级**/
                .setPriority(Notification.PRIORITY_DEFAULT)
                /**设置这个标志当用户单击面板就可以让通知将自动取消**/
                .setAutoCancel(true)
                /**设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)**/
                .setOngoing(false)
                /**向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：**/
                .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                // TODO 点击效果
//                .setContentIntent(PendingIntent.getActivity(context, 1, new Intent(context, MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT))
                .build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        /**发起通知**/
        notificationManager.notify(0, notification);
    }

    /**
     * 显示一个下载带进度条的通知
     *
     * @param context 上下文
     */
    public static NotificationCompat.Builder showNotificationProgress(Context context) {
        //进度条通知
        final NotificationCompat.Builder builderProgress = new NotificationCompat.Builder(context);
        builderProgress.setContentTitle("下载中");
        builderProgress.setSmallIcon(getIcon(context.getPackageName(), context));
        builderProgress.setTicker("进度条通知");
        builderProgress.setProgress(100, 0, false);
        final Notification notification = builderProgress.build();
        final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //发送一个通知
        if (notificationManager != null) {
            notificationManager.notify(2, notification);
        }

//        builderProgress.setProgress(100, progress, false);
//        builderProgress.cancel(2);
        return builderProgress;
    }


    /**
     * 悬挂式,部分系统厂商不支持
     *
     * @param context
     */
    public static void showFullScreen(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://blog.csdn.net/linglongxin24"));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(getIcon(context.getPackageName(), context));
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),getIcon(context.getPackageName(), context)));
        builder.setAutoCancel(true);
        builder.setContentTitle("悬挂式通知");
        // TODO 设置点击跳转
//        Intent hangIntent = new Intent();
//        hangIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        hangIntent.setClass(context, MainActivity.class);
//        //如果描述的PendingIntent已经存在，则在产生新的Intent之前会先取消掉当前的
//        PendingIntent hangPendingIntent = PendingIntent.getActivity(context, 0, hangIntent, PendingIntent.FLAG_CANCEL_CURRENT);
//        builder.setFullScreenIntent(hangPendingIntent, true);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(3, builder.build());
    }

    /**
     * 折叠式
     *
     * @param context
     */
//    public static void shwoNotify(Context context) {
//        //先设定RemoteViews
//        RemoteViews view_custom = new RemoteViews(context.getPackageName(), R.layout.view_custom);
//        //设置对应IMAGEVIEW的ID的资源图片
//        view_custom.setImageViewResource(R.id.custom_icon, getIcon(context.getPackageName(), context));
//        view_custom.setTextViewText(R.id.tv_custom_title, "今日头条");
//        view_custom.setTextColor(R.id.tv_custom_title, Color.BLACK);
//        view_custom.setTextViewText(R.id.tv_custom_content, "金州勇士官方宣布球队已经解雇了主帅马克-杰克逊，随后宣布了最后的结果。");
//        view_custom.setTextColor(R.id.tv_custom_content, Color.BLACK);
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
//        mBuilder.setContent(view_custom).setContentIntent(PendingIntent.getActivity(context, 4, new Intent(context, MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT)).setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
//                .setTicker("有新资讯").setPriority(Notification.PRIORITY_HIGH)// 设置该通知优先级
//                .setOngoing(false)//不是正在进行的   true为正在进行  效果和.flag一样
//                .setSmallIcon(getIcon(context.getPackageName(), context));
//        Notification notify = mBuilder.build();
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
//        notificationManager.notify(4, notify);
//    }

    public static Drawable getIconFromPackageName(String packageName, Context context) {
        PackageManager pm = context.getPackageManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            try {
                PackageInfo pi = pm.getPackageInfo(packageName, 0);
                Context otherAppCtx = context.createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY);
                int displayMetrics[] = {DisplayMetrics.DENSITY_XXXHIGH, DisplayMetrics.DENSITY_XXHIGH, DisplayMetrics.DENSITY_XHIGH, DisplayMetrics.DENSITY_HIGH, DisplayMetrics.DENSITY_TV};
                for (int displayMetric : displayMetrics) {
                    try {
                        Drawable d = otherAppCtx.getResources().getDrawableForDensity(pi.applicationInfo.icon, displayMetric);
                        if (d != null) {
                            return d;
                        }
                    } catch (Resources.NotFoundException e) {
                        continue;
                    }
                }
            } catch (Exception e) {
                // Handle Error here
            }
        }
        ApplicationInfo appInfo = null;
        try {
            appInfo = pm.getApplicationInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
        return appInfo.loadIcon(pm);
    }


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

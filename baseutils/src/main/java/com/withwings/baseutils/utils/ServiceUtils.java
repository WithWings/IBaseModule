package com.withwings.baseutils.utils;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;

import java.util.List;

/**
 * 服务工具类
 * 创建：WithWings 时间 2018/5/3
 * Email:wangtong1175@sina.com
 */
@SuppressWarnings("unused")
public class ServiceUtils {

    /**
     * 该方法是为了查询某个指定的Service（服务）当前是否在运行，一般用以防止用户在设置中停止服务，或者被某些杀毒软件清理掉
     *
     * @param serviceClass 传输要查询的服务对象，这里使用的是服务名.class格式
     * @param context      当前上下文对象，用以获得Service。调用getSystemService方法
     * @return 返回值是对该对象是否存在的判断，boolean类型，如果在运行，返回true，否则返回false
     */
    // 获取到正在运行的一些服务，判断我们要找的服务是否正在运行
    public static boolean isServiceRunning(Class<? extends Service> serviceClass, Context context) {
        // 获得Activity的信息类一般用于获取到正在运行的一些东西 比如进程 比如服务等
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        // 传一个参数 获得正在运行的所有服务，参数意为至多返回多少个服务，如果有5个，我们返回3个，就不会返回完。
        // 为了将所有正在运行的服务都返回，一般设置一个差不多稍大的就可以了。
        // 泛型的RunningServiceInfo是一个存放运行的服务信息的对象。可以理解为一个bean对象，该对象是系统封装的
        List<ActivityManager.RunningServiceInfo> runningServiceInfos;
        if (activityManager != null) {
            runningServiceInfos = activityManager.getRunningServices(Integer.MAX_VALUE);
        } else {
            return false;
        }
        // 取出集合中每个的RunningServiceInfo来查看是否有自己要找的。
        for (ActivityManager.RunningServiceInfo info : runningServiceInfos) {
            // 拿到一个四大组件的封装类
            ComponentName service = info.service;
            // 获取类名，这里获得的是全类名
            String className = service.getClassName();
            // 获得要查询的服务类的全类名
            String name = serviceClass.getName();
            // 如果有相同的。
            if (!TextUtils.isEmpty(className) && className.equals(name)) {
                // 说明我们传进来的服务正在运行
                return true;
            }
        }
        // 否则说明不同
        return false;
    }

}

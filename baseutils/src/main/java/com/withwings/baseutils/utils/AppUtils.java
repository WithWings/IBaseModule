package com.withwings.baseutils.utils;

import android.annotation.SuppressLint;
import android.app.Application;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Application 工具类
 * 创建：WithWings 时间 2018/2/28
 * Email:wangtong1175@sina.com
 */
public class AppUtils {

    public static Application getApp() {
        Application application = getInitialApplication();
        if(application == null) {
            return getThreadApplication();
        } else {
            return application;
        }
    }

    @SuppressLint("PrivateApi")
    private static Application getInitialApplication() {
        Application application = null;
        Class<?> activityThreadClass;
        try {
            activityThreadClass = Class.forName("android.app.ActivityThread");
            Field appField = activityThreadClass
                    .getDeclaredField("mInitialApplication");
            // Object object = activityThreadClass.newInstance();
            final Method method = activityThreadClass.getMethod(
                    "currentActivityThread");
            // 得到当前的ActivityThread对象
            Object localObject = method.invoke(null, (Object[]) null);
            appField.setAccessible(true);
            application = (Application) appField.get(localObject);
            // appField.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
        return application;
    }

    private static Application getThreadApplication() {
        Application application = null;
        Class<?> activityThreadClass;
        try {
            activityThreadClass = Class.forName("android.app.ActivityThread");
            final Method method2 = activityThreadClass.getMethod(
                    "currentActivityThread");
            // 得到当前的ActivityThread对象
            Object localObject = method2.invoke(null, (Object[]) null);

            final Method method = activityThreadClass
                    .getMethod("getApplication");
            application = (Application) method.invoke(localObject, (Object[]) null);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
        return application;
    }

}

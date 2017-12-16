package com.withwings.baseutils.base;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础类 Application 初始化一些数据
 * 创建：WithWings 时间：2017/10/25.
 * Email:wangtong1175@sina.com
 */
public class BaseApplication extends Application {

    // 根据是否一键退出需要判断是否要赋值
    public static final List<BaseActivity> mActivities = initExit();
    //
    private static final String TAG = "BaseApplication";

    // 是否需要一键退出功能
    public static final boolean mExitAll = false;

    @Override
    public void onCreate() {
        super.onCreate();

        initRegister();

        initData();

        registerComponent();

        registerActivityLifecycle();
    }
    /**
     * TODO 第三方库初始化
     */
    private void initRegister() {
        // 注意不要执行耗时操作，否则会拖慢应用程序启动速度

        /*
         * 接收内存状态通知Activity, Service, ContentProvider, Application
         * 1.OnTrimMemory(int level)是 OnLowMemory（） Android 4.0后的替代 API
         * 2.OnLowMemory() =  OnTrimMemory(int level) 中的TRIM_MEMORY_COMPLETE级别
         * 3.若想兼容Android 4.0前，请使用OnLowMemory()；否则直接使用OnTrimMemory(int level)即可
         */
    }

    /**
     * TODO 初始化项目数据
     */
    private void initData() {
        // Application 是单例模式，所以整个项目都可以拿到，且拿到的是同一个对象
        // 临时性的共享数据
    }

    /**
     * 注册内存监听
     */
    private void registerComponent() {

        /*
         * 建议使用 ComponentCallbacks2 可以监听内存使用情况
         */
        registerComponentCallbacks(new ComponentCallbacks2() {

            /**
             * 应用程序 当前内存使用情况，以下方法都会有该功能
             *
             * Application.onTrimMemory()
             * Activity.onTrimMemory()
             * Fragment.OnTrimMemory()
             * Service.onTrimMemory()
             * ContentProvider.OnTrimMemory()
             *
             * @param level 内存级别
             */
            @Override
            public void onTrimMemory(int level) {
                switch (level){
                    // 5 / 前台 / 有点低，开始清理后台程序
                    case TRIM_MEMORY_RUNNING_MODERATE:

                        break;
                    // 10 / 前台 / 不会被杀掉，但是会影响性能
                    case TRIM_MEMORY_RUNNING_LOW:

                        break;
                    // 15 / 前台 / 大部分后台都被杀死
                    case TRIM_MEMORY_RUNNING_CRITICAL:

                        break;
                    // 20 / 前台 / 当前程序会被切到后台
                    case TRIM_MEMORY_UI_HIDDEN:
                        // 备注：该状态是界面所有 UI 组件不可见，不是该 Activity 不可见，与 onStop 还是不同的
                        // 在 onStop 之前调用
                        break;
                    // 40 / 后台 / 开始清理，不会清理到自己
                    case TRIM_MEMORY_BACKGROUND:

                        break;
                    // 60 / 后台 / 中间位置，快要清理到了
                    case TRIM_MEMORY_MODERATE:

                        break;
                    // 80 / 后台 / 很低了，随时都会被清理
                    case TRIM_MEMORY_COMPLETE:

                        break;
                }
            }

            /**
             * 监听 应用程序 配置信息的改变，如屏幕旋转/键盘显示等
             * @param newConfig 新的配置信息
             */
            @Override
            public void onConfigurationChanged(Configuration newConfig) {
                /* <activity android:name=".MainActivity">
                * android:configChanges="keyboardHidden|orientation|screenSize"
                * // 设置该配置属性会使 Activity在配置改变时不重启，只执行onConfigurationChanged（）
                * // 上述语句表明，设置该配置属性可使 Activity 在屏幕旋转时不重启
                * </activity>
                */
            }

            /**
             * 监听 Android系统整体内存较低时刻
             */
            @Override
            public void onLowMemory() {

            }
        });
    }



    /**
     * 注册 ComponentCallbacks2回调接口
     * @param callback
     */
    @Override
    public void registerComponentCallbacks(ComponentCallbacks callback) {
        super.registerComponentCallbacks(callback);
    }

    /**
     * 注销 ComponentCallbacks2回调接口
     * @param callback
     */
    @Override
    public void unregisterComponentCallbacks(ComponentCallbacks callback) {
        super.unregisterComponentCallbacks(callback);
    }

    private void registerActivityLifecycle() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.d(TAG,"onActivityCreated: " + activity.getLocalClassName());
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.d(TAG,"onActivityStarted: " + activity.getLocalClassName());
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.d(TAG,"onActivityResumed: " + activity.getLocalClassName());
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.d(TAG,"onActivityPaused: " + activity.getLocalClassName());
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Log.d(TAG, "onActivityStopped: " + activity.getLocalClassName());
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Log.d(TAG,"onActivityDestroyed: " + activity.getLocalClassName());
            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    /**
     * 注册 应用程序内 所有Activity的生命周期监听
     * @param callback
     */
    @Override
    public void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        super.registerActivityLifecycleCallbacks(callback);
    }

    /**
     * 注销 应用程序内 所有Activity的生命周期监听
     * @param callback
     */
    @Override
    public void unregisterActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        super.unregisterActivityLifecycleCallbacks(callback);
    }

    /**
     * 应用程序结束时调用
     * 但该方法只用于Android仿真机测试，在Android产品机是不会调用的
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    // 初始化界面存储器
    public static List<BaseActivity> initExit(){
        return mExitAll ? new ArrayList<BaseActivity>() : null;
    }

}

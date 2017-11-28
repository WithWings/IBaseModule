package com.withwings.baseutils.base;

import android.app.Application;

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

    // 是否需要一键退出功能
    public static final boolean mExitAll = false;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    // 初始化界面存储器
    public static List<BaseActivity> initExit(){
        return mExitAll ? new ArrayList<BaseActivity>() : null;
    }

}

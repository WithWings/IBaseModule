package com.withwings.ibasemodule.app;

import com.withwings.baselibs.nohttp.InitNoHttpConfig;
import com.withwings.baseutils.base.BaseApplication;

/**
 * TODO
 * 创建：WithWings 时间：2017/11/6.
 * Email:wangtong1175@sina.com
 */
public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        InitNoHttpConfig.getInstance(this);
    }
}

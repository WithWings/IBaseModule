package com.withwings.baselibs.nohttp;

import com.withwings.baselibs.BuildConfig;

/**
 * 接口地址保存界面
 * 创建：WithWings 时间：2017/11/6.
 * Email:wangtong1175@sina.com
 */
public class HttpConfig extends HttpConfigBaseUrl {

    /**
     * 服务器地址
     */
    public static String SERVICE_URL = checkService();

    /**
     * 登录接口
     */
    public static final String LOGIN_URL = SERVICE_URL + "sug?code=utf-8&q=卫衣&callback=cb";


    private static String checkService() {
        // 多环境请使用 switch(BuildConfig.BUILD_TYPE) 进行区别
        if (BuildConfig.DEBUG) {
            return HttpConfigBaseUrl.TEST_SERVICE_URL;
        } else {
            return HttpConfigBaseUrl.RELEASE_SERVICE_URL;
        }
    }
}

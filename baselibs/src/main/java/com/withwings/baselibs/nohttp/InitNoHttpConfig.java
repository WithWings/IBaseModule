package com.withwings.baselibs.nohttp;

import android.content.Context;

import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;
import com.yanzhenjie.nohttp.cache.DBCacheStore;
import com.yanzhenjie.nohttp.cookie.DBCookieStore;
import com.yanzhenjie.nohttp.ssl.SSLUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

/**
 * TODO
 * 创建：WithWings 时间：2017/11/3.
 * Email:wangtong1175@sina.com
 */
public class InitNoHttpConfig {

    private static InitNoHttpConfig mInitNoHttpConfig;
    private final InitializationConfig mInitializationConfig;

    private InitNoHttpConfig(Context context){
        // 全局连接服务器超时时间，单位毫秒，默认10s。
        // 全局等待服务器响应超时时间，单位毫秒，默认10s。
        // 配置缓存，默认保存数据库DBCacheStore，保存到SD卡使用DiskCacheStore。
        // 如果不使用缓存，setEnable(false)禁用。
        // 配置Cookie，默认保存数据库DBCookieStore，开发者可以自己实现CookieStore接口。
        // 如果不维护cookie，setEnable(false)禁用。
        // 配置网络层，默认URLConnectionNetworkExecutor，如果想用OkHttp：OkHttpNetworkExecutor。
        // 全局通用Header，add是添加，多次调用add不会覆盖上次add。
        // 全局通用Param，add是添加，多次调用add不会覆盖上次add。
        // 全局SSLSocketFactory。
        // 全局HostnameVerifier。
        // 全局重试次数，配置后每个请求失败都会重试x次。
        mInitializationConfig = InitializationConfig.newBuilder(context)
                // 全局连接服务器超时时间，单位毫秒，默认10s。
                .connectionTimeout(5 * 1000)
                // 全局等待服务器响应超时时间，单位毫秒，默认10s。
                .readTimeout(5 * 1000)
                // 配置缓存，默认保存数据库DBCacheStore，保存到SD卡使用DiskCacheStore。
                .cacheStore(
                        // 如果不使用缓存，setEnable(false)禁用。
                        new DBCacheStore(context).setEnable(false)
                )
                // 配置Cookie，默认保存数据库DBCookieStore，开发者可以自己实现CookieStore接口。
                .cookieStore(
                        // 如果不维护cookie，setEnable(false)禁用。
                        new DBCookieStore(context).setEnable(true)
                )
                // 配置网络层，默认URLConnectionNetworkExecutor，如果想用OkHttp：OkHttpNetworkExecutor。
                .networkExecutor(new OkHttpNetworkExecutor())
                // 全局通用Header，add是添加，多次调用add不会覆盖上次add。
//                .addHeader()
                // 全局通用Param，add是添加，多次调用add不会覆盖上次add。
//                .addParam()
                .sslSocketFactory(setSocketFact()) // 全局SSLSocketFactory。
                .hostnameVerifier(setHostnameVerifier()) // 全局HostnameVerifier。
                .retry(0) // 全局重试次数，配置后每个请求失败都会重试x次。
                .build();

        NoHttp.initialize(mInitializationConfig);
    }

    public synchronized static InitNoHttpConfig getInstance(Context context){
        if(mInitNoHttpConfig == null) {
            mInitNoHttpConfig = new InitNoHttpConfig(context);
        }
        return mInitNoHttpConfig;
    }

    /**
     * Https 证书
     * @return 为null 时使用默认
     */
    private SSLSocketFactory setSocketFact() {
        return SSLUtils.defaultSSLSocketFactory();
    }

    /**
     *
     * @return 为null 时使用默认
     */
    private HostnameVerifier setHostnameVerifier() {
        return SSLUtils.defaultHostnameVerifier();
    }
}

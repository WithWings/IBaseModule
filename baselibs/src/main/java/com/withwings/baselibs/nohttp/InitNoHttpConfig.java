package com.withwings.baselibs.nohttp;

import android.content.Context;

import com.withwings.baselibs.BuildConfig;
import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;
import com.yanzhenjie.nohttp.cache.DBCacheStore;
import com.yanzhenjie.nohttp.cookie.DBCookieStore;
import com.yanzhenjie.nohttp.ssl.SSLUtils;

import java.util.HashMap;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

/**
 * 初始化NoHttp
 * 创建：WithWings 时间：2017/11/3.
 * Email:wangtong1175@sina.com
 */
public class InitNoHttpConfig {

    private static final String NOHTTP_LOG_TAG = "WithWings";

    private static InitNoHttpConfig mInitNoHttpConfig;

    private final InitializationConfig.Builder mBuilder;

    private InitNoHttpConfig(Context context) {
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
        // 全局连接服务器超时时间，单位毫秒，默认10s。
        // 全局等待服务器响应超时时间，单位毫秒，默认10s。
        // 配置缓存，默认保存数据库DBCacheStore，保存到SD卡使用DiskCacheStore。
        // 如果不使用缓存，setEnable(false)禁用。
        // 配置Cookie，默认保存数据库DBCookieStore，开发者可以自己实现CookieStore接口。
        // 如果不维护cookie，setEnable(false)禁用。
        // 配置网络层，默认URLConnectionNetworkExecutor，如果想用OkHttp：OkHttpNetworkExecutor。
        // 全局SSLSocketFactory。
        // 全局HostnameVerifier。
        // 全局重试次数，配置后每个请求失败都会重试x次。
        mBuilder = InitializationConfig.newBuilder(context)
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
                .sslSocketFactory(setSocketFact()) // 全局SSLSocketFactory。
                .hostnameVerifier(setHostnameVerifier()) // 全局HostnameVerifier。
                .retry(0);
        addHeader();
        addParam();

        init();

        Logger.setDebug(BuildConfig.DEBUG);// 开启NoHttp的调试模式, 配置后可看到请求过程、日志和错误信息。
        Logger.setTag(NOHTTP_LOG_TAG);// 打印Log的tag。
    }

    private void init() {
        InitializationConfig initializationConfig = mBuilder
                .build();

        NoHttp.initialize(initializationConfig);
    }

    /**
     * 初始化NoHttp
     * @param context 上下文对象，建议初始化位置 Application
     * @return 操作对象
     */
    public synchronized static InitNoHttpConfig getInstance(Context context) {
        if (mInitNoHttpConfig == null) {
            mInitNoHttpConfig = new InitNoHttpConfig(context);
        }
        return mInitNoHttpConfig;
    }

    /**
     * Https 证书
     *
     * @return 为null 时使用默认
     */
    private SSLSocketFactory setSocketFact() {
        return SSLUtils.defaultSSLSocketFactory();
    }

    /**
     * @return 为null 时使用默认
     */
    private HostnameVerifier setHostnameVerifier() {
        return SSLUtils.defaultHostnameVerifier();
    }

    /**
     * 添加请求头
     */
    public void addHeader() {
        //        mBuilder.addHeader("key","value");
    }

    /**
     * 添加公共参数
     */
    public void addParam() {
        //        mBuilder.addParam("key","value");
    }

    /**
     * 这个方法会重新初始化 NoHttp，如果不是有参数在运行后才会生成，尽量不要调用
     *
     * @param hashMap 参数
     */
    public void addHeader(HashMap<String, String> hashMap) {
        Set<String> strings = hashMap.keySet();
        for (String string : strings) {
            mBuilder.addHeader(string, hashMap.get(string));
        }
        init();
    }

    /**
     * 这个方法会重新初始化 NoHttp，如果不是有参数在运行后才会生成，尽量不要调用
     *
     * @param hashMap 参数
     */
    public void addParam(HashMap<String, String> hashMap) {
        Set<String> strings = hashMap.keySet();
        for (String string : strings) {
            mBuilder.addParam(string, hashMap.get(string));
        }
        init();
    }
}

package com.withwings.baselibs.okhttp;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.withwings.baselibs.BuildConfig;
import com.withwings.baselibs.okhttp.listener.OkHttpEnqueueListener;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * OkHttp请求队列
 * 创建：WithWings 时间：2017/11/22.
 * Email:wangtong1175@sina.com
 */
public class OkHttpEnqueue {

    private static final long READ_TIME_OUT = 10000;

    private static HttpLoggingInterceptor mLogInterceptor = new HttpLoggingInterceptor()
            .setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.HEADERS : HttpLoggingInterceptor.Level.NONE);

    private static OkHttpClient mOkHttpClient = getOkHttpClient();

    private static OkHttpClient getOkHttpClient() {
        if(BuildConfig.DEBUG) { // Debug 环境允许使用代理来抓包
            return new OkHttpClient.Builder().addInterceptor(mLogInterceptor).addNetworkInterceptor(mLogInterceptor).connectTimeout(10 * 1000,TimeUnit.MILLISECONDS).writeTimeout(10 * 1000,TimeUnit.MILLISECONDS).readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS).build();
        } else {
            return new OkHttpClient.Builder().proxy(Proxy.NO_PROXY).addInterceptor(mLogInterceptor).addNetworkInterceptor(mLogInterceptor).connectTimeout(10 * 1000,TimeUnit.MILLISECONDS).writeTimeout(10 * 1000,TimeUnit.MILLISECONDS).readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS).build();
        }
    }

    public static void newCall(Request request) {
        newCall(request, null);
    }

    public static void newCall(Request request, final OkHttpEnqueueListener okHttpEnqueueListener) {
        call(mOkHttpClient, request, okHttpEnqueueListener);
    }

    public static void outTimeCall(Request request, long timeout, final OkHttpEnqueueListener okHttpEnqueueListener) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if(!BuildConfig.DEBUG) { // 允许测试环境抓包
            builder = builder.proxy(Proxy.NO_PROXY);
        }
        OkHttpClient okHttpClient = builder.addInterceptor(mLogInterceptor).addNetworkInterceptor(mLogInterceptor).readTimeout(timeout, TimeUnit.MILLISECONDS).build();
        call(okHttpClient, request, okHttpEnqueueListener);
    }

    public static void call(OkHttpClient okHttpClient, Request request, final OkHttpEnqueueListener okHttpEnqueueListener) {
        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    final MediaType mediaType = responseBody.contentType();
                    final byte[] bytes = responseBody.bytes();
                    final String result = new String(bytes);
                    final int code = response.code();
                    if (okHttpEnqueueListener != null) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                if (code == 200) {
                                    // 这里可以根据约定格式，将服务端给的错误吗处理成 onResponse ，不过这里因为没有格式，所以不做处理
                                    okHttpEnqueueListener.onSuccess(call, result, bytes, mediaType);
                                } else {
                                    okHttpEnqueueListener.onResponse(call, result, bytes, mediaType);
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(final Call call, final IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (okHttpEnqueueListener != null) {
                            okHttpEnqueueListener.onFailure(call, e);
                        }
                    }
                });
            }

        });
    }

    /**
     * 验证是否存在代理
     * @return true 存在，false 不存在
     */
    private static boolean isWifiProxy() {
        final boolean IS_ICS_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
        String proxyAddress;
        int proxyPort;
        if (IS_ICS_OR_LATER) {
            proxyAddress = System.getProperty("http.proxyHost");
            String portStr = System.getProperty("http.proxyPort");
            proxyPort = Integer.parseInt((portStr != null ? portStr : "-1"));
        } else {
            proxyAddress = android.net.Proxy.getHost(getApp());
            proxyPort = android.net.Proxy.getPort(getApp());
        }
        return (!TextUtils.isEmpty(proxyAddress)) && (proxyPort != -1);
    }

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
        Application application;
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

    @SuppressLint("PrivateApi")
    private static Application getThreadApplication() {
        Application application;
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

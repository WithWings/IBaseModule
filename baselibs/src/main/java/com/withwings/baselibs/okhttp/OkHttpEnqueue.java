package com.withwings.baselibs.okhttp;

import android.os.Handler;
import android.os.Looper;

import com.withwings.baselibs.BuildConfig;
import com.withwings.baselibs.okhttp.listener.OkHttpEnqueueListener;

import java.io.IOException;
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

    private static OkHttpClient mOkHttpClient = new OkHttpClient.Builder().addInterceptor(mLogInterceptor).addNetworkInterceptor(mLogInterceptor).readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS).build();

    public static void newCall(Request request) {
        newCall(request, null);
    }

    public static void newCall(Request request, final OkHttpEnqueueListener okHttpEnqueueListener) {
        call(mOkHttpClient, request, okHttpEnqueueListener);
    }

    public static void outTimeCall(Request request, long timeout, final OkHttpEnqueueListener okHttpEnqueueListener) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(mLogInterceptor).addNetworkInterceptor(mLogInterceptor).readTimeout(timeout, TimeUnit.MILLISECONDS).build();
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

}

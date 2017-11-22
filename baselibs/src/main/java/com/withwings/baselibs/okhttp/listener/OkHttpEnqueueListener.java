package com.withwings.baselibs.okhttp.listener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * OkHttp联网请求结果监听
 * 创建：WithWings 时间：2017/11/22.
 * Email:wangtong1175@sina.com
 */
public interface OkHttpEnqueueListener {

    /**
     * 联网请求成功
     */
    void onSuccess(Call call, String result, byte[] bytes, MediaType mediaType);

    /**
     * 联网成功，请求失败
     */
    void onResponse(Call call, String result, byte[] bytes, MediaType mediaType);

    /**
     * 联网失败
     */
    void onFailure(Call call, IOException e);

}

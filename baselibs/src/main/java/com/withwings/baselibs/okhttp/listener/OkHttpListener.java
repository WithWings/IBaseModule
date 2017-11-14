package com.withwings.baselibs.okhttp.listener;

import java.io.IOException;

import okhttp3.Call;

/**
 * OkHttp联网请求监听
 * 创建：WithWings 时间：2017/11/9.
 * Email:wangtong1175@sina.com
 */
public interface OkHttpListener {

    /**
     * 联网请求成功
     */
    void onSuccess(Call call, String result) throws IOException;

    /**
     * 联网成功，请求失败
     */
    void onResponse(Call call, String result) throws IOException;

    /**
     * 联网失败
     */
    void onFailure(Call call, IOException e);

}
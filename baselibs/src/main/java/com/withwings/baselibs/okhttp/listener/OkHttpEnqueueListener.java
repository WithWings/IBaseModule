package com.withwings.baselibs.okhttp.listener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * OkHttp联网请求结果监听
 * 创建：WithWings 时间：2017/11/22.
 * Email:wangtong1175@sina.com
 */
public abstract class OkHttpEnqueueListener {

    public boolean mShowError;

    public OkHttpEnqueueListener() {
        mShowError = true;
    }

    /**
     * 构造方法
     * @param showError 是否展示错误，默认展示
     */
    public OkHttpEnqueueListener(boolean showError) {
        mShowError = showError;
    }

    /**
     * 联网请求成功：且服务端返回 code = 0
     */
    public abstract void onSuccess(Call call, String result, byte[] bytes, MediaType mediaType);

    /**
     * 联网成功，请求失败
     */
    public abstract void onResponse(Call call, String result, byte[] bytes, MediaType mediaType);

    /**
     * 联网失败
     */
    public abstract void onFailure(Call call, IOException e);

}
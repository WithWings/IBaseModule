package com.withwings.baselibs.okhttp.listener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * OkHttp联网请求监听
 * 创建：WithWings 时间：2017/11/9.
 * Email:wangtong1175@sina.com
 */
public interface OkHttpListener {

    void onResponse(Call call, Response response) throws IOException;

    void onFailure(Call call, IOException e);

}

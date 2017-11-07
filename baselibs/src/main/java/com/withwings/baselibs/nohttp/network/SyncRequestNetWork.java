package com.withwings.baselibs.nohttp.network;

import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.StringRequest;
import com.yanzhenjie.nohttp.rest.SyncRequestExecutor;

/**
 * TODO
 * 创建：WithWings 时间：2017/11/6.
 * Email:wangtong1175@sina.com
 */
public class SyncRequestNetWork {

    public static void doApiPost(String url) {
        StringRequest stringRequest = new StringRequest(url, RequestMethod.POST);
        Response<String> response = SyncRequestExecutor.INSTANCE.execute(stringRequest);
        if(response.isSucceed()) {
            // 请求成功
        } else {
            // 请求失败
            Exception e = response.getException();

        }
    }

}
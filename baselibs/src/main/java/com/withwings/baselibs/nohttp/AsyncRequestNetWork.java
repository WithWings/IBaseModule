package com.withwings.baselibs.nohttp;

import com.yanzhenjie.nohttp.rest.AsyncRequestExecutor;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SimpleResponseListener;
import com.yanzhenjie.nohttp.rest.StringRequest;

/**
 * TODO
 * 创建：WithWings 时间：2017/11/6.
 * Email:wangtong1175@sina.com
 */
public class AsyncRequestNetWork {

    public static void doApiPost(String url){
        StringRequest stringRequest = new StringRequest(url);
        AsyncRequestExecutor.INSTANCE.execute(0, stringRequest, new SimpleResponseListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                // 请求成功。
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                // 请求失败。
            }
        });
    }
}

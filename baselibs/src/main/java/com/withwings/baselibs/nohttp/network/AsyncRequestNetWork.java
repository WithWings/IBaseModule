package com.withwings.baselibs.nohttp.network;

import com.withwings.baselibs.BuildConfig;
import com.withwings.baselibs.nohttp.NetWorkRequestListener;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.rest.AsyncRequestExecutor;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SimpleResponseListener;

/**
 * 异步请求，不需要单独打开子线程
 * 创建：WithWings 时间：2017/11/6.
 * Email:wangtong1175@sina.com
 */
public class AsyncRequestNetWork {

    public static  <T> void doRequest(Request<T> request, int what, final NetWorkRequestListener<T> netWorkRequestListener) {
        if(BuildConfig.DEBUG) {
            Logger.i("=============request start=============");
            Logger.i("RequestMethod:" + request.getRequestMethod());
            Logger.i("Priority:" + request.getPriority());
            Logger.i("CacheMode:" + request.getCacheMode());
            Logger.i("=============request end=============");
        }
        AsyncRequestExecutor.INSTANCE.execute(0, request, new SimpleResponseListener<T>() {
            @Override
            public void onStart(int what) {
                super.onStart(what);
            }

            @Override
            public void onSucceed(int what, Response<T> response) {
                super.onSucceed(what, response);
                if(BuildConfig.DEBUG) {
                    Logger.i("=============response start(onSucceed)=============");
                    Logger.i("what:" + what);
                    Logger.i(response.get());
                    Logger.i("=============response end=============");
                }
                if (netWorkRequestListener != null) {
                    if (response.getHeaders().getResponseCode() == 200) {
                        netWorkRequestListener.onSucceed(what, response.get());
                    } else {
                        netWorkRequestListener.onFailed(what, response.get());
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<T> response) {
                super.onFailed(what, response);
                if(BuildConfig.DEBUG) {
                    Logger.i("=============response start(onFailed)=============");
                    Logger.i("what:" + what);
                    Logger.i(response.get());
                    Logger.i("=============response end=============");
                }
                if (netWorkRequestListener != null) {
                    netWorkRequestListener.onFailed(what, response.get());
                }
            }

            @Override
            public void onFinish(int what) {
                super.onFinish(what);
            }
        });
    }
}

package com.withwings.baselibs.nohttp.network;

import com.withwings.baselibs.BuildConfig;
import com.withwings.baselibs.nohttp.listener.NetWorkRequestListener;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.rest.AsyncRequestExecutor;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * 异步请求，不需要单独打开子线程
 * 创建：WithWings 时间：2017/11/6.
 * Email:wangtong1175@sina.com
 */
public class AsyncRequestNetWork {

    /**
     * 异步访问接口
     * @param request 通过 RestRequestUtils 获得的请求接口信息
     * @param what 队列标记
     * @param netWorkRequestListener 状态监听
     * @param <T> 请求数据类型
     */
    public static <T> void doRequest(Request<T> request, int what, final NetWorkRequestListener<T> netWorkRequestListener) {
        // release 环境不要打印 log
        if (BuildConfig.DEBUG) {
            Logger.i("=============request start=============");
            Logger.i("RequestMethod:" + request.getRequestMethod());
            Logger.i("Priority:" + request.getPriority());
            Logger.i("CacheMode:" + request.getCacheMode());
            Logger.i("=============request end=============");
        }
        AsyncRequestExecutor.INSTANCE.execute(0, request, new OnResponseListener<T>() {
            @Override
            public void onStart(int what) {
                if (netWorkRequestListener != null) {
                    netWorkRequestListener.onStart(what);
                }
            }

            @Override
            public void onSucceed(int what, Response<T> response) {
                if (BuildConfig.DEBUG) {
                    Logger.i("=============response start(onSucceed)=============");
                    Logger.i("what:" + what);
                    Logger.i(response.get());
                    Logger.i("=============response end=============");
                }
                if (netWorkRequestListener != null) {
                    if (response.getHeaders().getResponseCode() == 200) {
                        netWorkRequestListener.onSucceed(what, response);
                    } else {
                        netWorkRequestListener.onFailed(what, response);
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<T> response) {
                if (BuildConfig.DEBUG) {
                    Logger.i("=============response start(onFailed)=============");
                    Logger.i("what:" + what);
                    Logger.i(response.get());
                    Logger.i("=============response end=============");
                }
                if (netWorkRequestListener != null) {
                    netWorkRequestListener.onFailed(what, response);
                }
            }

            @Override
            public void onFinish(int what) {
                if (netWorkRequestListener != null) {
                    netWorkRequestListener.onFinish(what);
                }
            }
        });
    }
}

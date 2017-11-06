package com.withwings.baselibs.nohttp;

import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * TODO
 * 创建：WithWings 时间：2017/11/6.
 * Email:wangtong1175@sina.com
 */
public class RequestQueueNetWork {

    /**
     * 工具类
     */
    private static RequestQueueNetWork mRequestQueueNetWork;

    /**
     * 请求队列
     */
    private static RequestQueue mRequestQueue;

    private RequestQueueNetWork() {
        mRequestQueue = NoHttp.newRequestQueue(5);
    }

    public static synchronized RequestQueueNetWork getInstance() {
        if (mRequestQueueNetWork == null) {
            mRequestQueueNetWork = new RequestQueueNetWork();
        }
        return mRequestQueueNetWork;
    }

    public <T> void doRequest(final Request<T> request, int what, final NetWorkRequestListener<T> netWorkRequestListener) {
        mRequestQueue.add(what, request, new OnResponseListener<T>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<T> response) {
                Logger.i("=============response start(onSucceed)=============");
                Logger.i("what:" + what);
                Logger.i(response);
                Logger.i("=============response end=============");
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
                Logger.i("=============response start(onFailed)=============");
                Logger.i("what:" + what);
                Logger.i(response);
                Logger.i("=============response end=============");
                if (netWorkRequestListener != null) {
                    netWorkRequestListener.onFailed(what, response.get());
                }
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 释放内存
     */
    public void stop() {
        mRequestQueue.stop();
    }

}

package com.withwings.baselibs.nohttp.network;

import com.withwings.baselibs.BuildConfig;
import com.withwings.baselibs.nohttp.NetWorkRequestListener;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SyncRequestExecutor;

/**
 * 同步请求，需要手动开启子线程
 * 创建：WithWings 时间：2017/11/6.
 * Email:wangtong1175@sina.com
 */
public class SyncRequestNetWork {

    public <T> void doRequest(Request<T> request, NetWorkRequestListener<T> netWorkRequestListener) {
        if(BuildConfig.DEBUG) {
            Logger.i("=============request start=============");
            Logger.i("RequestMethod:" + request.getRequestMethod());
            Logger.i("Priority:" + request.getPriority());
            Logger.i("CacheMode:" + request.getCacheMode());
            Logger.i("=============request end=============");
        }
        Response<T> response = SyncRequestExecutor.INSTANCE.execute(request);
        if(BuildConfig.DEBUG) {
            Logger.i("=============response start(onSucceed)=============");
            Logger.i(response.get());
            Logger.i("=============response end=============");
        }
        if (response.isSucceed()) {
            // 请求成功。
            if (netWorkRequestListener != null) {
                if (response.getHeaders().getResponseCode() == 200) {
                    netWorkRequestListener.onSucceed(0, response.get());
                } else {
                    netWorkRequestListener.onFailed(0, response.get());
                }
            }
        } else {
            // 请求失败，拿到错误：
            if (netWorkRequestListener != null) {
                netWorkRequestListener.onFailed(0, response.get());
            }
        }
    }

}
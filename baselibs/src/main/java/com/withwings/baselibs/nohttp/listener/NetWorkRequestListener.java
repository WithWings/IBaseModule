package com.withwings.baselibs.nohttp.listener;

import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * 网络请求监听
 * 创建：WithWings 时间：2017/11/6.
 * Email:wangtong1175@sina.com
 */
public abstract class NetWorkRequestListener<T> implements OnResponseListener<T> {

    /**
     * 联网开始
     * @param what 队列标记
     */
    @Override
    public void onStart(int what) {

    }

    /**
     * 请求成功
     * @param what 队列标记
     * @param response 请求结果
     */
    @Override
    public abstract void onSucceed(int what, Response<T> response);

    /**
     * 请求失败
     * @param what 队列标记
     * @param response 请求结果
     */
    @Override
    public abstract void onFailed(int what, Response<T> response);

    /**
     * 请求结束
     * @param what 队列标记
     */
    @Override
    public void onFinish(int what) {

    }
}

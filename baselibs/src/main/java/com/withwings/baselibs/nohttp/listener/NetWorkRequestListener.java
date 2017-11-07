package com.withwings.baselibs.nohttp.listener;

import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * TODO
 * 创建：WithWings 时间：2017/11/6.
 * Email:wangtong1175@sina.com
 */
public abstract class NetWorkRequestListener<T> implements OnResponseListener<T> {

    @Override
    public void onStart(int what) {

    }

    @Override
    public abstract void onSucceed(int what, Response<T> response);

    @Override
    public abstract void onFailed(int what, Response<T> response);

    @Override
    public void onFinish(int what) {

    }
}

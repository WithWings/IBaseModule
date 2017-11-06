package com.withwings.baselibs.nohttp;

import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;

import java.util.concurrent.BlockingQueue;

/**
 * TODO
 * 创建：WithWings 时间：2017/11/6.
 * Email:wangtong1175@sina.com
 */
public abstract class NetWorkRequest<T> extends Request<T> {


    public NetWorkRequest(String url) {
        super(url);
    }

    public NetWorkRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    @Override
    public int what() {
        return 0;
    }

    @Override
    public OnResponseListener<T> responseListener() {
        return null;
    }

    @Override
    public void setQueue(BlockingQueue<?> queue) {

    }

    @Override
    public boolean inQueue() {
        return false;
    }

    @Override
    public T parseResponse(Headers responseHeaders, byte[] responseBody) throws Exception {
        return null;
    }

}

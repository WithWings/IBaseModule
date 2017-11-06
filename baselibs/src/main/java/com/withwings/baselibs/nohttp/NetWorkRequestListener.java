package com.withwings.baselibs.nohttp;

/**
 * TODO
 * 创建：WithWings 时间：2017/11/6.
 * Email:wangtong1175@sina.com
 */
public interface NetWorkRequestListener<T> {

    void onSucceed(int what, T result);

    void onFailed(int what, T result);

}

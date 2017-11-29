package com.withwings.baseutils.network.listener;

/**
 * TODO
 * 创建：WithWings 时间：2017/11/29.
 * Email:wangtong1175@sina.com
 */
public interface OnNetWorkListener {

    void onSuccess(byte[] data);

    void onFailed(int responseCode);

}

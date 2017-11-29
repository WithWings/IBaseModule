package com.withwings.mvc.query.model;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.withwings.mvc.local.listener.OnQueryLocalListener;

/**
 * TODO
 * 创建：WithWings 时间：2017/11/29.
 * Email:wangtong1175@sina.com
 */
public interface QueryInputIpLocationModel {

    void requestNetWork(String url, String ip, @NonNull Activity activity, OnQueryLocalListener onQueryLocalListener);

}

package com.withwings.mvc.local.model;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.withwings.mvc.local.listener.OnQueryLocalListener;

/**
 * TODO
 * 创建：WithWings 时间：2017/11/28.
 * Email:wangtong1175@sina.com
 */
public interface QueryLocalIpLocationModel {

    String loadTrackRecordInfo(Context context);

    void requestNetWork(String url, @NonNull Activity activity, OnQueryLocalListener onQueryLocalListener);

}

package com.withwings.mvp.page.read.model;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.withwings.mvp.page.read.model.listener.OnReadListener;

/**
 * 数据获取器
 * 创建：WithWings 时间：2017/11/28.
 * Email:wangtong1175@sina.com
 */
public interface ReadUserInfoModel {

    void read(@NonNull SharedPreferences sharedPreferences, @NonNull OnReadListener onReadListener);

}

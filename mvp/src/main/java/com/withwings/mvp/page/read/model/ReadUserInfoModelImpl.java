package com.withwings.mvp.page.read.model;

import android.content.SharedPreferences;
import androidx.annotation.NonNull;

import com.withwings.mvp.page.read.model.listener.OnReadListener;

/**
 * TODO
 * 创建：WithWings 时间：2017/11/28.
 * Email:wangtong1175@sina.com
 */
public class ReadUserInfoModelImpl implements ReadUserInfoModel {

    @Override
    public void read(@NonNull SharedPreferences sharedPreferences, @NonNull OnReadListener onReadListener) {
        String name = sharedPreferences.getString("name", "");
        String password = sharedPreferences.getString("password", "");
        onReadListener.read(name, password);
    }
}

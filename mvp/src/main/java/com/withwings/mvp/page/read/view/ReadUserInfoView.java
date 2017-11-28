package com.withwings.mvp.page.read.view;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * View
 * 创建：WithWings 时间：2017/11/28.
 * Email:wangtong1175@sina.com
 */
public interface ReadUserInfoView {

    SharedPreferences getSharedPreferences();

    void setUserName(@NonNull String name);

    void setUserPassword(@NonNull String password);

    Activity getActivity();
}

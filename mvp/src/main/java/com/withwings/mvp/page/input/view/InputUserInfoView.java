package com.withwings.mvp.page.input.view;

import android.content.SharedPreferences;

/**
 * 界面刷新处
 * 创建：WithWings 时间：2017/11/27.
 * Email:wangtong1175@sina.com
 */
public interface InputUserInfoView {

    String getName();

    String getPassword();

    SharedPreferences getSharedPreferences();

    void showToast(String string);

}

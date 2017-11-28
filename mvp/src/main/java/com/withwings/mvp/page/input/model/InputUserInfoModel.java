package com.withwings.mvp.page.input.model;

import android.content.SharedPreferences;

import com.withwings.mvp.page.input.model.listener.OnSubmitListener;

/**
 * 逻辑执行处
 * 创建：WithWings 时间：2017/11/27.
 * Email:wangtong1175@sina.com
 */
public interface InputUserInfoModel {

    void submit(String name, String password, OnSubmitListener onSubmitListener, SharedPreferences sharedPreferences);

}

package com.withwings.mvp.page.input.model;

import android.content.SharedPreferences;
import androidx.annotation.NonNull;

import com.withwings.mvp.page.input.model.listener.OnSubmitListener;

/**
 * 逻辑执行处
 * 创建：WithWings 时间：2017/11/27.
 * Email:wangtong1175@sina.com
 */
public interface InputUserInfoModel {

    void submit(@NonNull String name, @NonNull String password, @NonNull OnSubmitListener onSubmitListener, SharedPreferences sharedPreferences);

}

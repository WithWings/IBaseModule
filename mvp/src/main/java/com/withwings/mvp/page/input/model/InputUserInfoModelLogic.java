package com.withwings.mvp.page.input.model;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.withwings.mvp.page.input.model.listener.OnSubmitListener;

/**
 * 逻辑执行处代码实现
 * 创建：WithWings 时间：2017/11/27.
 * Email:wangtong1175@sina.com
 */
public class InputUserInfoModelLogic implements InputUserInfoModel {

    @Override
    public void submit(String name, String password, OnSubmitListener onSubmitListener, SharedPreferences sharedPreferences) {
        if (TextUtils.isEmpty(name)) {
            if (onSubmitListener != null) {
                onSubmitListener.onFailed("用户名不能为空！");
            }
            return;
        }
        if (TextUtils.isEmpty(password)) {
            if (onSubmitListener != null) {
                onSubmitListener.onFailed("密码不能为空！");
            }
            return;
        }
        if (sharedPreferences != null) {
            sharedPreferences.edit().putString("name", name).apply();
            sharedPreferences.edit().putString("password", password).apply();
            if (onSubmitListener != null) {
                onSubmitListener.onSuccess();
            }
        } else {
            if (onSubmitListener != null) {
                onSubmitListener.onFailed("存储失败");
            }
        }
    }

}

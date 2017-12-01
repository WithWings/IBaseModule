package com.withwings.mvp.page.input.model;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.withwings.mvp.base.MvpBaseModel;
import com.withwings.mvp.page.input.model.listener.OnSubmitListener;

/**
 * 逻辑执行处代码实现
 * 创建：WithWings 时间：2017/11/27.
 * Email:wangtong1175@sina.com
 */
public class InputUserInfoModelImpl extends MvpBaseModel implements InputUserInfoModel {

    @Override
    public void submit(@NonNull String name, @NonNull String password, @NonNull OnSubmitListener onSubmitListener, SharedPreferences sharedPreferences) {
        if (TextUtils.isEmpty(name)) {
            onSubmitListener.onFailed("用户名不能为空！");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            onSubmitListener.onFailed("密码不能为空！");
            return;
        }
        if (sharedPreferences != null) {
            sharedPreferences.edit().putString("name", name).apply();
            sharedPreferences.edit().putString("password", password).apply();
            onSubmitListener.onSuccess();
        } else {
            onSubmitListener.onFailed("存储失败");
        }
    }

}

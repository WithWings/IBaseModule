package com.withwings.baselibs.andpermission.listener;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * 重写不常用的方法，防止复写过多无用方法
 * 创建：WithWings 时间：2017/11/22.
 * Email:wangtong1175@sina.com
 */
public abstract class DefaultAndPermissionListener implements AndPermissionListener {

    @Override
    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {

    }

    @Override
    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {

    }
}

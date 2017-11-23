package com.withwings.baselibs.andpermission.listener;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * 权限申请结果：注意只能监听到操作结果
 * 创建：WithWings 时间：2017/11/22.
 * Email:wangtong1175@sina.com
 */
public interface AndPermissionListener {

    /**
     * 部分成功，全部成功时也会调用
     *
     * @param requestCode      请求code
     * @param grantPermissions 允许的权限
     */
    void onSucceed(int requestCode, @NonNull List<String> grantPermissions);

    /**
     * 部分失败，全部失败时也会调用
     *
     * @param requestCode       请求code
     * @param deniedPermissions 禁止的权限
     */
    void onFailed(int requestCode, @NonNull List<String> deniedPermissions);

    /**
     * 请求的所有权限全部都允许后才会触发
     *
     * @param requestCode      请求code
     * @param grantPermissions 允许的权限
     */
    void onAllSucceed(int requestCode, @NonNull List<String> grantPermissions);

    /**
     * 请求的所有权限全部都拒绝后才会触发
     *
     * @param requestCode       请求code
     * @param deniedPermissions 禁止的权限
     */
    void onAllFailed(int requestCode, @NonNull List<String> deniedPermissions);
}

package com.withwings.baselibs.andpermission;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.withwings.baselibs.andpermission.listener.AndPermissionListener;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * AndPermission 工具类
 * 创建：WithWings 时间：2017/11/22.
 * Email:wangtong1175@sina.com
 */
public class PermissionUtils {

    private PermissionUtils() {

    }

    public static void checkPermission(final Context context, int requestCode, SettingDialogShow rationaleDialog, final AndPermissionListener andPermissionListener, String... permissions) {
        // 敲黑板，Arrays.asList 返回的集合不可修改
        final List<String> requestList = Arrays.asList(permissions);
        AndPermission.with(context)
                .requestCode(requestCode)
                .permission(permissions)
                .rationale(rationaleDialog != null ? rationaleDialog.rationale() : null)
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                        if (andPermissionListener != null) {
                            andPermissionListener.onAllSucceed(requestCode, grantPermissions);
                            andPermissionListener.onSucceed(requestCode, grantPermissions);
                        }
                    }

                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                        if (andPermissionListener != null) {
                            // 全部失败
                            if (deniedPermissions.size() == requestList.size()) {
                                andPermissionListener.onAllFailed(requestCode, deniedPermissions);
                                andPermissionListener.onFailed(requestCode, deniedPermissions);
                            } else {
                                // 部分失败
                                andPermissionListener.onFailed(requestCode, deniedPermissions);
                                List<String> grantPermissions = new ArrayList<>();
                                grantPermissions.addAll(requestList);
                                // 部分成功
                                if (grantPermissions.removeAll(deniedPermissions)) {
                                    andPermissionListener.onSucceed(requestCode, grantPermissions);
                                }
                            }
                        }
                    }
                })
                .start();
    }

    public static void mustPermission(final Activity activity, int requestCode, final SettingDialogShow rationaleDialog, final SettingDialogShow settingDialog, final AndPermissionListener andPermissionListener, String... permissions) {
        // 敲黑板，Arrays.asList 返回的集合不可修改
        final List<String> requestList = Arrays.asList(permissions);
        AndPermission.with(activity)
                .requestCode(requestCode)
                .permission(permissions)
                .rationale(rationaleDialog != null ? rationaleDialog.rationale() : null)
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                        if (andPermissionListener != null) {
                            andPermissionListener.onAllSucceed(requestCode, grantPermissions);
                            andPermissionListener.onSucceed(requestCode, grantPermissions);
                        }
                    }

                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                        if (andPermissionListener != null) {
                            // 全部失败
                            if (deniedPermissions.size() == requestList.size()) {
                                andPermissionListener.onAllFailed(requestCode, deniedPermissions);
                                andPermissionListener.onFailed(requestCode, deniedPermissions);
                            } else {
                                // 部分失败
                                andPermissionListener.onFailed(requestCode, deniedPermissions);
                                List<String> grantPermissions = new ArrayList<>();
                                grantPermissions.addAll(requestList);
                                // 部分成功
                                if (grantPermissions.removeAll(deniedPermissions)) {
                                    andPermissionListener.onSucceed(requestCode, grantPermissions);
                                }
                            }


                            // 是否有不再提示并拒绝的权限。
                            if (settingDialog != null && AndPermission.hasAlwaysDeniedPermission(activity, deniedPermissions)) {
                                settingDialog.show();
                            }
                        }
                    }
                })
                .start();
    }

}

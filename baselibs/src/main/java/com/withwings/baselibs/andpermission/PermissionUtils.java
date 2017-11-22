package com.withwings.baselibs.andpermission;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;

import com.withwings.baselibs.andpermission.listener.AndPermissionListener;
import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.yanzhenjie.permission.SettingService;

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

    public static void checkPermission(final Context context, int requestCode, final AndPermissionListener andPermissionListener, String... permissions) {
        // 敲黑板，Arrays.asList 返回的集合不可修改
        final List<String> requestList = Arrays.asList(permissions);
        AndPermission.with(context)
                .requestCode(requestCode)
                .permission(permissions)
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
                        AlertDialog.newBuilder(context)
                                .setTitle("友好提醒")
                                .setMessage("你已拒绝过定位权限，沒有定位定位权限无法为你推荐附近的妹子，你看着办！")
                                .setPositiveButton("好，给你", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        rationale.resume();
                                    }
                                })
                                .setNegativeButton("我拒绝", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        rationale.cancel();
                                    }
                                }).show();
                    }
                })
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

    public static void mustPermission(final Activity activity, int requestCode, final AndPermissionListener andPermissionListener, String... permissions) {
        // 敲黑板，Arrays.asList 返回的集合不可修改
        final List<String> requestList = Arrays.asList(permissions);
        AndPermission.with(activity)
                .requestCode(requestCode)
                .permission(permissions)
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
                        AlertDialog.newBuilder(activity)
                                .setTitle("友好提醒")
                                .setMessage("你已拒绝过定位权限，沒有定位定位权限无法为你推荐附近的妹子，你看着办！")
                                .setPositiveButton("好，给你", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        rationale.resume();
                                    }
                                })
                                .setNegativeButton("我拒绝", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        rationale.cancel();
                                    }
                                }).show();
                    }
                })
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
                            if (AndPermission.hasAlwaysDeniedPermission(activity, deniedPermissions)) {
                                // 第一种：用AndPermission默认的提示语。
                                AndPermission.defaultSettingDialog(activity, requestCode).show();

                                // 第二种：用自定义的提示语。
                                AndPermission.defaultSettingDialog(activity, requestCode)
                                        .setTitle("权限申请失败")
                                        .setMessage("您拒绝了我们必要的一些权限，已经没法愉快的玩耍了，请在设置中授权！")
                                        .setPositiveButton("好，去设置")
                                        .show();

                                // 第三种：自定义dialog样式。
                                SettingService settingService = AndPermission.defineSettingDialog(activity, requestCode);

                                // 你的dialog点击了确定调用：
                                settingService.execute();
                                // 你的dialog点击了取消调用：
                                settingService.cancel();
                            }
                        }
                    }
                })
                .start();
    }

}

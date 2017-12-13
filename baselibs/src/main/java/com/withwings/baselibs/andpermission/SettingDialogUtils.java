package com.withwings.baselibs.andpermission;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.withwings.baselibs.R;
import com.withwings.baselibs.andpermission.listener.RationaleDialogListener;
import com.withwings.baselibs.andpermission.listener.SettingNegativeListener;
import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.yanzhenjie.permission.SettingDialog;
import com.yanzhenjie.permission.SettingService;

/**
 * 用户点击了不再提醒的提示框
 * 创建：WithWings 时间：2017/11/23.
 * Email:wangtong1175@sina.com
 */
public class SettingDialogUtils {

    private static final int DEFAULT_REQUEST_CODE = 101;

    private SettingDialogUtils() {

    }

    /**
     * 可以根据公司情况做一个请求的样式二次抽取，方便使用。
     * @param activity 界面
     * @param message 提示信息
     * @return 弹框对象
     */
    public static SettingDialogShow getRationaleDialog(Activity activity,String message){
        return null;
    }

    /**
     * 可以根据公司情况做一个请求的样式二次抽取，方便使用。
     * @param activity 界面
     * @param code 请求code
     * @return 弹框对象
     */
    public static SettingDialogShow getSettingDialog(Activity activity,int code){
        return null;
    }

    /**
     * 当用户勾选不再提醒时的提示框
     *
     * @param activity 提示框依赖的界面
     * @return 提示框对象，交给 PermissionUtils 即可
     */
    public static SettingDialogShow defaultSettingDialog(Activity activity) {
        return defaultSettingDialog(activity, DEFAULT_REQUEST_CODE);
    }

    /**
     * 当用户勾选不再提醒时的提示框
     *
     * @param activity    提示框依赖的界面
     * @param requestCode 当用户前往设置界面返回的 onActivityResult 回调 code
     * @return 提示框对象，交给 PermissionUtils 即可
     */
    public static SettingDialogShow defaultSettingDialog(Activity activity, int requestCode) {
        return new SettingDialogShow(AndPermission.defaultSettingDialog(activity, requestCode));
    }

    /**
     * 系统样式，自定义文案
     *
     * @param activity 提示框依赖的界面
     * @param title    提示框标题
     * @param message  提示信息
     * @param negative 取消按钮文字
     * @param positive 确认按钮文字
     * @return 弹框工具类
     */
    public static SettingDialogShow defaultSettingStyle(Activity activity, String title, String message, String negative, String positive) {
        return defaultSettingStyle(activity, DEFAULT_REQUEST_CODE, title, message, negative, null, positive);
    }

    /**
     * 系统样式，自定义文案
     *
     * @param activity                提示框依赖的界面
     * @param requestCode             当用户前往设置界面返回的 onActivityResult 回调 code
     * @param title                   提示框标题
     * @param message                 提示信息
     * @param negative                取消按钮文字
     * @param settingNegativeListener 取消监听
     * @param positive                确认按钮文字
     * @return 弹框工具类
     */
    public static SettingDialogShow defaultSettingStyle(Activity activity, final int requestCode, String title, String message, String negative, final SettingNegativeListener settingNegativeListener, String positive) {
        SettingDialog settingDialog = AndPermission.defaultSettingDialog(activity, requestCode);
        if (!TextUtils.isEmpty(title)) {
            settingDialog.setTitle(title);
        }
        if (!TextUtils.isEmpty(message)) {
            settingDialog.setMessage(message);
        }
        if (!TextUtils.isEmpty(positive)) {
            settingDialog.setPositiveButton(positive);
        }
        DialogInterface.OnClickListener negativeListener = null;
        if (settingNegativeListener != null) {
            negativeListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    settingNegativeListener.onNegative(requestCode);
                }
            };
        }
        if (!TextUtils.isEmpty(negative)) {
            settingDialog.setNegativeButton(negative, negativeListener);
        } else if (TextUtils.isEmpty(negative) && negativeListener != null) {
            settingDialog.setNegativeButton("取消", negativeListener);
        }
        return new SettingDialogShow(settingDialog);
    }

    /**
     * 自定义统一 Dialog
     *
     * @param activity 依赖的界面
     * @param title    提示框标题
     * @param message  提示信息
     * @param negative 取消按钮文字
     * @param positive 确认按钮文字
     * @return 弹框工具类
     */
    public static SettingDialogShow customSettingDialog(Activity activity, String title, String message, String negative, String positive) {
        return customSettingDialog(activity, DEFAULT_REQUEST_CODE, title, message, negative, null, positive);
    }

    /**
     * 自定义统一 Dialog
     *
     * @param activity                提示框依赖的界面
     * @param requestCode             当用户前往设置界面返回的 onActivityResult 回调 code
     * @param title                   提示框标题
     * @param message                 提示信息
     * @param negative                取消按钮文字
     * @param settingNegativeListener 取消监听
     * @param positive                确认按钮文字
     * @return 弹框工具类
     */
    public static SettingDialogShow customSettingDialog(Activity activity, final int requestCode, String title, String message, String negative, final SettingNegativeListener settingNegativeListener, String positive) {
        final Dialog dialog = new Dialog(activity, R.style.NoTitleDialog);
        View inflate = LayoutInflater.from(activity).inflate(R.layout.dialog_permission_setting, null);
        dialog.setContentView(inflate.findViewById(R.id.dialog_main));
        dialog.setContentView(R.layout.dialog_permission_setting);
        // 空白处无效
        dialog.setCanceledOnTouchOutside(false);
        // 返回按钮无效
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(dp2px(activity, 270), LinearLayout.LayoutParams.WRAP_CONTENT);
        }

        final SettingService settingService = AndPermission.defineSettingDialog(activity, requestCode);

        // 提示标题
        TextView tvTitle = dialog.findViewById(R.id.tv_title);
        if (TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        }
        // 提示文案
        TextView tvMessage = dialog.findViewById(R.id.tv_message);
        if (TextUtils.isEmpty(message)) {
            tvMessage.setText("您拒绝了我们必要的一些权限，已经没法愉快的玩耍了，请在设置中授权！");
        } else {
            tvMessage.setText(message);
        }
        // 取消按钮
        TextView tvNegative = dialog.findViewById(R.id.tv_negative);
        if (TextUtils.isEmpty(negative)) {
            tvNegative.setText("取消");
        } else {
            tvNegative.setText(negative);
        }
        tvNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // 你的dialog点击了取消调用：
                settingService.cancel();
                if (settingNegativeListener != null) {
                    settingNegativeListener.onNegative(requestCode);
                }
            }
        });
        // 确认按钮
        TextView tvPositive = dialog.findViewById(R.id.tv_positive);
        if (TextUtils.isEmpty(positive)) {
            tvPositive.setText("去设置");
        } else {
            tvPositive.setText(positive);
        }
        tvPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // 你的dialog点击了确定调用：
                settingService.execute();
                if (settingNegativeListener != null) {
                    settingNegativeListener.onPositive(requestCode);
                }
            }
        });
        return new SettingDialogShow(dialog);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 系统样式，自定义文案
     *
     * @param context  提示框依赖的界面
     * @param title    提示框标题
     * @param message  提示信息
     * @param negative 取消按钮文字
     * @param positive 确认按钮文字
     * @return 弹框工具类
     */
    public static SettingDialogShow defaultRationaleStyle(Context context, String title, String message, String negative, String positive) {
        return defaultRationaleStyle(context, DEFAULT_REQUEST_CODE, title, message, negative, positive, null);
    }


    /**
     * 系统样式，自定义文案
     *
     * @param context                 提示框依赖的界面
     * @param requestCode             请求 code
     * @param title                   提示框标题
     * @param message                 提示信息
     * @param negative                取消按钮文字
     * @param positive                确认按钮文字
     * @param rationaleDialogListener 提示框用户操作监听
     * @return 弹框工具类
     */
    public static SettingDialogShow defaultRationaleStyle(final Context context, int requestCode, final String title, final String message, final String negative, final String positive, final RationaleDialogListener rationaleDialogListener) {
        RationaleListener rationaleListener = new RationaleListener() {
            @Override
            public void showRequestPermissionRationale(final int requestCode, final Rationale rationale) {
                AlertDialog.Builder builder = AlertDialog.newBuilder(context);

                if (!TextUtils.isEmpty(title)) {
                    builder.setTitle(title);
                }
                if (!TextUtils.isEmpty(message)) {
                    builder.setMessage(message);
                }
                String positiveStr = null;
                if (!TextUtils.isEmpty(positive)) {
                    positiveStr = positive;
                }
                builder.setPositiveButton(positiveStr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (rationaleDialogListener != null) {
                            rationaleDialogListener.onPositive(requestCode);
                        }
                        rationale.resume();
                    }
                });
                String negativeStr = null;
                if (!TextUtils.isEmpty(negative)) {
                    negativeStr = negative;
                }
                builder.setNegativeButton(negativeStr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (rationaleDialogListener != null) {
                            rationaleDialogListener.onNegative(requestCode);
                        }
                        rationale.cancel();
                    }
                });
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (rationaleDialogListener != null) {
                            rationaleDialogListener.onDismiss(requestCode);
                        }
                    }
                });
                builder.show();
            }
        };

        return new SettingDialogShow(rationaleListener);
    }

    public static SettingDialogShow customRationaleDialog(final Context context, final String title, final String message, final String negative, final String positive) {
        return customRationaleDialog(context, title, message, negative, positive, null);
    }

    public static SettingDialogShow customRationaleDialog(final Context context, final String title, final String message, final String negative, final String positive, final RationaleDialogListener rationaleDialogListener) {
        RationaleListener rationaleListener = new RationaleListener() {
            @Override
            public void showRequestPermissionRationale(final int requestCode, final Rationale rationale) {
                final Dialog dialog = new Dialog(context, R.style.NoTitleDialog);
                View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_permission_setting, null);
                dialog.setContentView(inflate.findViewById(R.id.dialog_main));
                dialog.setContentView(R.layout.dialog_permission_setting);
                // 空白处无效
                // dialog.setCanceledOnTouchOutside(false);
                // 返回按钮无效
                // dialog.setCancelable(false);
                Window window = dialog.getWindow();
                if (window != null) {
                    window.setLayout(dp2px(context, 270), LinearLayout.LayoutParams.WRAP_CONTENT);
                }

                // 提示标题
                TextView tvTitle = dialog.findViewById(R.id.tv_title);
                if (TextUtils.isEmpty(title)) {
                    tvTitle.setVisibility(View.GONE);
                } else {
                    tvTitle.setVisibility(View.VISIBLE);
                    tvTitle.setText(title);
                }
                // 提示文案
                TextView tvMessage = dialog.findViewById(R.id.tv_message);
                if (TextUtils.isEmpty(message)) {
                    tvMessage.setText("您拒绝了我们的权限申请，这可能会造成我们的某些功能不可用，进而影响到您的体验，为了您的使用体验，希望您可以赋予我们该权限。");
                } else {
                    tvMessage.setText(message);
                }
                // 取消按钮
                TextView tvNegative = dialog.findViewById(R.id.tv_negative);
                if (TextUtils.isEmpty(negative)) {
                    tvNegative.setText("不允许");
                } else {
                    tvNegative.setText(negative);
                }
                tvNegative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (rationaleDialogListener != null) {
                            rationaleDialogListener.onNegative(requestCode);
                        }
                        rationale.cancel();
                    }
                });
                // 确认按钮
                TextView tvPositive = dialog.findViewById(R.id.tv_positive);
                if (TextUtils.isEmpty(positive)) {
                    tvPositive.setText("好");
                } else {
                    tvPositive.setText(positive);
                }
                tvPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (rationaleDialogListener != null) {
                            rationaleDialogListener.onPositive(requestCode);
                        }
                        rationale.resume();
                    }
                });
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (rationaleDialogListener != null) {
                            rationaleDialogListener.onDismiss(requestCode);
                        }
                    }
                });
                dialog.show();
            }
        };

        return new SettingDialogShow(rationaleListener);
    }

}

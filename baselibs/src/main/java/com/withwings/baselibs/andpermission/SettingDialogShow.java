package com.withwings.baselibs.andpermission;

import android.app.Dialog;
import android.support.annotation.NonNull;

import com.yanzhenjie.permission.RationaleListener;
import com.yanzhenjie.permission.SettingDialog;

/**
 * 展示类
 * 创建：WithWings 时间：2017/11/23.
 * Email:wangtong1175@sina.com
 */
public class SettingDialogShow {

    private int mTag;

    private SettingDialog mSettingDialog;

    private Dialog mDialog;

    private RationaleListener mRationaleListener;

    public SettingDialogShow(@NonNull SettingDialog settingDialog) {
        mTag = 0;
        mSettingDialog = settingDialog;
    }

    public SettingDialogShow(@NonNull Dialog dialog) {
        mTag = 1;
        mDialog = dialog;
    }

    public SettingDialogShow(RationaleListener rationaleListener) {
        mTag = 2;
        mRationaleListener = rationaleListener;
    }

    public void show() {
        if (mTag == 0 && mSettingDialog != null) {
            mSettingDialog.show();
        } else if (mTag == 1 && mDialog != null) {
            mDialog.show();
        }
    }

    public RationaleListener rationale() {
        if (mTag == 2) {
            return mRationaleListener;
        }
        return null;
    }

}

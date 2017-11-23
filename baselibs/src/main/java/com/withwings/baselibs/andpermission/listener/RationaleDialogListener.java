package com.withwings.baselibs.andpermission.listener;

/**
 * 询问用户提示框
 * 创建：WithWings 时间：2017/11/23.
 * Email:wangtong1175@sina.com
 */
public interface RationaleDialogListener {

    void onPositive(int requestCode);

    void onNegative(int requestCode);

    void onDismiss(int requestCode);

}

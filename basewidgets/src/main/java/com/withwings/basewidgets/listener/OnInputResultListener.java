package com.withwings.basewidgets.listener;

/**
 * 键盘监听
 * 创建：WithWings 时间：2017/11/20.
 * Email:wangtong1175@sina.com
 */
public interface OnInputResultListener {

    void onConfirm(String area, String phone, String extension);

    void onDismiss();

}
package com.withwings.baselibs.andpermission.listener;

/**
 * 最后的挽回提示
 * 创建：WithWings 时间：2017/11/23.
 * Email:wangtong1175@sina.com
 */
public interface SettingNegativeListener {

    // 点击确认按钮

    /**
     * 该方法只在自定义Dialog时起效
     *
     * @param requestCode 请求 code
     */
    void onPositive(int requestCode);

    // 点击取消按钮
    void onNegative(int requestCode);

}

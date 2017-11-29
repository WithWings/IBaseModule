package com.withwings.mvc.local.listener;

import com.withwings.mvc.bean.IPInfoBean;

/**
 * TODO
 * 创建：WithWings 时间：2017/11/29.
 * Email:wangtong1175@sina.com
 */
public interface OnQueryLocalListener {

    void onSuccess(IPInfoBean ipInfoBean);

    void onError();

}

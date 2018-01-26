package com.withwings.basewidgets.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.withwings.basewidgets.R;

/**
 * 网络加载阻拦框
 * 创建：WithWings 时间 2018/1/26
 * Email:wangtong1175@sina.com
 */
public class NetLoadingDialog extends ProgressDialog {

    public NetLoadingDialog(Context context) {
        super(context, R.style.NetLoadingDialog);
        initStyle();
    }

    public NetLoadingDialog(Context context, int theme) {
        super(context, R.style.NetLoadingDialog);
        initStyle();
    }

    private void initStyle() {
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.dialog_net_loading);
    }
}

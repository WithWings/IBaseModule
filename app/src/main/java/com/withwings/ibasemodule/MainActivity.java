package com.withwings.ibasemodule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.withwings.baseutils.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private Button mBtnExecutiveOperation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout(R.layout.activity_main);
        setTitleText("首页");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mBtnExecutiveOperation = findViewById(R.id.btn_executive_operation);
    }

    @Override
    protected void syncPage() {

    }

    @Override
    protected void initListener() {
        mBtnExecutiveOperation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_executive_operation:

                break;
        }
    }

    @Override
    protected void onLeftClick() {
//        ToastUtils.showToast(mActivity, "取消");
    }

    @Override
    protected void onRightClick() {
//        ToastUtils.showToast(mActivity, "确定");
    }
}

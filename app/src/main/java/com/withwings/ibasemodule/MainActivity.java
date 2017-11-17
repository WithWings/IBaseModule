package com.withwings.ibasemodule;

import android.view.View;
import android.widget.Button;

import com.withwings.baseutils.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private Button mBtnExecutiveOperation;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mBtnExecutiveOperation = findViewById(R.id.btn_executive_operation);
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
}

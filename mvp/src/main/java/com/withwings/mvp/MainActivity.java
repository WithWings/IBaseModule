package com.withwings.mvp;

import android.view.View;
import android.widget.Button;

import com.withwings.mvp.base.BaseMvpActivity;
import com.withwings.mvp.page.input.view.InputUserInfoActivity;
import com.withwings.mvp.page.read.view.ReadUserInfoActivity;

public class MainActivity extends BaseMvpActivity {

    private Button mBtnInputUserInfo;
    private Button mBtnReadUserInfo;

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mBtnInputUserInfo = findViewById(R.id.btn_input_user_info);
        mBtnReadUserInfo = findViewById(R.id.btn_read_user_info);
    }

    @Override
    protected void syncPage() {

    }

    @Override
    protected void initListener() {
        mBtnInputUserInfo.setOnClickListener(this);
        mBtnReadUserInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_input_user_info:
                startActivity(InputUserInfoActivity.class);
                break;
            case R.id.btn_read_user_info:
                startActivity(ReadUserInfoActivity.class);
                break;
        }
    }
}

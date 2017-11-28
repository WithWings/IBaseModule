package com.withwings.mvp.page.read.view;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.withwings.mvp.R;
import com.withwings.mvp.base.BaseMvpActivity;
import com.withwings.mvp.page.read.presenter.ReadUserInfoPresenter;

/**
 * 展示用户信息界面
 * 创建：WithWings 时间：2017/11/28.
 * Email:wangtong1175@sina.com
 */
public class ReadUserInfoActivity extends BaseMvpActivity implements ReadUserInfoView {

    private ReadUserInfoPresenter mReadUserInfoPresenter;
    private TextView mTvShowUserName;
    private TextView mTvShowUserPassword;
    private Button mBtnCheck;

    @Override
    protected int initLayout() {
        return R.layout.activity_read_user_info;
    }

    @Override
    protected void initData() {
        mReadUserInfoPresenter = new ReadUserInfoPresenter(this);
    }

    @Override
    protected void initView() {
        mTvShowUserName = findViewById(R.id.tv_show_user_name);
        mTvShowUserPassword = findViewById(R.id.tv_show_user_password);
        mBtnCheck = findViewById(R.id.btn_check);
    }

    @Override
    protected void syncPage() {

    }

    @Override
    protected void initListener() {
        mBtnCheck.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_check:
                mReadUserInfoPresenter.read();
                break;
        }
    }

    @Override
    public SharedPreferences getSharedPreferences() {
        return getSharedPreferences("user_info", MODE_PRIVATE);
    }

    @Override
    public void setUserName(@NonNull String name) {
        mTvShowUserName.setText(name);
    }

    @Override
    public void setUserPassword(@NonNull String password) {
        mTvShowUserPassword.setText(password);
    }
}

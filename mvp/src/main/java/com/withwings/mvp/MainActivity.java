package com.withwings.mvp;

import android.view.View;
import android.widget.Button;

import com.withwings.baseutils.base.BaseActivity;
import com.withwings.mvp.page.input.view.InputUserInfoActivity;
import com.withwings.mvp.page.read.view.ReadUserInfoActivity;

/**
 * MVP 模式的 Activity
 * View 的特点有两个，
 * 1.调用的方法不要涉及到参数，也就是不传递参数出去。
 * 2.对需要的参数，开放获取的接口，通过接口获取。
 * Model 的特点是只处理数据，也就是说，model 不做任何获取参数的操作，所有操作都是 Presenter 调用方法时传递过来的。
 * <p>
 * 创建：WithWings 时间：2017/11/27.
 * Email:wangtong1175@sina.com
 */
public class MainActivity extends BaseActivity {

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

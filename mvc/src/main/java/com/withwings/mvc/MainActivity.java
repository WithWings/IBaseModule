package com.withwings.mvc;

import android.view.View;
import android.widget.Button;

import com.withwings.baseutils.base.BaseActivity;
import com.withwings.mvc.local.QueryLocalIpLocationActivity;
import com.withwings.mvc.query.QueryInputIpLocationActivity;

/**
 * 本地信息
 * 创建：WithWings 时间：2017/11/28.
 * Email:wangtong1175@sina.com
 */
public class MainActivity extends BaseActivity {

    private Button mBtnQueryLocalIpLocation;
    private Button mBtnQueryInputIpLocation;

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mBtnQueryLocalIpLocation = findViewById(R.id.btn_query_local_ip_location);
        mBtnQueryInputIpLocation = findViewById(R.id.btn_query_input_ip_location);
    }

    @Override
    protected void syncPage() {

    }

    @Override
    protected void initListener() {
        mBtnQueryLocalIpLocation.setOnClickListener(this);
        mBtnQueryInputIpLocation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_query_local_ip_location:
                startActivity(QueryLocalIpLocationActivity.class);
                break;
            case R.id.btn_query_input_ip_location:
                startActivity(QueryInputIpLocationActivity.class);
                break;
        }
    }

    @Override
    protected void onLeftClick() {

    }

    @Override
    protected void onRightClick() {

    }
}

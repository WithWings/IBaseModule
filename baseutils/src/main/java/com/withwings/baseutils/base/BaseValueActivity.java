package com.withwings.baseutils.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * TODO
 * 创建：WithWings 时间 2018/1/30
 * Email:wangtong1175@sina.com
 */
public class BaseValueActivity extends AppCompatActivity {

    protected Activity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = this;
    }
}

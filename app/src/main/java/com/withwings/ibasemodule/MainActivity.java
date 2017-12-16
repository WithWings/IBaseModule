package com.withwings.ibasemodule;

import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.withwings.baseutils.base.BaseActivity;
import com.withwings.basewidgets.image.newest.bean.NewestMessage;
import com.withwings.basewidgets.image.newest.listener.OnNewestPopupClickListener;
import com.withwings.basewidgets.image.newest.view.NewestPopupWindow;

public class MainActivity extends BaseActivity {

    private Button mBtnExecutiveOperation;

    @Override
    protected int initLayout() {
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
                new NewestPopupWindow(mActivity).listener(new OnNewestPopupClickListener() {
                    @Override
                    public void onClick(View view, NewestMessage newestMessage) {
                        Toast.makeText(mActivity, newestMessage.getName(), Toast.LENGTH_SHORT).show();
                    }
                }).size(TypedValue.COMPLEX_UNIT_DIP, 120, 120).show(v);
                break;
        }
    }
}

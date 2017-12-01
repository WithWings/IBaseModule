package com.withwings.mvp.page.input.view;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.withwings.mvp.R;
import com.withwings.mvp.base.MvpBaseActivity;
import com.withwings.mvp.page.input.model.InputUserInfoModelImpl;
import com.withwings.mvp.page.input.presenter.InputUserInfoPresenter;

/**
 * 用户信息输入界面
 * 创建：WithWings 时间：2017/11/27.
 * Email:wangtong1175@sina.com
 */
public class InputUserInfoActivity extends MvpBaseActivity<InputUserInfoActivity, InputUserInfoPresenter, InputUserInfoModelImpl> implements InputUserInfoView {

    private InputUserInfoPresenter mInputUserInfoPresenter;
    private TextView mEtInputUserName;
    private TextView mEtInputUserPassword;
    private Button mBtnSubmit;

    @NonNull
    @Override
    public InputUserInfoPresenter createPresenter() {
        return new InputUserInfoPresenter(this);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_input_user_info;
    }

    @Override
    protected void initData() {
        mInputUserInfoPresenter = new InputUserInfoPresenter(this);
    }

    @Override
    protected void initView() {
        mEtInputUserName = findViewById(R.id.et_input_user_name);
        mEtInputUserPassword = findViewById(R.id.et_input_user_password);
        mBtnSubmit = findViewById(R.id.btn_submit);
    }

    @Override
    protected void syncPage() {

    }

    @Override
    protected void initListener() {
        mBtnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                mInputUserInfoPresenter.submit();
                break;
        }
    }

    // 对外暴露获取数据的方法

    @Override
    public String getName() {
        return mEtInputUserName.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return mEtInputUserPassword.getText().toString().trim();
    }

    @Override
    public SharedPreferences getSharedPreferences() {
        return getSharedPreferences("user_info", MODE_PRIVATE);
    }

    // 对外暴露更新界面的方法

    @Override
    public void showToast(@NonNull String toast) {
        Toast.makeText(mActivity, toast, Toast.LENGTH_SHORT).show();
    }
}

package com.withwings.mvp.page.read.presenter;

import com.withwings.mvp.R;
import com.withwings.mvp.page.read.model.ReadUserInfoModel;
import com.withwings.mvp.page.read.model.ReadUserInfoModelLogic;
import com.withwings.mvp.page.read.model.listener.OnReadListener;
import com.withwings.mvp.page.read.view.ReadUserInfoView;

/**
 * 逻辑处理器
 * 创建：WithWings 时间：2017/11/28.
 * Email:wangtong1175@sina.com
 */
public class ReadUserInfoPresenter {

    private final ReadUserInfoView mReadUserInfoView;

    private final ReadUserInfoModel mReadUserInfoModel;

    public ReadUserInfoPresenter(ReadUserInfoView readUserInfoView) {
        mReadUserInfoView = readUserInfoView;
        mReadUserInfoModel = new ReadUserInfoModelLogic();
    }

    public void read() {
        mReadUserInfoModel.read(mReadUserInfoView.getSharedPreferences(), new OnReadListener() {

            @Override
            public void read(String name, String password) {
                mReadUserInfoView.setUserName(mReadUserInfoView.getActivity().getString(R.string.show_user_name, name));
                mReadUserInfoView.setUserPassword(mReadUserInfoView.getActivity().getString(R.string.show_user_password, password));
            }
        });
    }
}

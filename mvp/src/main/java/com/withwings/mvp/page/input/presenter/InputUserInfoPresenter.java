package com.withwings.mvp.page.input.presenter;

import com.withwings.mvp.page.input.model.InputUserInfoModel;
import com.withwings.mvp.page.input.model.InputUserInfoModelImpl;
import com.withwings.mvp.page.input.model.listener.OnSubmitListener;
import com.withwings.mvp.page.input.view.InputUserInfoView;

/**
 * 逻辑交互控制器
 * 创建：WithWings 时间：2017/11/27.
 * Email:wangtong1175@sina.com
 */
public class InputUserInfoPresenter {


    private final InputUserInfoView mInputUserInfoView;

    private final InputUserInfoModel mInputUserInfoModel;

    public InputUserInfoPresenter(InputUserInfoView inputUserInfoView) {
        mInputUserInfoView = inputUserInfoView;
        mInputUserInfoModel = new InputUserInfoModelImpl();
    }

    public void submit() {
        mInputUserInfoModel.submit(mInputUserInfoView.getName(), mInputUserInfoView.getPassword(), new OnSubmitListener() {

            @Override
            public void onSuccess() {
                mInputUserInfoView.showToast("成功");
            }

            @Override
            public void onFailed(String string) {
                mInputUserInfoView.showToast(string);
            }
        }, mInputUserInfoView.getSharedPreferences());
    }
}

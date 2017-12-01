package com.withwings.mvp.page.input.presenter;

import android.support.annotation.NonNull;

import com.withwings.mvp.base.MvpBasePresenter;
import com.withwings.mvp.page.input.model.InputUserInfoModelImpl;
import com.withwings.mvp.page.input.model.listener.OnSubmitListener;
import com.withwings.mvp.page.input.view.InputUserInfoActivity;

/**
 * 逻辑交互控制器
 * 创建：WithWings 时间：2017/11/27.
 * Email:wangtong1175@sina.com
 */
public class InputUserInfoPresenter extends MvpBasePresenter<InputUserInfoActivity, InputUserInfoModelImpl> {

    public InputUserInfoPresenter(@NonNull InputUserInfoActivity view) {
        super(view);
    }

    @Override
    @NonNull
    protected InputUserInfoModelImpl createModel() {
        return new InputUserInfoModelImpl();
    }

    //    public InputUserInfoPresenter(InputUserInfoView inputUserInfoView) {
    //        getView() = inputUserInfoView;
    //        mInputUserInfoModel = new InputUserInfoModelImpl();
    //    }

    public void submit() {
        getModel().submit(getView().getName(), getView().getPassword(), new OnSubmitListener() {

            @Override
            public void onSuccess() {
                getView().showToast("成功");
            }

            @Override
            public void onFailed(String string) {
                getView().showToast(string);
            }
        }, getView().getSharedPreferences());
    }
}

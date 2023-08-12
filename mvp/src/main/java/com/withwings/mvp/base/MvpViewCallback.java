package com.withwings.mvp.base;

import androidx.annotation.NonNull;

/**
 * TODO
 * 创建：WithWings 时间：2017/12/1.
 * Email:wangtong1175@sina.com
 */
public interface MvpViewCallback<V extends MvpBaseView, P extends MvpBasePresenter<V, ? extends MvpBaseModel>> {

    @NonNull
    P createPresenter();

    P getPresenter();

    void setPresenter(P presenter);

    V getMvpView();

}

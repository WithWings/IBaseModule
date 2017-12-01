package com.withwings.mvp.base;

import android.support.annotation.NonNull;

/**
 * Presenter 中转控制器
 * 创建：WithWings 时间：2017/12/1.
 * Email:wangtong1175@sina.com
 */
public abstract class MvpBasePresenter<V extends MvpBaseView, M extends MvpBaseModel> {

    public V mView;
    public M mModel;

    public MvpBasePresenter(V view) {
        mView = view;
        mModel = createModel();
    }

    @NonNull
    protected abstract M createModel();

    public V getView() {
        return mView;
    }

    public M getModel() {
        return mModel;
    }

}

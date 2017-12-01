package com.withwings.mvp.base;

import com.withwings.baseutils.base.BaseFragment;

/**
 * Fragment Mvp 框架
 * 创建：WithWings 时间：2017/12/1.
 * Email:wangtong1175@sina.com
 */
public abstract class MvpBaseFragment<V extends MvpBaseFragment, P extends MvpBasePresenter<V, M>, M extends MvpBaseModel> extends BaseFragment implements MvpBaseView, MvpViewCallback<V, P> {

    protected P mPresenter;

    @Override
    public P getPresenter() {
        return mPresenter;
    }

    @Override
    public void setPresenter(P presenter) {
        mPresenter = presenter;
    }

    @Override
    public V getMvpView() {
        return (V) this;
    }
}

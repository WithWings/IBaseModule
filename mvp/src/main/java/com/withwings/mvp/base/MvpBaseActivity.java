package com.withwings.mvp.base;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.withwings.baseutils.base.BaseActivity;

/**
 * MVP 框架
 * 创建：WithWings 时间：2017/12/1.
 * Email:wangtong1175@sina.com
 */
public abstract class MvpBaseActivity<V extends MvpBaseActivity, P extends MvpBasePresenter<V, M>, M extends MvpBaseModel> extends BaseActivity implements MvpBaseView, MvpViewCallback<V, P> {

    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        createPresenter();
        super.onCreate(savedInstanceState);
    }

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

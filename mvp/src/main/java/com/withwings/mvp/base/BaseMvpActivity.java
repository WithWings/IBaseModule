package com.withwings.mvp.base;

import android.content.Context;

import com.withwings.baseutils.base.BaseActivity;

/**
 * MVP 模式的 Activity
 * View 的特点有两个，
 * 1.调用的方法不要涉及到参数，也就是不传递参数出去。
 * 2.对需要的参数，开放获取的接口，通过接口获取。
 * Model 的特点是只处理数据，也就是说，model 不做任何获取参数的操作，所有操作都是 Presenter 调用方法时传递过来的。
 * <p>
 * 创建：WithWings 时间：2017/11/27.
 * Email:wangtong1175@sina.com
 */
public abstract class BaseMvpActivity extends BaseActivity {

    public BaseMvpActivity getActivity() {
        return this;
    }

    public Context getContext() {
        return this;
    }

}

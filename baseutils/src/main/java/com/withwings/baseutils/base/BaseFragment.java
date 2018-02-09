package com.withwings.baseutils.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.withwings.baseutils.R;

/**
 * Fragment 基础类
 * 目前市面上最低版本不过是4.0 所以没必要再使用 v4 包的 Fragment
 * 创建：WithWings 时间：2017/12/1.
 * Email:wangtong1175@sina.com
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    protected Activity mActivity;

    // 布局文件嵌入位置
    private ViewStub mVsLoadMainLayout;

    /**
     * 关于使用
     * FragmentManager manager = getFragmentManager();
     * FragmentTransaction transaction = manager.beginTransaction();
     * Fragment1 fragment1 = new Fragment1();
     * transaction.add(R.id.fragment_container, fragment1);
     * transaction.commit();
     */

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();

        View layout = inflater.inflate(initLayout(), container, false);

        mVsLoadMainLayout = layout.findViewById(R.id.vs_load_main_layout);

        mVsLoadMainLayout.setLayoutResource(initLayout());
        mVsLoadMainLayout.inflate();

        init();

        return layout;
    }

    /**
     * 获得布局文件
     *
     * @return 布局文件
     */
    protected abstract @LayoutRes
    int initLayout();

    private void init() {

        initData();

        initView();

        syncPage();

        initListener();

    }

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化界面
     */
    protected abstract void initView();

    /**
     * 根据数据同步界面
     */
    protected abstract void syncPage();

    /**
     * 设置监听
     */
    protected abstract void initListener();

    @Override
    public abstract void onClick(View v);

    /**
     * 打开某个界面 singleTask 效果
     *
     * @param activityClass 界面
     */
    protected void startActivityForOnlyOne(Class<? extends Activity> activityClass) {
        startActivityForOnlyOne(activityClass, 0, false);
    }

    /**
     * 打开某个界面 singleTask 效果 获得打开界面返回值
     *
     * @param activityClass 界面
     * @param requestCode   请求标记
     */
    protected void startActivityForOnlyOne(Class<? extends Activity> activityClass, int requestCode) {
        startActivityForOnlyOne(activityClass, requestCode, true);
    }

    private void startActivityForOnlyOne(Class<? extends Activity> activityClass, int requestCode, boolean forResult) {
        Intent intent = new Intent(mActivity, activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (forResult) {
            startActivityForResult(intent, requestCode);
        } else {
            startActivity(intent);
        }
    }

    /**
     * 打开一个界面
     *
     * @param activityClass 界面
     */
    protected void startActivity(Class<? extends Activity> activityClass) {
        startActivity(activityClass, 0, false);
    }

    /**
     * 打开一个界面 获得打开界面返回值
     *
     * @param activityClass 界面
     * @param requestCode   请求标记
     */
    protected void startActivityForResult(Class<? extends Activity> activityClass, int requestCode) {
        startActivity(activityClass, requestCode, true);
    }

    private void startActivity(Class<? extends Activity> activityClass, int requestCode, boolean forResult) {
        Intent intent = new Intent(mActivity, activityClass);
        if (forResult) {
            startActivityForResult(intent, requestCode);
        } else {
            startActivity(intent);
        }
    }
}

package com.withwings.baseutils.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 基本 Activity 封装
 * 继承自 Activity 默认是无标题的，AppCompatActivity 是 v7 包的，因为兼容性问题自带标题栏
 * 如果使用自定义的界面不需要标题，请设置 style 属性 android:windowNoTitle 为 true
 * 创建：WithWings 时间：2017/10/25.
 * Email:wangtong1175@sina.com
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected Activity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = this;

        if (BaseApplication.mActivities != null) {
            BaseApplication.mActivities.add(this);
        }

        setContentView(initLayout());

        initData();

        initView();

        syncPage();

        initListener();
    }

    /**
     * 获得布局文件
     * @return 布局文件
     */
    protected abstract @LayoutRes
    int initLayout();

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
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null && v != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                System.out.println("Click int！");
                return false;
            } else {
                // 点击的是输入框外的区域，需要拦截处理
                System.out.println("Click out！");
                return true;
            }
        }
        return false;
    }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (BaseApplication.mActivities != null) {
            BaseApplication.mActivities.remove(this);
        }
    }

    public void exitApp() {
        if (BaseApplication.mActivities != null) {
            for (BaseActivity activity : BaseApplication.mActivities) {
                activity.finish();
            }
        }
    }
}
package com.withwings.baseutils.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 基本 Activity 封装
 * 创建：WithWings 时间：2017/10/25.
 * Email:wangtong1175@sina.com
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener {

    protected Activity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = this;

        if (BaseApplication.mActivitys != null ) {
            BaseApplication.mActivitys.add(this);
        }

        setContentView(setLayout());

        initData();

        initView();

        initListener();
    }

    protected abstract int setLayout();

    protected abstract void initData();

    protected abstract void initView();

    protected abstract void initListener();

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public abstract void onClick(View v);

    /**
     * 打开某个界面 singleTask 效果
     * @param activityClass 界面
     */
    protected void startActivityForOnlyOne(Class<? extends Activity> activityClass){
        startActivityForOnlyOne(activityClass,0,false);
    }

    /**
     * 打开某个界面 singleTask 效果 获得打开界面返回值
     * @param activityClass 界面
     * @param requestCode 请求标记
     */
    protected void startActivityForOnlyOne(Class<? extends Activity> activityClass,int requestCode){
        startActivityForOnlyOne(activityClass,requestCode,true);
    }

    private void startActivityForOnlyOne(Class<? extends Activity> activityClass,int requestCode,boolean forResult){
        Intent intent = new Intent(mActivity,activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if(forResult) {
            startActivityForResult(intent,requestCode);
        } else {
            startActivity(intent);
        }
    }

    /**
     * 打开一个界面
     * @param activityClass 界面
     */
    protected void startActivity(Class<? extends Activity> activityClass) {
        startActivity(activityClass,0,false);
    }

    /**
     * 打开一个界面 获得打开界面返回值
     * @param activityClass 界面
     * @param requestCode 请求标记
     */
    protected void startActivityForResult(Class<? extends Activity> activityClass,int requestCode) {
        startActivity(activityClass,requestCode,true);
    }

    private void startActivity(Class<? extends Activity> activityClass,int requestCode,boolean forResult){
        Intent intent = new Intent(mActivity,activityClass);
        if(forResult) {
            startActivityForResult(intent,requestCode);
        } else {
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (BaseApplication.mActivitys != null ) {
            BaseApplication.mActivitys.remove(this);
        }
    }
}
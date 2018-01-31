package com.withwings.baseutils.base;

import android.app.Activity;
import android.content.Intent;

/**
 * TODO
 * 创建：WithWings 时间 2018/1/30
 * Email:wangtong1175@sina.com
 */
public abstract class BaseOpenActivity extends BaseValueActivity {

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

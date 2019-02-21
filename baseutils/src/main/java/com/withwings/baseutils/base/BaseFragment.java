package com.withwings.baseutils.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.withwings.baseutils.R;

import java.util.HashMap;
import java.util.Map;


/**
 * Fragment 基础类
 * 目前市面上最低版本不过是4.0 所以没必要再使用 v4 包的 Fragment
 * 创建：WithWings 时间：2017/12/1.
 * Email:wangtong1175@sina.com
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    protected Activity mActivity;


    private Map<Integer, Dialog> mDialogMap;
    // 布局文件嵌入位置
    private ViewStub mVsLoadMainLayout;
    private View mTitleBar;
    private View mLayout;

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

        mLayout = inflater.inflate(R.layout.fragment_base, container, false);
        mTitleBar = mLayout.findViewById(R.id.title_bar);
        mVsLoadMainLayout = mLayout.findViewById(R.id.vs_load_main_layout);

        // Title
        LinearLayout titleLeft = mLayout.findViewById(R.id.title_left);
        LinearLayout titleRight = mLayout.findViewById(R.id.title_right);
        titleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLeftClick();
            }
        });
        titleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRightClick();
            }
        });

        setLayout(initLayout(), titleText(), leftText(), rightText());
        return mLayout;
    }

    private void setLayout(@LayoutRes int layout, String title, String left, String right) {
        mVsLoadMainLayout.setLayoutResource(layout);
        mVsLoadMainLayout.inflate();
        if (!TextUtils.isEmpty(title)) {
            TextView titleText = mLayout.findViewById(R.id.title_text);
            titleText.setText(title);
        }
        if (!TextUtils.isEmpty(left)) {
            TextView titleLeftText = mLayout.findViewById(R.id.title_left_text);
            titleLeftText.setVisibility(View.VISIBLE);
            titleLeftText.setText(left);
        }
        if (!TextUtils.isEmpty(right)) {
            TextView titleRightText = mLayout.findViewById(R.id.title_right_text);
            titleRightText.setVisibility(View.VISIBLE);
            titleRightText.setText(right);
        }

        init();
    }

    protected void hideTitle(boolean hide) {
        mTitleBar.setVisibility(hide ? View.GONE : View.VISIBLE);
    }

    private void init() {

        initData();

        initView(mLayout);

        syncPage();

        initListener();
    }

    /**
     * 获得布局文件
     *
     * @return 布局文件
     */
    protected abstract @LayoutRes
    int initLayout();

    protected String titleText() {
        return null;
    }

    protected String leftText() {
        return null;
    }

    protected String rightText() {
        return null;
    }

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化界面
     *
     * @param layout 布局文件对象
     */
    protected abstract void initView(View layout);

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

    protected abstract void onLeftClick();

    protected abstract void onRightClick();

    protected void exitApp() {
        if (BaseApplication.mActivities != null) {
            for (BaseActivity activity : BaseApplication.mActivities) {
                activity.finish();
            }
        }
    }

    protected void showNetDialog(final int tag) {
        if (mDialogMap == null) {
            mDialogMap = new HashMap<>();
        }
        Dialog dialog;
        if (mDialogMap.containsKey(tag)) {
            dialog = mDialogMap.get(tag);
        } else {
            // 自定义的 Dialog 加在这里
            dialog = new Dialog(mActivity);
            mDialogMap.put(tag, dialog);
        }
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                int key = -1;
                for (Map.Entry<Integer, Dialog> entry : mDialogMap.entrySet()) {
                    if (dialog.equals(entry.getValue())) {
                        key = entry.getKey();
                        break;
                    }
                }
                dismissNetDialog(key);
            }
        });
        dialog.show();
    }

    protected void dismissNetDialog(int tag) {
        if (mDialogMap != null) {
            Dialog dialog = mDialogMap.get(tag);
            if (dialog != null) {
                dialog.dismiss();
                mDialogMap.remove(tag);
            }
            if (mDialogMap.size() == 0) {
                mDialogMap = null;
            }
        }
    }
}

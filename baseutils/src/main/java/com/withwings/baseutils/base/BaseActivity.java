package com.withwings.baseutils.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.withwings.baseutils.R;

import java.util.HashMap;
import java.util.Map;

/**
 * 基本 Activity 封装
 * 继承自 Activity 默认是无标题的，AppCompatActivity 是 v7 包的，因为兼容性问题自带标题栏
 * 如果使用自定义的界面不需要标题，请设置 style 属性 android:windowNoTitle 为 true
 * 创建：WithWings 时间：2017/10/25.
 * Email:wangtong1175@sina.com
 */
public abstract class BaseActivity extends BaseOpenActivity implements View.OnClickListener {

    private Map<Integer, Dialog> mDialogMap;

    private ViewStub mVsLoadMainLayout;
    private View mTitleBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (BaseApplication.mActivities != null) {
            BaseApplication.mActivities.add(this);
        }

        setContentView(R.layout.activity_base);
        mTitleBar = findViewById(R.id.title_bar);
        mVsLoadMainLayout = findViewById(R.id.vs_load_main_layout);

        mVsLoadMainLayout.setLayoutResource(initLayout());
        mVsLoadMainLayout.inflate();

        init();

        // Title
        RelativeLayout titleLeft = findViewById(R.id.title_left);
        RelativeLayout titleRight = findViewById(R.id.title_right);
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
    }

    private void setLayout(@LayoutRes int layout, String title, String left, String right) {
        mVsLoadMainLayout.setLayoutResource(layout);
//        mVsLoadMainLayout.inflate();
        if(!TextUtils.isEmpty(title)) {
            TextView titleText = findViewById(R.id.title_text);
            titleText.setText(title);
        }
    }

    protected void setLeftTag(@DrawableRes int left) {
        if(left != 0) {
            setLeftTag(ContextCompat.getDrawable(this, left));
        }
    }

    protected void setLeftTag(Drawable left) {
        if(left != null) {
            ImageView titleLeftImage = findViewById(R.id.title_left_image);
            titleLeftImage.setVisibility(View.VISIBLE);
            titleLeftImage.setImageDrawable(left);
        }
    }

    protected void setLeftText(@StringRes int left) {
        if(left != 0) {
            setLeftText(mActivity.getString(left));
        }
    }

    protected void setLeftText(String left) {
        if(!TextUtils.isEmpty(left)) {
            TextView titleLeftText = findViewById(R.id.title_left_text);
            titleLeftText.setVisibility(View.VISIBLE);
            titleLeftText.setText(left);
        }
    }

    protected void setRightTag(@DrawableRes int right) {
        if(right != 0) {
            setRightTag(ContextCompat.getDrawable(this, right));
        }
    }

    protected void setRightTag(Drawable right) {
        if(right != null) {
            ImageView titleRightImage = findViewById(R.id.title_right_image);
            titleRightImage.setVisibility(View.VISIBLE);
            titleRightImage.setImageDrawable(right);
        }
    }

    protected void setRightText(@StringRes int right) {
        if(right != 0) {
            setRightText(getString(right));
        }
    }

    protected void setRightText(String right) {
        if(!TextUtils.isEmpty(right)) {
            TextView titleRightText = findViewById(R.id.title_right_text);
            titleRightText.setVisibility(View.VISIBLE);
            titleRightText.setText(right);
        }
    }

    protected void hideTitle(boolean hide) {
        mTitleBar.setVisibility(hide ? View.GONE : View.VISIBLE);
    }

    private void init() {

        initData();

        initView();

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
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(KeyEvent.KEYCODE_BACK == event.getKeyCode()) {
            onLeftClick();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected abstract void onLeftClick();

    protected abstract void onRightClick();

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (BaseApplication.mActivities != null) {
            BaseApplication.mActivities.remove(this);
        }
    }

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
                if(mDialogMap == null) {
                    return;
                }
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
            if (dialog != null && dialog.isShowing() && mActivity != null && !mActivity.isFinishing()) {
                dialog.dismiss();
                mDialogMap.remove(tag);
            }
            if (mDialogMap.size() == 0) {
                mDialogMap = null;
            }
        }
    }
}
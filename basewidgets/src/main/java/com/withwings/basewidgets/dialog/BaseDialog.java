package com.withwings.basewidgets.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.withwings.basewidgets.R;
import com.withwings.basewidgets.dialog.listener.OnDialogClickListener;

/**
 * Dialog 基类
 * 创建：WithWings 时间：2017/11/23.
 * Email:wangtong1175@sina.com
 */
public abstract class BaseDialog implements View.OnClickListener {

    protected Context mContext;

    protected Dialog mDialog;

    protected OnDialogClickListener mOnDialogClickListener;

    public BaseDialog(Context context, @LayoutRes int layoutId) {
        mContext = context;
        View inflate = LayoutInflater.from(mContext).inflate(layoutId, null);
        mDialog = new Dialog(context, R.style.NoTitleDialog);
        mDialog.setContentView(inflate);
        Window window = mDialog.getWindow();
        if (window != null) {
            window.setLayout(dp2px(context, 270), LinearLayout.LayoutParams.WRAP_CONTENT);
        }

        initView(mDialog);
    }

    /**
     * 获得Layout里的View
     *
     * @param dialog 弹出的Dialog对象
     */
    protected abstract void initView(Dialog dialog);

    public abstract BaseDialog setTitle(String title);

    public abstract BaseDialog setMessage(String message);

    public abstract BaseDialog setPositive(String positive);

    public abstract BaseDialog setNegative(String negative);

    public BaseDialog setMustSelect(boolean mustSelect) {
        if (mustSelect) {
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setCancelable(false);
        } else {
            mDialog.setCanceledOnTouchOutside(true);
            mDialog.setCancelable(true);
        }
        return this;
    }

    public BaseDialog setOnDialogClickListener(OnDialogClickListener onDialogClickListener) {
        mOnDialogClickListener = onDialogClickListener;
        return this;
    }

    public void show() {
        if (mDialog != null) {
            mDialog.show();
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    protected int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}

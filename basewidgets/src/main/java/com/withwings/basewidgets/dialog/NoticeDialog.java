package com.withwings.basewidgets.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.withwings.basewidgets.R;

/**
 * 通知类弹窗
 * 创建：WithWings 时间：2017/11/23.
 * Email:wangtong1175@sina.com
 */
public class NoticeDialog extends BaseDialog {

    private TextView mTvTitle;
    private TextView mTvMessage;
    private TextView mTvPositive;

    private NoticeDialog(Context context) {
        super(context, R.layout.dialog_notice);
    }

    public static NoticeDialog create(Context context) {
        return new NoticeDialog(context);
    }


    @Override
    protected void initView(Dialog dialog) {
        mTvTitle = dialog.findViewById(R.id.tv_title);
        mTvTitle.setVisibility(View.GONE);
        mTvMessage = dialog.findViewById(R.id.tv_message);
        mTvPositive = dialog.findViewById(R.id.tv_positive);
    }

    @Override
    public NoticeDialog setTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            mTvTitle.setVisibility(View.GONE);
        } else {
            mTvTitle.setVisibility(View.VISIBLE);
            mTvTitle.setText(title);
        }
        return this;
    }

    @Override
    public NoticeDialog setMessage(String message) {
        mTvMessage.setText(message);
        return this;
    }

    @Override
    public NoticeDialog setPositive(String positive) {
        mTvPositive.setOnClickListener(this);
        if (TextUtils.isEmpty(positive)) {
            mTvPositive.setText("我知道了");
        } else {
            mTvPositive.setText(positive);
        }
        return this;
    }

    @Override
    public NoticeDialog setNegative(String negative) {
        return this;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_positive) {
            if (mOnDialogClickListener != null) {
                mOnDialogClickListener.onPositive(v);
            }
            mDialog.dismiss();
        }
    }
}

package com.withwings.basewidgets.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.withwings.basewidgets.R;

/**
 * 确定取消选择框
 * 创建：WithWings 时间：2017/11/24.
 * Email:wangtong1175@sina.com
 */
public class ControlDialog extends BaseDialog {

    private TextView mTvTitle;
    private TextView mTvMessage;
    private TextView mTvNegative;
    private TextView mTvPositive;

    private static final String POSITIVE_TEXT = "确定";
    private static final String NEGATIVE_TEXT = "取消";

    private static boolean DEFAULT_ORDER = true;

    private ControlDialog(Context context, boolean defaultOrder) {
        super(context, R.layout.dialog_control, defaultOrder);
    }

    public static ControlDialog create(Context context) {
        return create(context, true);
    }

    public static ControlDialog create(Context context, boolean defaultOrder) {
        return new ControlDialog(context, defaultOrder);
    }

    @Override
    protected void initView() {
        mTvTitle = mDialog.findViewById(R.id.tv_title);
        mTvMessage = mDialog.findViewById(R.id.tv_message);
        if (mDefaultOrder) {
            mDialog.findViewById(R.id.ll_default_view).setVisibility(View.VISIBLE);
            mDialog.findViewById(R.id.ll_new_view).setVisibility(View.GONE);
            mTvNegative = mDialog.findViewById(R.id.tv_negative);
            mTvPositive = mDialog.findViewById(R.id.tv_positive);
        } else {
            mDialog.findViewById(R.id.ll_default_view).setVisibility(View.GONE);
            mDialog.findViewById(R.id.ll_new_view).setVisibility(View.VISIBLE);
            mTvNegative = mDialog.findViewById(R.id.tv_new_negative);
            mTvPositive = mDialog.findViewById(R.id.tv_new_positive);
        }
    }

    @Override
    protected void defaultView() {
        mTvTitle.setVisibility(View.GONE);
        mTvNegative.setText(NEGATIVE_TEXT);
        mTvNegative.setOnClickListener(this);
        mTvPositive.setText(POSITIVE_TEXT);
        mTvPositive.setOnClickListener(this);
    }

    @Override
    public BaseDialog setTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            mTvTitle.setVisibility(View.GONE);
        } else {
            mTvTitle.setVisibility(View.VISIBLE);
            mTvTitle.setText(title);
        }
        return this;
    }

    @Override
    public BaseDialog setMessage(String message) {
        mTvMessage.setText(message);
        return this;
    }

    @Override
    public BaseDialog setPositive(String positive) {
        if (!TextUtils.isEmpty(positive)) {
            mTvPositive.setText(positive);
        }
        return this;
    }

    @Override
    public BaseDialog setNegative(String negative) {
        if (!TextUtils.isEmpty(negative)) {
            mTvNegative.setText(negative);
        }
        return this;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_negative || i == R.id.tv_new_negative) {
            if (mOnDialogClickListener != null) {
                mOnDialogClickListener.onNegative(v);
            }
            mDialog.dismiss();
        } else if (i == R.id.tv_positive || i == R.id.tv_new_positive) {
            if (mOnDialogClickListener != null) {
                mOnDialogClickListener.onPositive(v);
            }
            mDialog.dismiss();
        }
    }
}

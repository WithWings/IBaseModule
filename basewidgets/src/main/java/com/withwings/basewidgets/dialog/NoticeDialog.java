package com.withwings.basewidgets.dialog;

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

    /**
     * 默认的确认文案
     */
    private static final String POSITIVE_TEXT = "我知道了";

    private NoticeDialog(Context context, boolean defaultOrder) {
        super(context, R.layout.dialog_notice,defaultOrder);
    }

    public static NoticeDialog create(Context context) {
        return create(context, true);
    }

    public static NoticeDialog create(Context context, boolean defaultOrder) {
        return new NoticeDialog(context,defaultOrder);
    }

    @Override
    protected void initView() {
        mTvTitle = mDialog.findViewById(R.id.tv_title);
        mTvMessage = mDialog.findViewById(R.id.tv_message);
        mTvPositive = mDialog.findViewById(R.id.tv_positive);
    }

    @Override
    protected void defaultView() {
        mTvTitle.setVisibility(View.GONE);
        mTvPositive.setText(POSITIVE_TEXT);
        mTvPositive.setOnClickListener(this);
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
    public BaseDialog setGravity(int gravity) {
        mTvMessage.setGravity(gravity);
        return this;
    }

    @Override
    public NoticeDialog setPositive(String positive) {
        if (!TextUtils.isEmpty(positive)) {
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

package com.withwings.basewidgets.keyboard;

import android.app.Activity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.withwings.basewidgets.BasePopupWindow;
import com.withwings.basewidgets.R;
import com.withwings.basewidgets.listener.OnInputResultListener;

/**
 * 固定电话软键盘
 * 创建：WithWings 时间：2017/11/17.
 * Email:wangtong1175@sina.com
 */
public class FixedTelephonePopupWindow extends BasePopupWindow {

    // 输入框
    private EditText mEtInputAreaCode;
    private EditText mEtInputPhoneNumber;
    private EditText mEtInputExtensionNumber;

    // 区号
    private static final int AREA_CODE = 0;
    // 电话
    private static final int PHONE_NUMBER = 1;
    // 分机号
    private static final int EXTENSION_NUMBER = 2;

    // 当前光标位置
    private int mFocusIndex = AREA_CODE;

    // 确认按钮监听
    private OnInputResultListener mOnInputResultListener;
    // 标题
    private TextView mTvTitle;
    private ImageView mIvCancelIcon;
    private TextView mTvCancelText;
    private ImageView mIvDoneIcon;
    private TextView mTvDoneText;

    public FixedTelephonePopupWindow(Activity activity, OnInputResultListener onInputResultListener) {
        super(activity, R.layout.popup_window_fixed_telephone);
        mOnInputResultListener = onInputResultListener;
    }

    @Override
    protected void init() {
        // 标题
        mTvTitle = mContentView.findViewById(R.id.tv_title);

        mIvCancelIcon = mContentView.findViewById(R.id.iv_cancel_icon);
        mTvCancelText = mContentView.findViewById(R.id.tv_cancel_text);

        mIvDoneIcon = mContentView.findViewById(R.id.iv_done_icon);
        mTvDoneText = mContentView.findViewById(R.id.tv_done_text);

        // 输入框
        mEtInputAreaCode = mContentView.findViewById(R.id.et_input_area_code);
        mEtInputPhoneNumber = mContentView.findViewById(R.id.et_input_phone_number);
        mEtInputExtensionNumber = mContentView.findViewById(R.id.et_input_extension_number);

        mEtInputAreaCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mEtInputAreaCode.length() >= 4) {
                    mFocusIndex++;
                    switchFocus();
                }
            }
        });
        mEtInputPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mEtInputPhoneNumber.length() >= 8) {
                    mFocusIndex++;
                    switchFocus();
                }
            }
        });
        mEtInputExtensionNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mEtInputExtensionNumber.length() >= 6) {
                    mFocusIndex++;
                    switchFocus();
                }
            }
        });

        mEtInputAreaCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        mEtInputAreaCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mFocusIndex = AREA_CODE;
                    mEtInputAreaCode.setSelection(mEtInputAreaCode.length());
                }
            }
        });

        mEtInputPhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mFocusIndex = PHONE_NUMBER;
                    mEtInputPhoneNumber.setSelection(mEtInputPhoneNumber.length());
                }
            }
        });

        mEtInputExtensionNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mFocusIndex = EXTENSION_NUMBER;
                    mEtInputExtensionNumber.setSelection(mEtInputExtensionNumber.length());
                }
            }
        });

        // 键盘
        mContentView.findViewById(R.id.btn_input_0).setOnClickListener(this);
        mContentView.findViewById(R.id.btn_input_1).setOnClickListener(this);
        mContentView.findViewById(R.id.btn_input_2).setOnClickListener(this);
        mContentView.findViewById(R.id.btn_input_3).setOnClickListener(this);
        mContentView.findViewById(R.id.btn_input_4).setOnClickListener(this);
        mContentView.findViewById(R.id.btn_input_5).setOnClickListener(this);
        mContentView.findViewById(R.id.btn_input_6).setOnClickListener(this);
        mContentView.findViewById(R.id.btn_input_7).setOnClickListener(this);
        mContentView.findViewById(R.id.btn_input_8).setOnClickListener(this);
        mContentView.findViewById(R.id.btn_input_9).setOnClickListener(this);
        mContentView.findViewById(R.id.ib_delete_input).setOnClickListener(this);

        switchString(null);
    }

    /**
     * 设置标题
     * @param title 标题名
     */
    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTvTitle.setText(title);
        }
    }

    /**
     * 设置取消按钮
     * @param cancel 取消文案
     * @param showImage 显示取消图标
     */
    public void setCancel(String cancel, boolean showImage) {
        if (!TextUtils.isEmpty(cancel)) {
            mTvCancelText.setText(cancel);
            mTvCancelText.setVisibility(View.VISIBLE);
        } else {
            mTvCancelText.setVisibility(View.GONE);
        }
        if (showImage) {
            mIvCancelIcon.setVisibility(View.VISIBLE);
        } else {
            mIvCancelIcon.setVisibility(View.GONE);
        }
    }

    /**
     * 设置确认按钮
     * @param confirm 确认文案
     * @param showImage 显示确认图标
     */
    public void setConfirm(String confirm, boolean showImage) {
        if (!TextUtils.isEmpty(confirm)) {
            mTvDoneText.setText(confirm);
            mTvDoneText.setVisibility(View.VISIBLE);
        } else {
            mTvDoneText.setVisibility(View.GONE);
        }
        if (showImage) {
            mIvDoneIcon.setVisibility(View.VISIBLE);
        } else {
            mIvDoneIcon.setVisibility(View.GONE);
        }
    }

    public void setDefaultData(String... args) {
        if (args.length == 0 || TextUtils.isEmpty(args[0])) {
            switchString(null);
            return;
        }
        for (String arg : args) {
            if (mFocusIndex > 2 || TextUtils.isEmpty(arg)) {
                break;
            }
            switchString(arg);
            mFocusIndex++;
        }
        switchString(null);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_cancel_icon || i == R.id.tv_cancel_text) {
            dismiss();
        } else if (i == R.id.iv_done_icon || i == R.id.tv_done_text) {
            if (checkData()) {
                if (mOnInputResultListener != null) {
                    mOnInputResultListener.onConfirm(mEtInputAreaCode.getText().toString(), mEtInputPhoneNumber.getText().toString(), mEtInputExtensionNumber.getText().toString());
                }
                dismiss();
            } else {
                Toast.makeText(mActivity, "请输入正确的区号和电话号码！", Toast.LENGTH_SHORT).show();
            }
        } else if (i == R.id.btn_input_0 || i == R.id.btn_input_1 || i == R.id.btn_input_2
                || i == R.id.btn_input_3 || i == R.id.btn_input_4 || i == R.id.btn_input_5
                || i == R.id.btn_input_6 || i == R.id.btn_input_7 || i == R.id.btn_input_8
                || i == R.id.btn_input_9) {
            Button button = (Button) v;
            String string = button.getText().toString();
            switchString(string);
        } else if (i == R.id.ib_delete_input) {
            switchDelete();
        }
    }

    /**
     * 输入信息
     *
     * @param string 输入框叠加数据
     */
    private void switchString(String string) {
        EditText editText;
        switch (mFocusIndex) {
            case AREA_CODE:
                editText = mEtInputAreaCode;
                break;
            case PHONE_NUMBER:
                editText = mEtInputPhoneNumber;
                break;
            case EXTENSION_NUMBER:
                editText = mEtInputExtensionNumber;
                break;
            default:
                editText = null;
                break;
        }
        if (editText != null) {
            if (TextUtils.isEmpty(string)) {
                string = "";
            }
            StringBuffer edit = new StringBuffer(editText.getText().toString());
            editText.setText(edit.append(string));
        }
        switchFocus();
    }

    /**
     * 删除操作
     */
    private void switchDelete() {
        EditText editText;
        switch (mFocusIndex) {
            case AREA_CODE:
                editText = mEtInputAreaCode;
                break;
            case PHONE_NUMBER:
                editText = mEtInputPhoneNumber;
                break;
            case EXTENSION_NUMBER:
                editText = mEtInputExtensionNumber;
                break;
            default:
                editText = null;
                break;
        }
        if (editText == null) {
            mFocusIndex = 2;
            switchFocus();
            switchDelete();
        } else if (TextUtils.isEmpty(editText.getText())) {
            if (mFocusIndex > 0) {
                mFocusIndex--;
                switchDelete();
            }
        } else {
            String string = editText.getText().toString();
            editText.setText(string.substring(0, string.length() - 1));
            switchFocus();
        }
    }

    /**
     * 切换光标位置
     */
    private void switchFocus() {
        if (mEtInputAreaCode.hasFocus()) {
            mEtInputAreaCode.clearFocus();
        }
        if (mEtInputPhoneNumber.hasFocus()) {
            mEtInputPhoneNumber.clearFocus();
        }
        if (mEtInputExtensionNumber.hasFocus()) {
            mEtInputExtensionNumber.clearFocus();
        }
        EditText editText;
        switch (mFocusIndex) {
            case AREA_CODE:
                editText = mEtInputAreaCode;
                break;
            case PHONE_NUMBER:
                editText = mEtInputPhoneNumber;
                break;
            case EXTENSION_NUMBER:
                editText = mEtInputExtensionNumber;
                break;
            default:
                editText = null;
        }
        if (editText != null) {
            editText.requestFocus();
            editText.setSelection(editText.length());
        } else {
            mContentView.clearFocus();
        }
    }

    /**
     * 检查数据格式
     * @return 检查结果
     */
    private boolean checkData() {
        return !(TextUtils.isEmpty(mEtInputAreaCode.getText()) || TextUtils.isEmpty(mEtInputPhoneNumber.getText())
                || mEtInputAreaCode.length() < 3 || mEtInputPhoneNumber.length() < 7);
    }

    @Override
    public void onDismiss() {
        super.onDismiss();
        if(mOnInputResultListener != null) {
            mOnInputResultListener.onDismiss();
        }
    }
}

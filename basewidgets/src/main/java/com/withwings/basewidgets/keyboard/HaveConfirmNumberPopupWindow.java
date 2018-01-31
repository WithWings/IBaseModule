package com.withwings.basewidgets.keyboard;

import android.app.Activity;
import android.text.Editable;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.withwings.basewidgets.BasePopupWindow;
import com.withwings.basewidgets.R;

/**
 * 带确定按钮的数字键盘
 * 创建：WithWings 时间 2018/1/5
 * Email:wangtong1175@sina.com
 */
public class HaveConfirmNumberPopupWindow extends BasePopupWindow {

    private EditText mEditText;

    private int mMaxLength = 8;

    public HaveConfirmNumberPopupWindow(Activity activity, EditText editText) {
        super(activity, R.layout.popup_window_have_confirm_number);
        mEditText = editText;
        mEditText.setInputType(InputType.TYPE_NULL);
    }

    @Override
    protected void init() {
        mContentView.findViewById(R.id.tv_done_text).setOnClickListener(this);

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
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_done_text) {
            dismiss();
        } else if (i == R.id.btn_input_0 || i == R.id.btn_input_1 || i == R.id.btn_input_2 || i == R.id.btn_input_3 || i == R.id.btn_input_4 || i == R.id.btn_input_5 || i == R.id.btn_input_6 || i == R.id.btn_input_7 || i == R.id.btn_input_8 || i == R.id.btn_input_9) {
            Button button = (Button) v;
            String string = button.getText().toString().trim();
            switchString(string);
        } else if (i == R.id.ib_delete_input) {
            switchDelete();
        }
    }

    private void switchDelete() {
        int index = mEditText.getSelectionStart();
        Editable editable = mEditText.getText();
        if(editable.length() > 0) {
            editable.delete(index - 1, index);
        }
    }

    private void switchString(String string) {
        Editable editable = mEditText.getText();
        if(mMaxLength > 0) {
            if(editable.length() == mMaxLength) {
                return;
            }
        }
        int index = mEditText.getSelectionStart();
        editable.insert(index, string);
    }

    public void show(View view) {
        showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    @Override
    protected boolean showBackground() {
        return false;
    }
}

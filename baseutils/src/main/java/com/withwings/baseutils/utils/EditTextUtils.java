package com.withwings.baseutils.utils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

/**
 * 输入框工具类
 * 创建：WithWings 时间 2018/1/16
 * Email:wangtong1175@sina.com
 */
@SuppressWarnings({"unused", "WeakerAccess", "ConstantConditions", "SameParameterValue", "SuspiciousNameCombination", "UnusedReturnValue"})
public class EditTextUtils {

    public static void longClickClear(final EditText editText) {
        editText.setOnKeyListener(new View.OnKeyListener() {

            private Handler mHandler;

            private int i = 0;

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    i ++;
                    if(mHandler == null) {
                        mHandler = new EditTextHandler(editText);
                        Message message = new Message();
                        message.what = 0;
                        mHandler.sendMessageDelayed(message, 200);
                    }
                }
                return false;
            }

            @SuppressLint("HandlerLeak")
            class EditTextHandler extends Handler {

                private EditText mEditText;

                private EditTextHandler(EditText editText) {
                    mEditText = editText;
                }

                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case 0:
                            if(i >= 4) {
                                mEditText.setText("");
                            }
                            i = 0;
                            mHandler = null;
                            break;
                    }
                }
            }

        });
    }

}

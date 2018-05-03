package com.withwings.baseutils.utils;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

/**
 * 短信工具类
 * 创建：WithWings 时间 2018/5/3
 * Email:wangtong1175@sina.com
 */
public class SmsUtils {

    public static void newSms(Context context) {
        ContentValues values = new ContentValues();
        values.put("address", "123456789");
        values.put("body", "haha");
        values.put("date", "135123000000");
        context.getContentResolver().insert(Uri.parse("content://sms/sent"), values);
    }

}

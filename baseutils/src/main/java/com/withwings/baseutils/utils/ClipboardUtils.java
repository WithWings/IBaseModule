package com.withwings.baseutils.utils;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;

/**
 * 剪切板工具类
 * 创建：WithWings 时间 19/1/23
 * Email:wangtong1175@sina.com
 */
public class ClipboardUtils {

    public static String getClipboardText(Context context) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        //无数据时直接返回
        if (clipboard == null || !clipboard.hasPrimaryClip()) {
            return null;
        }
        //如果是文本信息
        if (clipboard.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
            ClipData cdText = clipboard.getPrimaryClip();
            ClipData.Item item = cdText.getItemAt(0);
            //此处是TEXT文本信息
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                item.getHtmlText();
            }
            item.getUri();
            return item.getText().toString().trim();
        } else {
            return null;
        }
    }

}

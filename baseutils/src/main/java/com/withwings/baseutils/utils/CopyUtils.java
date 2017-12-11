package com.withwings.baseutils.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * 剪切板操作工具
 * 创建：WithWings 时间 2017/12/11
 * Email:wangtong1175@sina.com
 */
public class CopyUtils {

    /**
     * 保存字符串到剪切板
     * @param context 上下文对象
     * @param string 保存的字符串
     */
    public static void copyString(Context context, String string) {
        ClipData clipData = ClipData.newPlainText("Label", string);
        copy(context, clipData);
    }

    /**
     * 保存Url到剪切板
     * @param context 上下文对象
     * @param uri url路径
     */
    public static void copyUrl(Context context, String uri) {
        copyUrl(context, Uri.parse(uri));
    }

    /**
     * 保存Url到剪切板
     * @param context 上下文对象
     * @param uri url地址
     */
    public static void copyUrl(Context context, Uri uri) {
        ClipData clipData = ClipData.newRawUri("Label", uri);
        copy(context, clipData);
    }

    /**
     * 保存行为，如拖拽删除操作
     * @param context 上下文对象
     * @param intent 操作
     */
    public static void copyIntent(Context context, Intent intent) {
        ClipData clipData = ClipData.newIntent("Label", intent);
        copy(context, clipData);
    }

    /**
     * 将剪切板数据存入剪切板
     * @param context 上下文对象
     * @param clipData 剪切板数据
     */
    private static void copy(Context context, ClipData clipData) {
        ClipboardManager mClipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (mClipboardManager != null) {
            mClipboardManager.setPrimaryClip(clipData);
        }
    }

    /**
     * 获得剪切板
     * @param context 上下文对象
     * @return 剪切板对象 clipData.getItemAt(0).getText();
     */
    public static ClipData getCopy(Context context) {
        ClipboardManager mClipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (mClipboardManager != null) {
            return mClipboardManager.getPrimaryClip();
        } else {
            return ClipData.newPlainText("Label", null);
        }
    }

}

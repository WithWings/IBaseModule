package com.withwings.basewidgets.webview.client;

import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * TODO
 * 创建：WithWings 时间 2018/5/18
 * Email:wangtong1175@sina.com
 */
public class FileWebViewClient extends WebViewClient {

    // 是否是录像请求协议
    private boolean mVideoFlag = false;

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (!TextUtils.isEmpty(url)) {
            mVideoFlag = url.contains("vedio");
        } else {
            view.loadUrl(url);
        }
        return true;
    }

    public boolean isVideoFlag() {
        return mVideoFlag;
    }
}

package com.withwings.basewidgets.webview.client;

import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;
import java.util.Set;

/**
 * Uri解析WebViewClient
 * 创建：WithWings 时间 2018/5/18
 * Email:wangtong1175@sina.com
 */
public class UrlWebViewClient extends WebViewClient {

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        // 界面加载开始
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        // 界面加载完毕
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        String url = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            url = request.getUrl().toString();
        }
        return filterSpecialUrl(view, url);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return filterSpecialUrl(view, url);
    }

    private boolean filterSpecialUrl(WebView view, String url) {
        Log.d("地址", url);
        if (TextUtils.isEmpty(url)) {
            return super.shouldOverrideUrlLoading(view, url);
        }
        Uri uri = Uri.parse(url);
        // 如果url的协议 = 预先约定的 js 协议
        // 就解析往下解析参数
        if (uri.getScheme().equals("jscall")) {

            // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
            // 所以拦截url,下面JS开始调用Android需要的方法
            if (uri.getAuthority().equals("location")) {
                if (uri.getPath().equals("/path")) {
                    //  步骤3：
                    // 执行JS所需要调用的逻辑
                    System.out.println("js调用了Android的方法");
                    // 可以在协议上带有参数并传递到Android上
                    HashMap<String, String> params = new HashMap<>();
                    Set<String> collection = uri.getQueryParameterNames();

                    for (String s : collection) {
                        params.put(s, uri.getQueryParameter(s));
                    }
                    // 处理协议
                    // switchJsAgreement(params);
                }

            }

            return true;
        }
        return super.shouldOverrideUrlLoading(view, url);
    }

}

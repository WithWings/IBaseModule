package com.withwings.basewidgets.webview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 自定义WebView
 * 创建：WithWings 时间 2017/12/22
 * Email:wangtong1175@sina.com
 */
public class CustomWebView extends RelativeLayout {

    private Context mContext;
    private WebView mWebView;

    public CustomWebView(Context context) {
        super(context);
        initView(context);
    }

    public CustomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CustomWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        mWebView = new WebView(mContext);
        addView(mWebView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        initWebView();
    }

    private void initWebView() {
        // 背景透明
        mWebView.setBackgroundColor(Color.argb(0, 0, 0, 0));

        // 关闭长按复制
        mWebView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

        WebSettings settings = mWebView.getSettings();

        // 设置编码
        settings.setDefaultTextEncodingName("utf-8");

        // 缩放比例
        settings.setTextZoom(100);

        // 默认最小比例
        mWebView.setInitialScale(25);

        // 是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        settings.setJavaScriptEnabled(true);

        // 浏览器标记
        settings.setUserAgentString("");

        //设置缓存
        settings.setDomStorageEnabled(false);
        // 是否使用缓存
        settings.setAppCacheEnabled(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 允许访问文件
        settings.setAllowFileAccess(true);

        // 支持缩放
        settings.setSupportZoom(true);

        // 设置出现缩放工具
        settings.setSupportZoom(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);

        // 自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);

        // 允许 window.open 弹出提示框
        settings.setJavaScriptCanOpenWindowsAutomatically(true);

        // 允许 设置允许开启多窗口
        settings.setSupportMultipleWindows(true);


        mWebView.setWebViewClient(new WebViewClient() {//网页相关回调

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

        });
    }

    public void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

    public void loadUrl(String url, Map<String, String> additionalHttpHeaders) {
        mWebView.loadUrl(url, additionalHttpHeaders);
    }

    public void destroy() {
        mWebView.clearCache(true);
        mWebView.clearHistory();
        mWebView.destroy();
    }

    public boolean canGoBack() {
        return mWebView.canGoBack();
    }

    public void goBack() {
        mWebView.goBack();
    }

    public WebView getView() {
        return mWebView;
    }
}

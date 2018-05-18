package com.withwings.basewidgets.webview;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.withwings.basewidgets.webview.client.UrlWebChromeClient;
import com.withwings.basewidgets.webview.client.UrlWebViewClient;

import java.util.Map;

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
        settings.setDomStorageEnabled(true);

        // 是否可访问Content Provider的资源，默认值 true
        settings.setAllowContentAccess(true);

        // 是否可访问本地文件，默认值 true
        settings.setAllowFileAccess(true);

        // 是否使用缓存
        settings.setAppCacheEnabled(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        // 设置支持缩放 出现缩放工具
        settings.setSupportZoom(true);

        // 设置自适应屏幕
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);

        // 是否允许通过file url加载的Javascript读取本地文件，默认值 false
        settings.setAllowFileAccessFromFileURLs(false);
        // 是否允许通过file url加载的Javascript读取全部资源(包括文件,http,https)，默认值 false
        settings.setAllowUniversalAccessFromFileURLs(false);

        // 自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);

        // 允许 window.open 弹出提示框
        settings.setJavaScriptCanOpenWindowsAutomatically(true);

        // 允许 设置允许开启多窗口
        settings.setSupportMultipleWindows(true);


        mWebView.setWebViewClient(new UrlWebViewClient());
        mWebView.setWebChromeClient(new UrlWebChromeClient());
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

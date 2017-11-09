package com.withwings.baselibs.okhttp;

import android.text.TextUtils;

import com.withwings.baselibs.okhttp.listener.OkHttpListener;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * okHttp 工具类
 * 创建：WithWings 时间：2017/11/9.
 * Email:wangtong1175@sina.com
 */
public class OkHttpClientHelper {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static OkHttpClientHelper mOkHttpClientHelper;
    private static OkHttpClient mOkHttpClient;

    private OkHttpClientHelper() {
        mOkHttpClient = new OkHttpClient();
    }

    public static synchronized OkHttpClientHelper getInstance() {
        if (mOkHttpClientHelper == null) {
            mOkHttpClientHelper = new OkHttpClientHelper();
        }
        return mOkHttpClientHelper;
    }

    public static void doApiPost(String url, String json, Map<String, String> params, final OkHttpListener okHttpListener) {
        RequestBody requestBody;
        if (!TextUtils.isEmpty(json)) {
            requestBody = RequestBody.create(JSON, json);
        } else {
            FormBody.Builder rormBodyBuilder = new FormBody.Builder();
            setGetUrl(rormBodyBuilder, params);
            requestBody = rormBodyBuilder.build();
        }
        Request.Builder requestBuilder = new Request.Builder().url(url).post(requestBody);
        setHeader(requestBuilder);
        Request request = requestBuilder.build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (okHttpListener != null) {
                    okHttpListener.onResponse(call, response);
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                if (okHttpListener != null) {
                    okHttpListener.onFailure(call, e);
                }
            }

        });
    }

    private static void setGetUrl(FormBody.Builder builder, Map<String, String> params) {
        if (params != null) {
            for (String s : params.keySet()) {
                builder.add(s, params.get(s));
            }
        }

    }

    private static void setHeader(Request.Builder builder) {
        //        builder.addHeader(key, value);
        //        builder.header(key, value);
    }

    public static void doApiGet(String url, String actionUrl, Map<String, String> params, final OkHttpListener okHttpListener) {
        url = setGetUrl(url, actionUrl, params);
        Request.Builder requestBuilder = new Request.Builder().url(url).get();
        setHeader(requestBuilder);
        Request request = requestBuilder.build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (okHttpListener != null) {
                    okHttpListener.onResponse(call, response);
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                if (okHttpListener != null) {
                    okHttpListener.onFailure(call, e);
                }
            }

        });
    }

    private static String setGetUrl(String url, String actionUrl, Map<String, String> params) {
        if(params == null) {
            return url;
        }
        StringBuilder tempParams = new StringBuilder();
        //处理参数
        int pos = 0;
        for (String key : params.keySet()) {
            if (pos > 0) {
                tempParams.append("&");
            }
            //对参数进行URLEncoder
            try {
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(params.get(key), "utf-8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            pos++;
        }
        //补全请求地址
        return String.format("%s/%s?%s", url, actionUrl, tempParams.toString());
    }

}

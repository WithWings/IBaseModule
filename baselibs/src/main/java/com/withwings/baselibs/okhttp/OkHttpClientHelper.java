package com.withwings.baselibs.okhttp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.withwings.baselibs.okhttp.listener.OkHttpByteListener;
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
import okhttp3.ResponseBody;

/**
 * okHttp 工具类
 * 创建：WithWings 时间：2017/11/9.
 * Email:wangtong1175@sina.com
 */
public class OkHttpClientHelper {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static OkHttpClientHelper mOkHttpClientHelper;
    private static OkHttpClient mOkHttpClient;

    private static Context mContext;

    private OkHttpClientHelper() {
        mOkHttpClient = new OkHttpClient();
    }

    public static synchronized OkHttpClientHelper getInstance(Context context) {
        if (mOkHttpClientHelper == null) {
            mOkHttpClientHelper = new OkHttpClientHelper();
            mContext = context;
        }
        return mOkHttpClientHelper;
    }

    public static void doApiPost(final String url, String json, Map<String, String> params, final OkHttpListener okHttpListener) {
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
            public void onResponse(final Call call, final Response response) throws IOException {
                ResponseBody responseBody = response.body();
                if(responseBody != null) {
                    final String result = responseBody.string();
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (okHttpListener != null) {
                                try {
                                    if (response.code() == 200) {
                                        okHttpListener.onSuccess(call, result);
                                    } else {
                                        okHttpListener.onResponse(call, result);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                } else {
                    okHttpListener.onFailure(call, new IOException("联网请求失败。"));
                }
            }

            @Override
            public void onFailure(final Call call, final IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (okHttpListener != null) {
                            okHttpListener.onFailure(call, e);
                        }
                    }
                });
            }

        });
    }

    public static void doApiPost(final String url, String json, Map<String, String> params, final OkHttpByteListener okHttpListener) {
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
            public void onResponse(final Call call, final Response response) throws IOException {
                ResponseBody responseBody = response.body();
                if(responseBody != null) {
                    final byte[] result = responseBody.bytes();
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (okHttpListener != null) {
                                try {
                                    if (response.code() == 200) {
                                        okHttpListener.onSuccess(call, result);
                                    } else {
                                        okHttpListener.onResponse(call, result);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                } else {
                    okHttpListener.onFailure(call, new IOException("联网请求失败。"));
                }
            }

            @Override
            public void onFailure(final Call call, final IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (okHttpListener != null) {
                            okHttpListener.onFailure(call, e);
                        }
                    }
                });
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
        final String finalUrl = url;
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                ResponseBody responseBody = response.body();
                if(responseBody != null) {
                    final String result = responseBody.string();
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (okHttpListener != null) {
                                try {
                                    if (response.code() == 200) {
                                        okHttpListener.onSuccess(call, result);
                                    } else {
                                        okHttpListener.onResponse(call, result);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                } else {
                    okHttpListener.onFailure(call, new IOException("联网请求失败。"));
                }
            }

            @Override
            public void onFailure(final Call call, final IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (okHttpListener != null) {
                            okHttpListener.onFailure(call, e);
                        }
                    }
                });
            }

        });
    }

    public static void doApiGet(String url, String actionUrl, Map<String, String> params, final OkHttpByteListener okHttpListener) {
        url = setGetUrl(url, actionUrl, params);
        Request.Builder requestBuilder = new Request.Builder().url(url).get();
        setHeader(requestBuilder);
        Request request = requestBuilder.build();
        final String finalUrl = url;
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                ResponseBody responseBody = response.body();
                if(responseBody != null) {
                    final byte[] result = responseBody.bytes();
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (okHttpListener != null) {
                                try {
                                    if (response.code() == 200) {
                                        okHttpListener.onSuccess(call, result);
                                    } else {
                                        okHttpListener.onResponse(call, result);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                } else {
                    okHttpListener.onFailure(call, new IOException("联网请求失败。"));
                }
            }

            @Override
            public void onFailure(final Call call, final IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (okHttpListener != null) {
                            okHttpListener.onFailure(call, e);
                        }
                    }
                });
            }

        });
    }

    private static String setGetUrl(String url, String actionUrl, Map<String, String> params) {
        if (params == null) {
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

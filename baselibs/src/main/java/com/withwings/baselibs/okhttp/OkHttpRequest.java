package com.withwings.baselibs.okhttp;

import android.text.TextUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 请求对象获取
 * 创建：WithWings 时间：2017/11/20.
 * Email:wangtong1175@sina.com
 */
public class OkHttpRequest {

    /**
     * JSON格式
     */
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private OkHttpRequest() {

    }

    public static Request getRequest(String url, String actionUrl) {
        return getRequest(url, actionUrl, null, null);
    }

    public static Request getRequest(String url, String actionUrl, Map<String, String> params) {
        String urlFormat = getUrlFormat(url, actionUrl);
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
        urlFormat += tempParams.toString();

        if(urlFormat.endsWith("/")) {
            urlFormat = urlFormat.substring(0,urlFormat.length() -1);
        }
        Request.Builder requestBuilder = new Request.Builder().url(urlFormat).get();
        setHeader(requestBuilder);
        return requestBuilder.build();

    }

    public static Request getRequest(String url, String actionUrl, String key, String value) {
        String urlFormat = getUrlFormat(url, actionUrl);
        if (!TextUtils.isEmpty(key)) {
            urlFormat = urlFormat + key + "=" + value;
        }

        if(urlFormat.endsWith("/")) {
            urlFormat = urlFormat.substring(0,urlFormat.length() -1);
        }
        Request.Builder requestBuilder = new Request.Builder().url(urlFormat).get();
        setHeader(requestBuilder);
        return requestBuilder.build();
    }

    private static String getUrlFormat(String url, String actionUrl) {
        String formatUrl;
        if (TextUtils.isEmpty(actionUrl)) {
            if (url.endsWith("/")) {
                formatUrl = url;
            } else {
                formatUrl = url + "/";
            }
        } else {
            if (url.endsWith("/")) {
                formatUrl = url + actionUrl + "?";
            } else {
                formatUrl = url + "/" + actionUrl + "?";
            }
        }
        return formatUrl;
    }

    public static Request postRequest(String url) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        RequestBody requestBody = formBodyBuilder.build();
        Request.Builder requestBuilder = new Request.Builder().url(url).post(requestBody);
        setHeader(requestBuilder);
        return requestBuilder.build();
    }

    public static Request postRequest(String url, String json) {
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request.Builder requestBuilder = new Request.Builder().url(url).post(requestBody);
        setHeader(requestBuilder);
        return requestBuilder.build();
    }

    /**
     * post请求方式
     */
    public static Request postRequest(String url, String name, File file) {
        RequestBody fileBody = RequestBody.create(MultipartBody.FORM, file);
        RequestBody requestBody = new MultipartBody.Builder().addFormDataPart(name, file.getName(), fileBody).build();
        Request.Builder requestBuilder = new Request.Builder().url(url).post(requestBody);
        setHeader(requestBuilder);
        return requestBuilder.build();
    }

    /**
     * post请求方式
     */
    public static Request postRequest(String url, String name, byte[] bytes) {
        RequestBody byteBody = RequestBody.create(MultipartBody.FORM, bytes);
        //创建RequestBody
        RequestBody requestBody = new MultipartBody.Builder().addFormDataPart(name, "", byteBody).build();
        //创建Request
        Request.Builder requestBuilder = new Request.Builder().url(url).post(requestBody);
        setHeader(requestBuilder);
        return requestBuilder.build();
    }

    public static Request postRequest(String url, String key, String value) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (!TextUtils.isEmpty(key)) {
            formBodyBuilder.add(key, value);
        }
        RequestBody requestBody = formBodyBuilder.build();
        Request.Builder requestBuilder = new Request.Builder().url(url).post(requestBody);
        setHeader(requestBuilder);
        return requestBuilder.build();
    }

    public static Request postRequest(String url, Map<String, String> params) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (params != null) {
            for (String s : params.keySet()) {
                formBodyBuilder.add(s, params.get(s));
            }
        }
        RequestBody requestBody = formBodyBuilder.build();
        Request.Builder requestBuilder = new Request.Builder().url(url).post(requestBody);
        setHeader(requestBuilder);
        return requestBuilder.build();
    }


    /**
     * 更新请求头
     *
     * @param builder 操作体
     */
    private static void setHeader(Request.Builder builder) {
        //        builder.addHeader(key, value);
        //        builder.header(key, value);
    }

}

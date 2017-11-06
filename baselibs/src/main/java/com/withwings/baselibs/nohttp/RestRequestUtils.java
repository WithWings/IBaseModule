package com.withwings.baselibs.nohttp;

import android.graphics.Bitmap;

import com.withwings.baselibs.BuildConfig;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.Priority;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.CacheMode;
import com.yanzhenjie.nohttp.rest.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 获得请求对象
 * 创建：WithWings 时间：2017/11/6.
 * Email:wangtong1175@sina.com
 */
public class RestRequestUtils {

    /**
     * 服务器地址
     */
    public static String SERVICE_URL = checkService();

    /**
     * 默认请求方式
     */
    private static final RequestMethod DEFAULT_REQUEST_METHOD = RequestMethod.POST;

    /**
     * 默认优先级
     */
    private static final Priority DEFAULT_PRIORITY = Priority.DEFAULT;

    /**
     * 默认缓存模式
     */
    private static final CacheMode DEFAULT_CACHE_MODE = CacheMode.DEFAULT;

    private static final Map<String, String> REQUEST_PARAMS = new HashMap<>();

    private static final Map<String, String> REQUEST_HEADERS = new HashMap<>();

    public static Request<String> getRequestString(String url) {
        return getRequestString(url, DEFAULT_REQUEST_METHOD);
    }

    public static Request<String> getRequestString(String url, RequestMethod requestMethod) {
        return getRequestString(url, requestMethod, DEFAULT_PRIORITY);
    }

    public static Request<String> getRequestString(String url, RequestMethod requestMethod, Priority priority) {
        requestMethod = checkMethod(requestMethod);
        Request<String> stringRequest = NoHttp.createStringRequest(SERVICE_URL, requestMethod);
        setDefault(stringRequest, priority, url);
        return stringRequest;
    }

    public static Request<Bitmap> getRequestBitmap(String url) {
        return getRequestBitmap(url, DEFAULT_REQUEST_METHOD);
    }

    public static Request<Bitmap> getRequestBitmap(String url, RequestMethod requestMethod) {
        return getRequestBitmap(url, requestMethod, DEFAULT_PRIORITY);
    }

    public static Request<Bitmap> getRequestBitmap(String url, RequestMethod requestMethod, Priority priority) {
        requestMethod = checkMethod(requestMethod);
        Request<Bitmap> imageRequest = NoHttp.createImageRequest(SERVICE_URL, requestMethod);
        setDefault(imageRequest, priority, url);
        return imageRequest;
    }

    public static Request<JSONObject> getRequestJsonObject(String url) {
        return getRequestJsonObject(url, DEFAULT_REQUEST_METHOD);
    }

    public static Request<JSONObject> getRequestJsonObject(String url, RequestMethod requestMethod) {
        return getRequestJsonObject(url, requestMethod, DEFAULT_PRIORITY);
    }

    public static Request<JSONObject> getRequestJsonObject(String url, RequestMethod requestMethod, Priority priority) {
        requestMethod = checkMethod(requestMethod);
        Request<JSONObject> jsonObjectRequest = NoHttp.createJsonObjectRequest(SERVICE_URL, requestMethod);
        setDefault(jsonObjectRequest, priority, url);
        return jsonObjectRequest;
    }

    public static Request<JSONArray> getRequestJsonArray(String url) {
        return getRequestJsonArray(url, DEFAULT_REQUEST_METHOD);
    }

    public static Request<JSONArray> getRequestJsonArray(String url, RequestMethod requestMethod) {
        return getRequestJsonArray(url, requestMethod, DEFAULT_PRIORITY);
    }

    public static Request<JSONArray> getRequestJsonArray(String url, RequestMethod requestMethod, Priority priority) {
        requestMethod = checkMethod(requestMethod);
        Request<JSONArray> jsonArrayRequest = NoHttp.createJsonArrayRequest(SERVICE_URL, requestMethod);
        setDefault(jsonArrayRequest, priority, url);
        return jsonArrayRequest;
    }

    public static Request<byte[]> getRequestByteArray(String url) {
        return getRequestByteArray(url, DEFAULT_REQUEST_METHOD);
    }

    public static Request<byte[]> getRequestByteArray(String url, RequestMethod requestMethod) {
        return getRequestByteArray(url, requestMethod, DEFAULT_PRIORITY);
    }

    public static Request<byte[]> getRequestByteArray(String url, RequestMethod requestMethod, Priority priority) {
        requestMethod = checkMethod(requestMethod);
        Request<byte[]> byteArrayRequest = NoHttp.createByteArrayRequest(SERVICE_URL, requestMethod);
        setDefault(byteArrayRequest, priority, url);
        return byteArrayRequest;
    }

    /**
     * 添加公共参数
     * @param tRequest 请求
     * @param priority 优先级
     * @param url url
     */
    public static void setDefault(Request tRequest, Priority priority, String url) {
        priority = checkPriority(priority);
        tRequest.setPriority(priority);
        tRequest.setCacheMode(DEFAULT_CACHE_MODE);
        tRequest.path(url);
        // 请求体
        for (String s : REQUEST_PARAMS.keySet()) {
            tRequest.add(s,REQUEST_PARAMS.get(s));
        }
        // 请求头
        for (String s : REQUEST_HEADERS.keySet()) {
            tRequest.addHeader(s,REQUEST_HEADERS.get(s));
        }
    }

    /**
     * 设置默认参数
     * @param params 请求体
     * @param headers 请求头
     */
    public static void defaultParams(Map<String, String> params, Map<String, String> headers) {
        REQUEST_PARAMS.putAll(params);
        REQUEST_HEADERS.putAll(headers);
    }

    private static String checkService() {
        if (BuildConfig.DEBUG) {
            return HttpConfig.TEST_SERVICE_URL;
        } else {
            return HttpConfig.RELEASE_SERVICE_URL;
        }
    }

    private static RequestMethod checkMethod(RequestMethod requestMethod) {
        if (requestMethod == null) {
            requestMethod = DEFAULT_REQUEST_METHOD;
        }
        return requestMethod;
    }

    private static Priority checkPriority(Priority priority) {
        if (priority == null) {
            priority = DEFAULT_PRIORITY;
        }
        return priority;
    }

}

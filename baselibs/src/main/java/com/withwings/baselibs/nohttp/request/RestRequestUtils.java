package com.withwings.baselibs.nohttp.request;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.withwings.baselibs.BuildConfig;
import com.withwings.baselibs.nohttp.HttpConfigBaseUrl;
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
 * 获得请求对象：关于上传文件，只要使用指定的参数既可以上传各种文件了
 * <p>
 * *    单个文件
 * StringRequest request = ...
 * request.add("file", new FileBinary(file));
 * *    多文件，以不同的key上传不同的多个文件
 * 这里可以添加各种形式的文件，File、Bitmap、InputStream、ByteArray。
 * StringRequest request = ...
 * request.add("file1", new FileBinary(File));
 * request.add("file2", new FileBinary(File));
 * request.add("file3", new InputStreamBinary(InputStream));
 * request.add("file4", new ByteArrayBinary(byte[]));
 * request.add("file5", new BitmapBinary(Bitmap));
 * *    多文件，以相同的key上传相同的多个文件
 * StringRequest request = ...
 * fileList.add("image", new FileBinary(File));
 * fileList.add("image", new InputStreamBinary(InputStream));
 * fileList.add("image", new ByteArrayBinary(byte[]));
 * fileList.add("image", new BitmapBinary(Bitmap));
 * 创建：WithWings 时间：2017/11/6.
 * Email:wangtong1175@sina.com
 */
public class RestRequestUtils {

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

    /**
     * 默认请求参数
     */
    private static final Map<String, String> REQUEST_PARAMS = new HashMap<>();

    /**
     * 默认请求头
     */
    private static final Map<String, String> REQUEST_HEADERS = new HashMap<>();

    public static Request<String> getRequestString(String url) {
        return getRequestString(url, "");
    }

    public static Request<String> getRequestString(String url, String body) {
        return getRequestString(url, body, DEFAULT_REQUEST_METHOD);
    }

    public static Request<String> getRequestString(String url, String body, RequestMethod requestMethod) {
        return getRequestString(url, body, requestMethod, DEFAULT_PRIORITY);
    }

    public static Request<String> getRequestString(String url, String body, RequestMethod requestMethod, Priority priority) {
        requestMethod = checkMethod(requestMethod);
        Request<String> stringRequest = NoHttp.createStringRequest(url, requestMethod);
        setDefault(stringRequest, priority, body);
        return stringRequest;
    }

    public static Request<Bitmap> getRequestBitmap(String url) {
        return getRequestBitmap(url, "");
    }

    public static Request<Bitmap> getRequestBitmap(String url, String body) {
        return getRequestBitmap(url, body, DEFAULT_REQUEST_METHOD);
    }

    public static Request<Bitmap> getRequestBitmap(String url, String body, RequestMethod requestMethod) {
        return getRequestBitmap(url, body, requestMethod, DEFAULT_PRIORITY);
    }

    public static Request<Bitmap> getRequestBitmap(String url, String body, RequestMethod requestMethod, Priority priority) {
        requestMethod = checkMethod(requestMethod);
        Request<Bitmap> imageRequest = NoHttp.createImageRequest(url, requestMethod);
        setDefault(imageRequest, priority, body);
        return imageRequest;
    }

    public static Request<JSONObject> getRequestJsonObject(String url) {
        return getRequestJsonObject(url, "");
    }


    public static Request<JSONObject> getRequestJsonObject(String url, String body) {
        return getRequestJsonObject(url, body, DEFAULT_REQUEST_METHOD);
    }

    public static Request<JSONObject> getRequestJsonObject(String url, String body, RequestMethod requestMethod) {
        return getRequestJsonObject(url, body, requestMethod, DEFAULT_PRIORITY);
    }

    public static Request<JSONObject> getRequestJsonObject(String url, String body, RequestMethod requestMethod, Priority priority) {
        requestMethod = checkMethod(requestMethod);
        Request<JSONObject> jsonObjectRequest = NoHttp.createJsonObjectRequest(url, requestMethod);
        setDefault(jsonObjectRequest, priority, body);
        return jsonObjectRequest;
    }

    public static Request<JSONArray> getRequestJsonArray(String url) {
        return getRequestJsonArray(url, "");
    }

    public static Request<JSONArray> getRequestJsonArray(String url, String body) {
        return getRequestJsonArray(url, body, DEFAULT_REQUEST_METHOD);
    }

    public static Request<JSONArray> getRequestJsonArray(String url, String body, RequestMethod requestMethod) {
        return getRequestJsonArray(url, body, requestMethod, DEFAULT_PRIORITY);
    }

    public static Request<JSONArray> getRequestJsonArray(String url, String body, RequestMethod requestMethod, Priority priority) {
        requestMethod = checkMethod(requestMethod);
        Request<JSONArray> jsonArrayRequest = NoHttp.createJsonArrayRequest(url, requestMethod);
        setDefault(jsonArrayRequest, priority, body);
        return jsonArrayRequest;
    }

    public static Request<byte[]> getRequestByteArray(String url) {
        return getRequestByteArray(url, "");
    }

    public static Request<byte[]> getRequestByteArray(String url, String body) {
        return getRequestByteArray(url, body, DEFAULT_REQUEST_METHOD);
    }

    public static Request<byte[]> getRequestByteArray(String url, String body, RequestMethod requestMethod) {
        return getRequestByteArray(url, body, requestMethod, DEFAULT_PRIORITY);
    }

    public static Request<byte[]> getRequestByteArray(String url, String body, RequestMethod requestMethod, Priority priority) {
        requestMethod = checkMethod(requestMethod);
        Request<byte[]> byteArrayRequest = NoHttp.createByteArrayRequest(url, requestMethod);
        setDefault(byteArrayRequest, priority, body);
        return byteArrayRequest;
    }

    /**
     * 添加公共参数
     *
     * @param tRequest 请求
     * @param priority 优先级
     */
    public static void setDefault(Request tRequest, Priority priority, String body) {
        if (priority == null) {
            priority = DEFAULT_PRIORITY;
        }
        tRequest.setPriority(priority);
        tRequest.setCacheMode(DEFAULT_CACHE_MODE);
        // 请求体
        for (String s : REQUEST_PARAMS.keySet()) {
            tRequest.add(s, REQUEST_PARAMS.get(s));
        }
        // 请求头
        for (String s : REQUEST_HEADERS.keySet()) {
            tRequest.addHeader(s, REQUEST_HEADERS.get(s));
        }
        if (!TextUtils.isEmpty(body)) {
            tRequest.setDefineRequestBodyForJson(body);
        }
    }

    /**
     * 设置默认参数
     *
     * @param params  请求体
     * @param headers 请求头
     */
    public static void defaultParams(Map<String, String> params, Map<String, String> headers) {
        REQUEST_PARAMS.putAll(params);
        REQUEST_HEADERS.putAll(headers);
    }

    private static String checkService() {
        // 多环境请使用 switch(BuildConfig.BUILD_TYPE) 进行区别
        if (BuildConfig.DEBUG) {
            return HttpConfigBaseUrl.TEST_SERVICE_URL;
        } else {
            return HttpConfigBaseUrl.RELEASE_SERVICE_URL;
        }
    }

    /**
     * 默认请求方法
     *
     * @param requestMethod 请求方式
     * @return 请求方式
     */
    private static RequestMethod checkMethod(RequestMethod requestMethod) {
        if (requestMethod == null) {
            requestMethod = DEFAULT_REQUEST_METHOD;
        }
        return requestMethod;
    }

}

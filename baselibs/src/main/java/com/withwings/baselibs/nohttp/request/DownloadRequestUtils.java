package com.withwings.baselibs.nohttp.request;

import android.os.Environment;
import android.text.TextUtils;

import com.withwings.baselibs.BuildConfig;
import com.withwings.baselibs.nohttp.HttpConfigBaseUrl;
import com.yanzhenjie.nohttp.Priority;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.download.DownloadRequest;
import com.yanzhenjie.nohttp.rest.CacheMode;

import java.util.HashMap;
import java.util.Map;

/**
 * 下载配置工具
 * 创建：WithWings 时间：2017/11/7.
 * Email:wangtong1175@sina.com
 */
public class DownloadRequestUtils {

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

    /**
     * 默认请求参数
     */
    private static final Map<String, String> REQUEST_PARAMS = new HashMap<>();

    /**
     * 默认请求头
     */
    private static final Map<String, String> REQUEST_HEADERS = new HashMap<>();

    public static DownloadRequest getDownloadRequest(String url) {
        return getDownloadRequest(url, null);
    }

    public static DownloadRequest getDownloadRequest(String url, String fileFolder) {
        return getDownloadRequest(url, fileFolder, null);
    }

    public static DownloadRequest getDownloadRequest(String url, String fileFolder, String filename) {
        return getDownloadRequest(url, null, fileFolder, filename);
    }

    public static DownloadRequest getDownloadRequest(String url, RequestMethod requestMethod, String fileFolder, String filename) {
        return getDownloadRequest(url, requestMethod, fileFolder, filename, false, false);
    }

    public static DownloadRequest getDownloadRequest(String url, RequestMethod requestMethod, String fileFolder, String filename, boolean isRange, boolean isDeleteOld) {
        return getDownloadRequest(url, requestMethod, fileFolder, filename, isRange, isDeleteOld, null);
    }

    /**
     * 配置请求对象
     *
     * @param url           文件的url。
     * @param requestMethod 请求方法，一般为GET。
     * @param fileFolder    要保存的文件名路径，须是绝对路径。
     * @param filename      文件最终的文件名，最终会用这个文件命名下载好的文件。
     * @param isRange       是否断点续传，比如之前已经下载了50%，是否继续从50%处开始下载，否则从0开始下载。
     * @param isDeleteOld   下载前检测到已存在你指定的相同文件名的文件时，是否删除重新下载，否则直接回调下载成功。
     * @return 请求对象
     */
    public static DownloadRequest getDownloadRequest(String url, RequestMethod requestMethod, String fileFolder, String filename, boolean isRange, boolean isDeleteOld, Priority priority) {
        if (requestMethod == null) {
            requestMethod = DEFAULT_REQUEST_METHOD;
        }
        if (priority == null) {
            priority = DEFAULT_PRIORITY;
        }
        if (TextUtils.isEmpty(fileFolder)) {
            fileFolder = Environment.getExternalStorageDirectory().getPath();
        }

        DownloadRequest downloadRequest;
        if (TextUtils.isEmpty(filename)) {
            downloadRequest = new DownloadRequest(SERVICE_URL, requestMethod, fileFolder, isRange, isDeleteOld);
        } else {
            downloadRequest = new DownloadRequest(SERVICE_URL, requestMethod, fileFolder, filename, isRange, isDeleteOld);
        }
        downloadRequest.setPriority(priority);
        setDefault(downloadRequest, url);
        return downloadRequest;
    }

    private static void setDefault(DownloadRequest downloadRequest, String url) {
        if (url.contains(SERVICE_URL)) {
            if (!url.endsWith("/")) {
                url = url.replace(SERVICE_URL + "/", "");
            } else {
                url = url.replace(SERVICE_URL, "");
            }

        }
        downloadRequest.path(url);
        // 请求体
        for (String s : REQUEST_PARAMS.keySet()) {
            downloadRequest.add(s, REQUEST_PARAMS.get(s));
        }
        // 请求头
        for (String s : REQUEST_HEADERS.keySet()) {
            downloadRequest.addHeader(s, REQUEST_HEADERS.get(s));
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

}

package com.withwings.baseutils.network;

import android.text.TextUtils;

import com.withwings.baseutils.network.listener.OnNetWorkListener;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * 网络请求工具：基本工具
 * 创建：WithWings 时间：2017/11/29.
 * Email:wangtong1175@sina.com
 */
public class NetWorkUtils {

    private static final int DEFAULT_TIME_OUT = 5 * 1000;

    private static final int HTTP_200 = 200;

    // Get方式请求
    public static void requestByGet(String path) {
        requestByGet(path, null);
    }

    // Get方式请求
    public static void requestByGet(String path, OnNetWorkListener onNetWorkListener) {
        requestByGet(path, DEFAULT_TIME_OUT, null, onNetWorkListener);
    }

    // Get方式请求
    public static void requestByGet(String path, HashMap<String, String> params, OnNetWorkListener onNetWorkListener) {
        requestByGet(path, DEFAULT_TIME_OUT, params, onNetWorkListener);
    }

    // Get方式请求
    public static void requestByGet(String path, int timeout, OnNetWorkListener onNetWorkListener) {
        requestByGet(path, timeout, null, onNetWorkListener);
    }

    // Get方式请求
    public static void requestByGet(final String path, final int timeout, final HashMap<String, String> params, final OnNetWorkListener onNetWorkListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(path)) {
                    String baseUrl = path;
                    try {
                        if (params != null && params.keySet().size() > 0) {
                            StringBuilder tempParams = new StringBuilder();
                            int pos = 0;
                            for (String key : params.keySet()) {
                                if (pos > 0) {
                                    tempParams.append("&");
                                }
                                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(params.get(key), "utf-8")));
                                pos++;
                            }
                            // 新建一个URL对象
                            if (!baseUrl.endsWith("?")) {
                                baseUrl += "?";
                            }
                            baseUrl = baseUrl + tempParams.toString();
                        }
                        URL url = new URL(baseUrl);
                        // 打开一个HttpURLConnection连接
                        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                        // 设置连接主机超时时间
                        urlConn.setConnectTimeout(timeout);
                        //设置从主机读取数据超时
                        urlConn.setReadTimeout(timeout);
                        // 设置是否使用缓存  默认是true
                        urlConn.setUseCaches(true);
                        // 设置为Post请求
                        urlConn.setRequestMethod("GET");
                        //urlConn设置请求头信息
                        //设置请求中的媒体类型信息。
                        urlConn.setRequestProperty("Content-Type", "application/json");
                        //设置客户端与服务连接类型
                        urlConn.addRequestProperty("Connection", "Keep-Alive");
                        // 开始连接
                        urlConn.connect();
                        int responseCode = urlConn.getResponseCode();
                        if (onNetWorkListener != null) {

                            // 判断请求是否成功
                            if (responseCode == 200) {
                                // 获取返回的数据
                                byte[] data = readDataFromStream(urlConn.getInputStream());
                                onNetWorkListener.onSuccess(data);
                            } else {
                                onNetWorkListener.onFailed(responseCode);
                            }
                        }
                        // 关闭连接
                        urlConn.disconnect();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    // Post方式请求
    public static void requestByPost(String path) {
        requestByGet(path, null);
    }

    // Post方式请求
    public static void requestByPost(String path, OnNetWorkListener onNetWorkListener) {
        requestByGet(path, DEFAULT_TIME_OUT, null, onNetWorkListener);
    }

    // Post方式请求
    public static void requestByPost(String path, HashMap<String, String> params, OnNetWorkListener onNetWorkListener) {
        requestByGet(path, DEFAULT_TIME_OUT, params, onNetWorkListener);
    }

    // Post方式请求
    public static void requestByPost(String path, int timeout, OnNetWorkListener onNetWorkListener) {
        requestByGet(path, timeout, null, onNetWorkListener);
    }

    // Post方式请求
    public static void requestByPost(final String path, int timeout, final HashMap<String, String> params, final OnNetWorkListener onNetWorkListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 新建一个URL对象
                    URL url = new URL(path);
                    // 打开一个HttpURLConnection连接
                    HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                    // 设置连接超时时间
                    urlConn.setConnectTimeout(5 * 1000);
                    //设置从主机读取数据超时
                    urlConn.setReadTimeout(5 * 1000);
                    // Post请求必须设置允许输出 默认false
                    urlConn.setDoOutput(true);
                    //设置请求允许输入 默认是true
                    urlConn.setDoInput(true);
                    // Post请求不能使用缓存
                    urlConn.setUseCaches(false);
                    // 设置为Post请求
                    urlConn.setRequestMethod("POST");
                    //设置本次连接是否自动处理重定向
                    urlConn.setInstanceFollowRedirects(true);
                    // 配置请求Content-Type
                    urlConn.setRequestProperty("Content-Type", "application/json");
                    // 开始连接
                    urlConn.connect();
                    if (params != null && params.size() > 0) {
                        //合成参数
                        StringBuilder tempParams = new StringBuilder();
                        int pos = 0;
                        for (String key : params.keySet()) {
                            if (pos > 0) {
                                tempParams.append("&");
                            }
                            tempParams.append(String.format("%s=%s", key, URLEncoder.encode(params.get(key), "utf-8")));
                            pos++;
                        }
                        String params = tempParams.toString();
                        // 请求的参数转换为byte数组
                        byte[] postData = params.getBytes();
                        // 发送请求参数
                        DataOutputStream dos = new DataOutputStream(urlConn.getOutputStream());
                        dos.write(postData);
                        dos.flush();
                        dos.close();
                    }
                    // 判断请求是否成功
                    int responseCode = urlConn.getResponseCode();
                    if (onNetWorkListener != null) {

                        // 判断请求是否成功
                        if (responseCode == 200) {
                            // 获取返回的数据
                            byte[] data = readDataFromStream(urlConn.getInputStream());
                            onNetWorkListener.onSuccess(data);
                        } else {
                            onNetWorkListener.onFailed(responseCode);
                        }
                    }
                    // 关闭连接
                    urlConn.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static byte[] readDataFromStream(InputStream inputStream) {
        byte[] data = null;
        byte[] cache = new byte[1024 * 4];
        if (inputStream == null) {
            return null;
        } else {
            try {
                data = new byte[0];
                int count = inputStream.read(cache);
                while (count != -1) {
                    data = addBytes(data, cache);
                    count = inputStream.read(cache);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    private static byte[] addBytes(byte[] data1, byte[] data2) {
        byte[] data3 = new byte[data1.length + data2.length];
        System.arraycopy(data1, 0, data3, 0, data1.length);
        System.arraycopy(data2, 0, data3, data1.length, data2.length);
        return data3;
    }

}

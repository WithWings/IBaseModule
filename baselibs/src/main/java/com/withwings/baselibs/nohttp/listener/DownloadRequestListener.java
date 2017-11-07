package com.withwings.baselibs.nohttp.listener;

import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.download.DownloadListener;

/**
 * 下载接口
 * 创建：WithWings 时间：2017/11/7.
 * Email:wangtong1175@sina.com
 */
public abstract class DownloadRequestListener implements DownloadListener {

    /**
     * 下载开始
     * @param what 队列标记
     * @param isResume 是否是断点续传，从中间开始下载的。
     * @param rangeSize 如果是断点续传，这个参数非0，表示之前已经下载的文件大小。
     * @param responseHeaders 服务器响应头。
     * @param allCount 文件总大小，可能为0，因为服务器可能不返回文件大小。
     */
    @Override
    public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders, long allCount) {

    }

    /**
     * 更新下载进度和下载网速。
     * @param what 队列标记
     * @param progress 进度，[0-100]。
     * @param fileCount 文件总大小，可能为0，因为服务器可能不返回文件大小。
     * @param speed 下载的速度，含义为1S下载的byte大小，计算下载速度时：
     *              int xKB = (int) speed / 1024; // 单位：xKB/S
     *              int xM = (int) speed / 1024 / 1024; // 单位：xM/S
     */
    @Override
    public void onProgress(int what, int progress, long fileCount, long speed) {

    }

    /**
     * 取消/暂停下载
     * @param what 队列标记
     */
    @Override
    public void onCancel(int what) {

    }

    /**
     * 下载完成
     * @param what 队列标记
     * @param filePath 文件存储路径
     */
    @Override
    public abstract void onFinish(int what, String filePath);

    /**
     * 下载发生错误。
     * @param what 队列标记
     * @param exception 错误异常
     */
    @Override
    public abstract void onDownloadError(int what, Exception exception);
}

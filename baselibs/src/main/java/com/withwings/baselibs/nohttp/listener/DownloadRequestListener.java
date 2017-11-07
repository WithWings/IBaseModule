package com.withwings.baselibs.nohttp.listener;

import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.download.DownloadListener;

/**
 * 下载接口
 * 创建：WithWings 时间：2017/11/7.
 * Email:wangtong1175@sina.com
 */
public abstract class DownloadRequestListener implements DownloadListener {

    @Override
    public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders, long allCount) {

    }

    @Override
    public void onProgress(int what, int progress, long fileCount, long speed) {

    }

    @Override
    public void onCancel(int what) {

    }

    @Override
    public abstract void onFinish(int what, String filePath);

    @Override
    public abstract void onDownloadError(int what, Exception exception);
}

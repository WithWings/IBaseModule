package com.withwings.baselibs.nohttp.download;

import com.withwings.baselibs.nohttp.listener.DownloadRequestListener;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yanzhenjie.nohttp.download.DownloadQueue;
import com.yanzhenjie.nohttp.download.DownloadRequest;

/**
 * TODO
 * 创建：WithWings 时间：2017/11/7.
 * Email:wangtong1175@sina.com
 */
public class DownloadQueueService {

    private static DownloadQueueService mDownloadQueueService;

    private DownloadQueue mDownloadQueue;

    private DownloadQueueService(int size) {
        mDownloadQueue = NoHttp.newDownloadQueue(size);
    }

    public static DownloadQueueService getInstance() {
        return getInstance(3);
    }

    public static synchronized DownloadQueueService getInstance(int size) {
        if (mDownloadQueueService == null) {
            mDownloadQueueService = new DownloadQueueService(size);
        }
        return mDownloadQueueService;
    }

    public void doDown(final DownloadRequest downloadRequest, int what, final DownloadRequestListener downloadRequestListener) {
        mDownloadQueue.add(what, downloadRequest, new DownloadListener() {
            @Override
            public void onDownloadError(int what, Exception exception) {
                if (downloadRequestListener != null) {
                    downloadRequestListener.onDownloadError(what, exception);
                }
            }

            @Override
            public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders, long allCount) {
                if (downloadRequestListener != null) {
                    downloadRequestListener.onStart(what, isResume, rangeSize, responseHeaders, allCount);
                }
            }

            @Override
            public void onProgress(int what, int progress, long fileCount, long speed) {
                if (downloadRequestListener != null) {
                    downloadRequestListener.onProgress(what, progress, fileCount, speed);
                }
            }

            @Override
            public void onFinish(int what, String filePath) {
                if (downloadRequestListener != null) {
                    downloadRequestListener.onFinish(what, filePath);
                }
            }

            @Override
            public void onCancel(int what) {
                if (downloadRequestListener != null) {
                    downloadRequestListener.onCancel(what);
                }
            }
        });
    }

    /**
     * 释放内存
     */
    public void stop() {
        mDownloadQueue.stop();
        mDownloadQueueService = null;
    }

}

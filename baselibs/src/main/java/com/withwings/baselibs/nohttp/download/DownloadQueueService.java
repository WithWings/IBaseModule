package com.withwings.baselibs.nohttp.download;

import com.withwings.baselibs.nohttp.listener.DownloadRequestListener;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yanzhenjie.nohttp.download.DownloadQueue;
import com.yanzhenjie.nohttp.download.DownloadRequest;

/**
 * 下载队列
 * 创建：WithWings 时间：2017/11/7.
 * Email:wangtong1175@sina.com
 */
public class DownloadQueueService {

    /**
     * 单例对象
     */
    private static DownloadQueueService mDownloadQueueService;

    /**
     * 下载队列
     */
    private DownloadQueue mDownloadQueue;

    /**
     * 单例模式
     * @param size 队列大小
     */
    private DownloadQueueService(int size) {
        mDownloadQueue = NoHttp.newDownloadQueue(size);
    }

    /**
     * 实例化对象
     * @return 对象
     */
    public static DownloadQueueService getInstance() {
        return getInstance(3);
    }


    /**
     * 实例化对象
     * @param size 队列大小
     * @return 对象
     */
    public static synchronized DownloadQueueService getInstance(int size) {
        if (mDownloadQueueService == null) {
            mDownloadQueueService = new DownloadQueueService(size);
        }
        return mDownloadQueueService;
    }

    /**
     * 执行下载
     * @param downloadRequest 下载配置
     * @param what 队列标记
     * @param downloadRequestListener 下载监听
     */
    public void doDown(DownloadRequest downloadRequest, int what, final DownloadRequestListener downloadRequestListener) {
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

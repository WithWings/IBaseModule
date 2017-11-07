package com.withwings.baselibs.nohttp.download;

import com.withwings.baselibs.nohttp.listener.DownloadRequestListener;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yanzhenjie.nohttp.download.DownloadRequest;
import com.yanzhenjie.nohttp.download.SyncDownloadExecutor;

/**
 * 同步下载，记得开子线程等
 * 创建：WithWings 时间：2017/11/7.
 * Email:wangtong1175@sina.com
 */
public class SyncDownloadService {

    /**
     * 执行下载
     * @param downloadRequest 下载配置
     * @param what 队列标记
     * @param downloadRequestListener 下载监听
     */
    public static void doDown(DownloadRequest downloadRequest, int what, final DownloadRequestListener downloadRequestListener) {
        SyncDownloadExecutor.INSTANCE.execute(what, downloadRequest, new DownloadListener() {
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

}

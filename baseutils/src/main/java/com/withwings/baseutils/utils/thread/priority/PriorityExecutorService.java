package com.withwings.baseutils.utils.thread.priority;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 优先级线程池
 * 创建：WithWings 时间：2017/11/6.
 * Email:wangtong1175@sina.com
 */
public class PriorityExecutorService {

    private static ExecutorService mExecutorService;

    // 私有化工具类
    private PriorityExecutorService() {

    }

    /**
     * 实例化线程池
     * @return 线程池对象
     */
    public static synchronized ExecutorService getInstance() {
        if (mExecutorService == null) {
            mExecutorService = new ThreadPoolExecutor(3, 3, 0L, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<Runnable>());
        }
        return mExecutorService;
    }

    /**
     * 实例化线程池
     * @param corePoolSize 核心线程数量
     * @param maximumPoolSize 最大线程数量
     * @param keepAliveTime 保存时间（毫秒）
     * @return 线程池对象
     */
    public static synchronized ExecutorService getInstance(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        if (mExecutorService == null) {
            if (corePoolSize == 0) {
                corePoolSize = 3;
            }
            if (maximumPoolSize == 0) {
                maximumPoolSize = 3;
            }
            mExecutorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<Runnable>());
        }
        return mExecutorService;
    }

    /**
     * 执行线程任务
     * @param runnable 线程任务
     */
    public void run(Runnable runnable) {
        mExecutorService.execute(runnable);
    }

}

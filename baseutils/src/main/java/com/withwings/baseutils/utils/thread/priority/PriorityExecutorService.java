package com.withwings.baseutils.utils.thread.priority;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 优先级线程池:会先执行前几个，生下来的通过优先级排进去
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
            mExecutorService = new ThreadPoolExecutor(getCPUCount() + 1,  getCPUCount() * 2 + 1, 0L, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<Runnable>());
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
                corePoolSize = getCPUCount() + 1;
            }
            if (maximumPoolSize == 0) {
                maximumPoolSize = getCPUCount() * 2 + 1;
            }
            if (maximumPoolSize < corePoolSize) {
                maximumPoolSize = corePoolSize;
            }
            mExecutorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<Runnable>());
        }
        return mExecutorService;
    }

    /**
     * 执行线程任务
     * @param priorityRunnable 线程任务
     */
    public void run(PriorityRunnable priorityRunnable) {
        mExecutorService.execute(priorityRunnable);
    }

    public static int getCPUCount(){
        return Runtime.getRuntime().availableProcessors();
    }

}

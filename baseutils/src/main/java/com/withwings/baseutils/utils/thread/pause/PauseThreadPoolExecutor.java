package com.withwings.baseutils.utils.thread.pause;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可以设置暂停的循环线程
 * 创建：WithWings 时间：2017/11/6.
 * Email:wangtong1175@sina.com
 */
public class PauseThreadPoolExecutor extends ThreadPoolExecutor {

    private boolean isPaused;

    private ReentrantLock pauseLock = new ReentrantLock();

    private Condition unpaused = pauseLock.newCondition();

    public PauseThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    /**
     * 执行线程前
     * @param t 执行线程
     * @param r 线程任务
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        pauseLock.lock();
        try {
            while (isPaused) unpaused.await();
        } catch (InterruptedException ie) {
            t.interrupt();
        } finally {
            pauseLock.unlock();
        }
    }

    /**
     * 执行线程后
     * @param t 执行线程
     * @param r 线程任务
     */
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
    }

    /**
     * 线程池结束:比如调用 shutdown(); 或 shutdownNow()
     * shutdown() 允许已提交任务执行完
     * shutdownNow() 不仅组织队列，还会尝试停止正在执行的线程
     */
    @Override
    protected void terminated() {
        super.terminated();
    }

    /**
     * 暂停线程池
     */
    public void pause() {
        pauseLock.lock();
        try {
            isPaused = true;
        } finally {
            pauseLock.unlock();
        }
    }

    /**
     * 重启线程池
     */
    public void resume() {
        pauseLock.lock();
        try {
            isPaused = false;
            unpaused.signalAll();
        } finally {
            pauseLock.unlock();
        }
    }
}

package com.withwings.baseutils.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 多线程工具类
 * 创建：WithWings 时间：2017/11/3.
 * Email:wangtong1175@sina.com
 */
public class ThreadUtils {

    private static Handler mMainHandler = new Handler(Looper.getMainLooper());


    /**
     * 未使用线程池
     */
    private static final int NULL_USE_THREAD_POOL = 0;

    /**
     * 作用：该方法返回一个固定线程数量的线程池，该线程池中的线程数量始终不变，
     * 即不会再创建新的线程，也不会销毁已经创建好的线程，自始自终都是那几个固定的线程在工作
     * 所以该线程池可以控制线程的最大并发数。
     */
    private static final int FIXED_THREAD_POOL = 1;

    /**
     * 作用：该方法返回一个可以根据实际情况调整线程池中线程的数量的线程池。
     * 即该线程池中的线程数量不确定，是根据实际情况动态调整的。
     */
    private static final int CACHED_THREAD_POOL = 2;

    /**
     * 该方法返回一个只有一个线程的线程池，即每次只能执行一个线程任务，多余的任务会保存到一个任务队列中，
     * 等待这一个线程空闲，当这个线程空闲了再按FIFO方式顺序执行任务队列中的任务。
     */
    private static final int SINGLE_THREAD_EXECUTOR = 3;

    /**
     * 作用：该方法返回一个可以控制线程池内线程定时或周期性执行某任务的线程池。
     */
    private static final int SCHEDULED_THREAD_POOL = 4;

    /**
     * 作用：该方法返回一个可以控制线程池内线程定时或周期性执行某任务的线程池。
     * 只不过和上面的区别是该线程池大小为1，而上面的可以指定线程池的大小。
     */
    private static final int SINGLE_THREAD_SCHEDULED_EXECUTOR = 5;

    /**
     * 使用的线程池类型:直接设置好，这里列出来只是为了兼容所有情况
     */
    private static final Integer THREAD_POOL_TYPE = NULL_USE_THREAD_POOL;

    private static final Integer SCHEDULED_THREAD_POOL_TYPE = NULL_USE_THREAD_POOL;

    public static ExecutorService mExecutorService = getExecutorService();

    public static ScheduledExecutorService mScheduledExecutorService = getScheduledExecutorService();

    private static ScheduledExecutorService getScheduledExecutorService() {
        ScheduledExecutorService scheduledExecutorService;
        switch (SCHEDULED_THREAD_POOL_TYPE) {
            case NULL_USE_THREAD_POOL:
                return null;
            case SCHEDULED_THREAD_POOL:
                // 线程池容量
                scheduledExecutorService = Executors.newScheduledThreadPool(getCPUCount() + 1);
                break;
            case SINGLE_THREAD_SCHEDULED_EXECUTOR:
                scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
                break;
            default:
                scheduledExecutorService = null;
        }
        setScheduledExecutorService(scheduledExecutorService);
        return scheduledExecutorService;
    }

    private static void setScheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
        // TODO 如果需要详细设置，在这里设置
    }

    private static ExecutorService getExecutorService() {
        ExecutorService executorService;
        switch (THREAD_POOL_TYPE) {
            case NULL_USE_THREAD_POOL:
                return null;
            case FIXED_THREAD_POOL:
                // 线程池容量
                executorService = Executors.newFixedThreadPool(getCPUCount() + 1);
                break;
            case CACHED_THREAD_POOL:
                executorService = Executors.newCachedThreadPool();
                break;
            case SINGLE_THREAD_EXECUTOR:
                executorService = Executors.newSingleThreadExecutor();
                break;
            default:
                executorService = null;
        }
        setExecutorService(executorService);
        return executorService;
    }

    private static void setExecutorService(ExecutorService executorService) {
        // TODO 如果需要详细设置，在这里设置
    }

/*
关于 线程池工厂类的创建方式其实也是通过 ThreadPoolExecutor 创建，只不过参数配置不同而已
public ThreadPoolExecutor(
    int corePoolSize,
    int maximumPoolSize,
    long keepAliveTime,
    TimeUnit unit,
    BlockingQueue<Runnable> workQueue,
    ThreadFactory threadFactory,
    RejectedExecutionHandler handler) {//...}

我们可以看到它构造方法的参数比较多，有七个，下面一一来说明这些参数的作用：

    corePoolSize：线程池中的核心线程数量
    maximumPoolSize：线程池中的最大线程数量
    keepAliveTime：这个就是上面说到的“保持活动时间“，
            上面只是大概说明了一下它的作用，不过它起作用必须在一个前提下，
            就是当线程池中的线程数量超过了corePoolSize时，它表示多余的空闲线程的存活时间，
            即：多余的空闲线程在超过keepAliveTime时间内没有任务的话则被销毁。
            而这个主要应用在缓存线程池中
    unit：它是一个枚举类型，表示keepAliveTime的单位，常用的如：
            TimeUnit.SECONDS（秒）
            TimeUnit.MILLISECONDS（毫秒）
    workQueue：任务队列，主要用来存储已经提交但未被执行的任务，不同的线程池采用的排队策略不一样，
            不同线程任务的实现队列也不同的：
                1、newFixedThreadPool()—>LinkedBlockingQueue
                2、newSingleThreadExecutor()—>LinkedBlockingQueue
                3、newCachedThreadPool()—>SynchronousQueue
                4、newScheduledThreadPool()—>DelayedWorkQueue
                5、newSingleThreadScheduledExecutor()—>DelayedWorkQueue
                至于队列的详细信息可以单独查看
    threadFactory：线程工厂，用来创建线程池中的线程，通常用默认的即可
    handler：通常叫做拒绝策略，
            1、在线程池已经关闭的情况下
            2、任务太多导致最大线程数和任务队列已经饱和，无法再接收新的任务 。
            在上面两种情况下，只要满足其中一种时，在使用execute()来提交新的任务时将会拒绝，
            而默认的拒绝策略是抛一个RejectedExecutionException异常
*/

    /**
     * 最简单的线程使用方式，如果同时开启多个线程，切每个线程数据量较大，可能造成 OOM (Out of Memory)
     *
     * @param runnable 线程类
     */
    public static void run(Runnable runnable) {
        new Thread(runnable).start();
    }

    public static void execute(Runnable runnable) {
        if (mExecutorService == null) {
            throw new NullPointerException("请给注意 THREAD_POOL_TYPE 赋值");
        }
        mExecutorService.execute(runnable);
    }

    /**
     * 延迟执行
     *
     * @param runnable     线程任务
     * @param initialDelay 第一次延迟时间
     * @param period       每次启动线程间隔，如果为0，则默认只延迟执行一次
     */
    public static void scheduleAtFixedRate(Runnable runnable, long initialDelay, long period) {
        if (mScheduledExecutorService == null) {
            throw new NullPointerException("请给注意 SCHEDULED_THREAD_POOL_TYPE 赋值");
        }
        if (period == 0) {
            mScheduledExecutorService.schedule(runnable, initialDelay, TimeUnit.MILLISECONDS);
        } else {
            mScheduledExecutorService.scheduleAtFixedRate(runnable, initialDelay, period, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * 延迟执行
     *
     * @param runnable     线程任务
     * @param initialDelay 第一次延迟时间
     * @param delay        每次线程结束到下次开始，如果为0，则默认只延迟执行一次
     */
    public static void scheduleWithFixedDelay(Runnable runnable, long initialDelay, long delay) {
        if (mScheduledExecutorService == null) {
            throw new NullPointerException("请给注意 SCHEDULED_THREAD_POOL_TYPE 赋值");
        }
        if (delay == 0) {
            mScheduledExecutorService.schedule(runnable, initialDelay, TimeUnit.MILLISECONDS);
        } else {
            mScheduledExecutorService.scheduleWithFixedDelay(runnable, initialDelay, delay, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * 获得手机 CPU 数
     * @return
     */
    public static int getCPUCount() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * 跳转主线程
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable) {
        mMainHandler.post(runnable);
    }

    /**
     * 主线程定时任务
     * @param runnable 主线程
     * @param uptimeMillis 定时
     */
    public static void runOnUiThreadByUptime(Runnable runnable, long uptimeMillis) {
        mMainHandler.postAtTime(runnable, uptimeMillis);
    }

    /**
     * 主线程延时任务
     * @param runnable 主线程
     * @param delayMillis 延时
     */
    public static void runOnUiThreadByDelay(Runnable runnable, long delayMillis) {
        mMainHandler.postDelayed(runnable, delayMillis);
    }

}

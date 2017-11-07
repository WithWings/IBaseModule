package com.withwings.baseutils.utils;

import android.util.Log;

import com.withwings.baseutils.BuildConfig;

/**
 * Log 工具
 * 创建：WithWings 时间：2017/10/30.
 * Email:wangtong1175@sina.com
 */
public class LogUtils {

    private static LogUtils mLogUtils;

    private static boolean mShowLog;

    private static String mTag;

    private LogUtils() {
        mShowLog = BuildConfig.show_log;
    }

    public static LogUtils getInstance(){
        return getInstance("WithWings");
    }

    public synchronized static LogUtils getInstance(String tag){
        return getInstance(tag,false);
    }

    public synchronized static LogUtils getInstance(String tag,boolean showLog){
        mTag = tag;
        if(mLogUtils == null){
            mLogUtils = new LogUtils();
        }
        if(!mShowLog) {
            mShowLog = showLog;
        }
        return mLogUtils;
    }

    /**
     * 得到一个信息的详细的情况【类名+方法名+行号】
     *
     * @param message 要显示的信息
     * @return 一个信息的详细的情况【类名+方法名+行号+message】
     */
    private static String getDetailMessage(String message) {
        String detailMessage = "";
        for (StackTraceElement ste : (new Throwable()).getStackTrace()) {
            //栈顶肯定是LogUtil这个类自己
            if (!LogUtils.class.getName().equals(ste.getClassName())) {
                //栈顶的下一个就是需要调用这个类的地方[此处取出类名和方法名还有行号]
                int b = ste.getClassName().lastIndexOf(".") + 1;
                String TAG = ste.getClassName().substring(b);
                detailMessage = TAG + "->" + ste.getMethodName() + "():" + ste.getLineNumber()
                        + "->" + message;
                break;
            }
        }
        return detailMessage;
    }

    /**
     * 简单级别
     * @param message 信息内容
     */
    public void v(String message) {
        if (mShowLog) {
            Log.v(mTag, getDetailMessage(message));
        }
    }

    /**
     * debug级别
     * @param message 信息内容
     */
    public void d(String message) {
        if (mShowLog) {
            Log.d(mTag, getDetailMessage(message));
        }
    }

    /**
     * 普通级别
     * @param message 信息内容
     */
    public void i(String message) {
        if (mShowLog) {
            Log.i(mTag, getDetailMessage(message));
        }
    }

    /**
     * 警告级别
     * @param message 信息内容
     */
    public void w(String message) {
        if (mShowLog) {
            Log.w(mTag, getDetailMessage(message));
        }
    }

    /**
     * 错误级别
     * @param message 信息内容
     */
    public void e(String message) {
        if (mShowLog) {
            Log.e(mTag, getDetailMessage(message));
        }
    }

}

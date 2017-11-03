package com.withwings.baseutils.utils;

/**
 * 子线程类
 * 创建：WithWings 时间：2017/11/3.
 * Email:wangtong1175@sina.com
 */
public abstract class ThreadRunnable implements Runnable {

    public ThreadRunnable(){
        new Thread().start();
    }

}

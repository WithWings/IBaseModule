package com.withwings.baseutils.utils.thread.priority;

import androidx.annotation.NonNull;

public abstract class PriorityRunnable implements Runnable, Comparable<PriorityRunnable> {

    // 优先级
    private int priority;

    public PriorityRunnable(int priority) {
        if (priority < 0) {
            throw new IllegalArgumentException();
        }
        this.priority = priority;
    }

    /**
     * 比较优先级
     * @param o 优先线程对象
     * @return 比较结果
     */
    @Override
    public int compareTo(@NonNull PriorityRunnable o) {
        int my = this.getPriority();
        int other = o.getPriority();
        return my < other ? 1 : my > other ? -1 : 0;
    }

    // 获得优先级
    public int getPriority() {
        return priority;
    }
}
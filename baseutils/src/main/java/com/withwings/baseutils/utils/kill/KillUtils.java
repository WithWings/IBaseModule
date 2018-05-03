package com.withwings.baseutils.utils.kill;

/**
 * 关闭 APP 工具类
 * 创建：WithWings 时间 2018/5/3
 * Email:wangtong1175@sina.com
 */
public class KillUtils {

    /**
     * 结束 APP
     */
    public static void killSelf() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}

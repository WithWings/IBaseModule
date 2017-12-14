package com.withwings.baseutils.utils.kill;

import android.content.Context;
import android.content.Intent;

public class ReStartAPPTool {

    /**
     * 重启整个APP
     * @param context
     * @param Delayed 延迟多少毫秒
     */
    public static void reStartAPP(Context context, long Delayed){

        /*开启一个新的服务，用来重启本APP*/
        Intent intent=new Intent(context,KillSelfService.class);
        intent.putExtra("PackageName",context.getPackageName());
        intent.putExtra("Delayed",Delayed);
        context.startService(intent);

        /*杀死整个进程*/
        android.os.Process.killProcess(android.os.Process.myPid());
    }
    /***重启整个APP*/
    public static void reStartAPP(Context context){
        reStartAPP(context,0);
    }
}
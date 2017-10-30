package com.withwings.baseutils.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.Toast;

import com.withwings.baseutils.utils.toasty.Toasty;

/**
 * 自定义 Toast Utils
 * 创建：WithWings 时间：2017/10/30.
 * Email:wangtong1175@sina.com
 */
public class ToastUtils {

    private static Toast mToast;

    /**
     * 弹出 Toast
     * @param context 上下文
     * @param id 资源引用
     */
    public static void showToast(Context context, int id) {
        showToast(context,context.getString(id));
    }

    @SuppressLint("ShowToast")
    public static void showToast(Context context, String string) {
        if (mToast == null) {
            mToast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
        }
        else {
            mToast.setText(string);
        }
        mToast.show();
    }

    // TODO 这些封装还可以进行图标替换，这些在使用过程中如果有需求再添加即可。

    /**
     * 美化效果：错误 Toast 样式
     * @param context 上下文
     * @param id 资源引用
     */
    public static void errorToasty(Context context, int id){
        errorToasty(context,context.getString(id));
    }

    public static void errorToasty(Context context, String string){
        Toasty.error(context, string, Toast.LENGTH_SHORT, true).show();
    }

    /**
     * 美化效果：成功 Toast 样式
     * @param context 上下文
     * @param id 资源引用
     */
    public static void successToasty(Context context, int id){
        successToasty(context,context.getString(id));
    }

    public static void successToasty(Context context, String string){
        Toasty.success(context, string, Toast.LENGTH_SHORT, true).show();
    }

    /**
     * 美化效果：提示 Toast 样式
     * @param context 上下文
     * @param id 资源引用
     */
    public static void infoToasty(Context context, int id){
        infoToasty(context,context.getString(id));
    }

    public static void infoToasty(Context context, String string){
        Toasty.info(context, string, Toast.LENGTH_SHORT, true).show();
    }

    /**
     * 美化效果：警告 Toast 样式
     * @param context 上下文
     * @param id 资源引用
     */
    public static void warningToasty(Context context, int id){
        warningToasty(context,context.getString(id));
    }

    public static void warningToasty(Context context, String string){
        Toasty.warning(context, string, Toast.LENGTH_SHORT, true).show();
    }

    /**
     * 美化效果：正常 Toast 样式
     * @param context 上下文
     * @param id 资源引用
     */
    public static void normalToasty(Context context, int id){
        normalToasty(context,context.getString(id));
    }

    public static void normalToasty(Context context, String string){
        Toasty.normal(context, string, Toast.LENGTH_SHORT).show();
    }

    /**
     * 美化效果：正常 Toast 样式(带图标版本)
     * @param context 上下文
     * @param id 资源引用
     */
    public static void normalToasty(Context context, int id, int icon){
        normalToasty(context,context.getString(id), ContextCompat.getDrawable(context,icon));
    }

    public static void normalToasty(Context context, String string, Drawable drawable){
        Toasty.normal(context, string, Toast.LENGTH_SHORT, drawable).show();
    }

    /**
     * 私人定制简化
     * @param context 上下文
     * @param string 字符串
     * @param color 颜色：默认填 -1
     * @param size 字号：默认填 -1
     * @param otf 字体：默认填 null
     * @param drawable 图标 无则填 null
     */
    @SuppressLint("CheckResult")
    public static void instanceToasty(Context context, String string, int color, int size, String otf, Drawable drawable){
        Toasty.Config instance = Toasty.Config.getInstance();
        if(color >= 0) {
            instance.setTextColor(ContextCompat.getColor(context, color));
        }
        if(!TextUtils.isEmpty(otf)) {
            instance.setToastTypeface(Typeface.createFromAsset(context.getAssets(), otf));
        }
        if(size >= 0) {
            instance.setTextSize(size);
        }
        instance.apply();

        Toasty.custom(context, string, drawable,
                ContextCompat.getColor(context, color), Toast.LENGTH_SHORT, true, true).show();

        Toasty.Config.reset(); // 私人定制后最好重置，不影响其他地方使用
    }

}

package com.withwings.baseutils.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单工具类
 * 创建：WithWings 时间 2018/4/18
 * Email:wangtong1175@sina.com
 */
@TargetApi(android.os.Build.VERSION_CODES.N_MR1)
public class ShortcutUtils {

    private ShortcutUtils() {

    }

    /**
     * 该方法仅用于指引出 Manifest 注册方式说明
     * Manifest 注册的优势在于即使用户从未启动过 APP 仍然会生成菜单。
     * 1.   AndroidManifest 文件内启动页面内注册 meta-data
     * <p>
     * <meta-data
     * android:name="android.app.shortcuts"
     * android:resource="@xml/shortcut" />
     * <p>
     * 2.   res 目录下创建 xml 文件夹 shortcut.xml 文件
     * <p>
     * 内容说明如下
     * <p>
     * <shortcuts xmlns:android="http://schemas.android.com/apk/res/android">
     * <shortcut
     * android:enabled="true"
     * android:icon="@mipmap/ic_launcher"
     * android:shortcutDisabledMessage="@string/shortcut_publish"
     * android:shortcutId="publish"
     * android:shortcutLongLabel="@string/shortcut_publish"
     * android:shortcutShortLabel="@string/shortcut_publish">
     * <intent
     * android:action="android.intent.action.VIEW"
     * android:targetClass="sample.withwings.ishortcut.PublishPostActivity"
     * android:targetPackage="sample.withwings.ishortcut" />
     * <categories android:name="android.shortcut.conversation" />
     * </shortcut>
     * <p>
     * <shortcut
     * android:enabled="true"
     * android:icon="@mipmap/ic_launcher"
     * android:shortcutDisabledMessage="@string/shortcut_write"
     * android:shortcutId="write"
     * android:shortcutLongLabel="@string/shortcut_write"
     * android:shortcutShortLabel="@string/shortcut_write">
     * <intent
     * android:action="android.intent.action.VIEW"
     * android:targetClass="sample.withwings.ishortcut.WriteActivity"
     * android:targetPackage="sample.withwings.ishortcut" />
     * <categories android:name="android.shortcut.conversation" />
     * </shortcut>
     * </shortcuts>
     * <p>
     * 1、enabled：表示当前快捷方式是否可使用
     * 2、 icon： 快捷方式图标
     * 3、 shortcutDisabledMessage： 快捷方式不可使用时显示的名字
     * 4、 shortcutId：快捷方式标识
     * 5、 shortcutLongLabel：长按下图标弹出来列表框中每个快捷名
     * 6、 shortcutShortLabel：快捷是可以单独显示在桌面上的，显示名为shortcutShortLabel
     * 7、 targetClass：点击快捷方式进入的Activity
     * 8、categories 默认写死即可
     */
    public static void registerManifestShortcuts() throws IllegalAccessException {
        throw new IllegalAccessException("该方法仅用于指引出 Manifest 注册方式说明，禁止调用。");
    }

    /**
     * 隐藏所有配置清单注册的菜单
     *
     * @param context 上下文
     */
    public static void disableManifestShortcuts(Context context) {
        ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
        if (shortcutManager != null) {
            List<ShortcutInfo> allManifestShortcuts = shortcutManager.getManifestShortcuts();
            List<String> allManifestShortcutIds = new ArrayList<>();
            for (ShortcutInfo manifestShortcut : allManifestShortcuts) {
                allManifestShortcutIds.add(manifestShortcut.getId());
            }
            disableManifestShortcuts(context, allManifestShortcutIds);
        }
    }

    /**
     * 隐藏某个配置清单注册的菜单
     *
     * @param context            上下文
     * @param manifestShortcutId 某个菜单 id
     */
    public static void disableManifestShortcuts(Context context, String manifestShortcutId) {
        List<String> manifestShortcutIds = new ArrayList<>();
        manifestShortcutIds.add(manifestShortcutId);
        disableManifestShortcuts(context, manifestShortcutIds);
    }

    /**
     * 隐藏指定的配置清单注册的菜单
     *
     * @param context             上下文
     * @param manifestShortcutIds 指定的菜单 id
     */
    public static void disableManifestShortcuts(Context context, List<String> manifestShortcutIds) {
        ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
        if (shortcutManager != null) {
            List<ShortcutInfo> manifestShortcuts = shortcutManager.getManifestShortcuts();
            List<String> allManifestShortcutIds = new ArrayList<>();
            for (ShortcutInfo manifestShortcut : manifestShortcuts) {
                allManifestShortcutIds.add(manifestShortcut.getId());
            }
            List<String> disableManifestShortcutIds = new ArrayList<>();
            for (String manifestShortcutId : manifestShortcutIds) {
                if (allManifestShortcutIds.contains(manifestShortcutId)) {
                    disableManifestShortcutIds.add(manifestShortcutId);
                }
            }
            shortcutManager.disableShortcuts(disableManifestShortcutIds);
        }
    }
}

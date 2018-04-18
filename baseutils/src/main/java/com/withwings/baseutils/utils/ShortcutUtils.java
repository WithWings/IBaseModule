package com.withwings.baseutils.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单工具类
 * 创建：WithWings 时间 2018/4/18
 * Email:wangtong1175@sina.com
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@TargetApi(android.os.Build.VERSION_CODES.N_MR1)
public class ShortcutUtils {

    private ShortcutUtils() {

    }

    // ************************ 配置中心注册 ************************

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
            disableShortcuts(context, manifestShortcuts, manifestShortcutIds);
        }
    }

    /**
     * 显示所有配置清单注册的菜单
     *
     * @param context 上下文
     */
    public static void enableManifestShortcuts(Context context) {
        ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
        if (shortcutManager != null) {
            List<ShortcutInfo> allManifestShortcuts = shortcutManager.getManifestShortcuts();
            List<String> allManifestShortcutIds = new ArrayList<>();
            for (ShortcutInfo manifestShortcut : allManifestShortcuts) {
                allManifestShortcutIds.add(manifestShortcut.getId());
            }
            enableManifestShortcuts(context, allManifestShortcutIds);
        }
    }

    /**
     * 显示某个配置清单注册的菜单
     *
     * @param context            上下文
     * @param manifestShortcutId 某个菜单 id
     */
    public static void enableManifestShortcuts(Context context, String manifestShortcutId) {
        List<String> manifestShortcutIds = new ArrayList<>();
        manifestShortcutIds.add(manifestShortcutId);
        enableManifestShortcuts(context, manifestShortcutIds);
    }

    /**
     * 显示指定的配置清单注册的菜单
     *
     * @param context             上下文
     * @param manifestShortcutIds 指定的菜单 id
     */
    public static void enableManifestShortcuts(Context context, List<String> manifestShortcutIds) {
        ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
        if (shortcutManager != null) {
            List<ShortcutInfo> manifestShortcuts = shortcutManager.getManifestShortcuts();
            enableShortcuts(context, manifestShortcuts, manifestShortcutIds);
        }
    }

    // ************************ 配置中心注册 ************************


    // ************************ 桌面菜单注册 ************************

    /**
     * 注册桌面图标
     *
     * @param context      上下文
     * @param shortcutInfo 图标信息类
     *                     ShortcutInfo info = new ShortcutInfo.Builder(this, "publish-2")
     *                     .setShortLabel("动态创建-发布帖子")
     *                     .setLongLabel("动态创建-发布帖子")
     *                     .setIcon(Icon.createWithResource(this, R.mipmap.ic_launcher))
     *                     .setIntents(intents)
     *                     .build();
     */
    @TargetApi(Build.VERSION_CODES.O)
    public static void registerPinnedShortcuts(Context context, ShortcutInfo shortcutInfo) {
        ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
        if (shortcutManager != null) {
            String shortcutInfoId = shortcutInfo.getId();
            for (ShortcutInfo info : shortcutManager.getPinnedShortcuts()) {
                if (info.getId().equals(shortcutInfoId) && !info.isEnabled()) {
                    List<String> shortcutInfoIds = new ArrayList<>();
                    shortcutInfoIds.add(shortcutInfoId);
                    shortcutManager.enableShortcuts(shortcutInfoIds);
                }
            }
            shortcutManager.requestPinShortcut(shortcutInfo, null);

        }
    }

    /**
     * 关闭所有桌面图标
     *
     * @param context 上下文
     */
    public static void disablePinnedShortcuts(Context context) {
        ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
        if (shortcutManager != null) {
            List<ShortcutInfo> allPinnedShortcuts = shortcutManager.getPinnedShortcuts();
            List<String> allPinnedShortcutIds = new ArrayList<>();
            for (ShortcutInfo manifestShortcut : allPinnedShortcuts) {
                allPinnedShortcutIds.add(manifestShortcut.getId());
            }
            disableManifestShortcuts(context, allPinnedShortcutIds);
        }
    }

    /**
     * 关闭某个桌面图标
     *
     * @param context          上下文
     * @param pinnedShortcutId 某个桌面图标 Id
     */
    public static void disablePinnedShortcuts(Context context, String pinnedShortcutId) {
        List<String> pinnedShortcutIds = new ArrayList<>();
        pinnedShortcutIds.add(pinnedShortcutId);
        disablePinnedShortcuts(context, pinnedShortcutIds);
    }

    /**
     * 关闭指定的桌面图标
     *
     * @param context           上下文
     * @param pinnedShortcutIds 指定的图标 Id
     */
    public static void disablePinnedShortcuts(Context context, List<String> pinnedShortcutIds) {
        ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
        if (shortcutManager != null) {
            List<ShortcutInfo> pinnedShortcuts = shortcutManager.getPinnedShortcuts();
            disableShortcuts(context, pinnedShortcuts, pinnedShortcutIds);
        }
    }

    /**
     * 显示所有桌面图标
     *
     * @param context 上下文
     */
    public static void enablePinnedShortcuts(Context context) {
        ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
        if (shortcutManager != null) {
            List<ShortcutInfo> allPinnedShortcuts = shortcutManager.getPinnedShortcuts();
            List<String> allPinnedShortcutIds = new ArrayList<>();
            for (ShortcutInfo pinnedShortcut : allPinnedShortcuts) {
                allPinnedShortcutIds.add(pinnedShortcut.getId());
            }
            enablePinnedShortcuts(context, allPinnedShortcutIds);
        }
    }

    /**
     * 显示某个桌面图标
     *
     * @param context          上下文
     * @param pinnedShortcutId 某个桌面图标 Id
     */
    public static void enablePinnedShortcuts(Context context, String pinnedShortcutId) {
        List<String> pinnedShortcutIds = new ArrayList<>();
        pinnedShortcutIds.add(pinnedShortcutId);
        enablePinnedShortcuts(context, pinnedShortcutIds);
    }

    /**
     * 显示指定的桌面图标
     *
     * @param context           上下文
     * @param pinnedShortcutIds 指定的图标 Id
     */
    public static void enablePinnedShortcuts(Context context, List<String> pinnedShortcutIds) {
        ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
        if (shortcutManager != null) {
            List<ShortcutInfo> pinnedShortcuts = shortcutManager.getPinnedShortcuts();
            enableShortcuts(context, pinnedShortcuts, pinnedShortcutIds);
        }
    }

    // ************************ 桌面菜单注册 ************************

    // ************************ ICON菜单 ************************

    /**
     * 注册 ICON 菜单，可以拖动添加为桌面快捷方式，关闭的时候根据Id禁止，也会被禁止的，但是不会被删除。重复创建桌面不会重复出现
     *
     * @param context      上下文
     * @param shortcutInfo ShortcutInfo info = new ShortcutInfo.Builder(this, "publish-2")
     *                     .setShortLabel("动态创建-发布帖子")
     *                     .setLongLabel("动态创建-发布帖子")
     *                     .setIcon(Icon.createWithResource(this, R.mipmap.ic_launcher))
     *                     .setIntents(intents)
     *                     .build();
     */
    public static void registerDynamicShortcuts(Context context, ShortcutInfo shortcutInfo) {
        ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
        if (shortcutManager != null) {
            List<ShortcutInfo> shortcutInfos = new ArrayList<>();
            shortcutInfos.add(shortcutInfo);
            shortcutManager.setDynamicShortcuts(shortcutInfos);
        }
    }

    /**
     * 关闭所有ICON图标
     *
     * @param context 上下文
     */
    public static void disableDynamicShortcuts(Context context) {
        ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
        if (shortcutManager != null) {
            List<ShortcutInfo> dynamicShortcuts = shortcutManager.getDynamicShortcuts();
            List<String> dynamicShortcutsIds = new ArrayList<>();
            for (ShortcutInfo dynamicShortcut : dynamicShortcuts) {
                dynamicShortcutsIds.add(dynamicShortcut.getId());
            }
            disableDynamicShortcuts(context, dynamicShortcutsIds);
        }
    }

    /**
     * 关闭某个ICON图标
     *
     * @param context            上下文
     * @param dynamicShortcutsId 某个ICON图标 Id
     */
    public static void disableDynamicShortcuts(Context context, String dynamicShortcutsId) {
        List<String> dynamicShortcutsIds = new ArrayList<>();
        dynamicShortcutsIds.add(dynamicShortcutsId);
        disableDynamicShortcuts(context, dynamicShortcutsIds);
    }

    /**
     * 关闭指定的ICON图标
     *
     * @param context             上下文
     * @param dynamicShortcutsIds 指定的图标 Id
     */
    public static void disableDynamicShortcuts(Context context, List<String> dynamicShortcutsIds) {
        ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
        if (shortcutManager != null) {
            List<ShortcutInfo> dynamicShortcuts = shortcutManager.getDynamicShortcuts();
            disableShortcuts(context, dynamicShortcuts, dynamicShortcutsIds);
        }
    }

    /**
     * 显示所有ICON图标
     *
     * @param context 上下文
     */
    public static void enableDynamicShortcuts(Context context) {
        ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
        if (shortcutManager != null) {
            List<ShortcutInfo> dynamicShortcuts = shortcutManager.getDynamicShortcuts();
            List<String> dynamicShortcutsIds = new ArrayList<>();
            for (ShortcutInfo dynamicShortcut : dynamicShortcuts) {
                dynamicShortcutsIds.add(dynamicShortcut.getId());
            }
            enableDynamicShortcuts(context, dynamicShortcutsIds);
        }
    }

    /**
     * 显示某个ICON图标
     *
     * @param context            上下文
     * @param dynamicShortcutsId 某个ICON图标 Id
     */
    public static void enableDynamicShortcuts(Context context, String dynamicShortcutsId) {
        List<String> dynamicShortcutsIds = new ArrayList<>();
        dynamicShortcutsIds.add(dynamicShortcutsId);
        enableDynamicShortcuts(context, dynamicShortcutsIds);
    }

    /**
     * 显示指定的ICON图标
     *
     * @param context             上下文
     * @param dynamicShortcutsIds 指定的图标 Id
     */
    public static void enableDynamicShortcuts(Context context, List<String> dynamicShortcutsIds) {

        ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
        if (shortcutManager != null) {
            List<ShortcutInfo> dynamicShortcuts = shortcutManager.getDynamicShortcuts();
            enableShortcuts(context, dynamicShortcuts, dynamicShortcutsIds);
        }
    }

    // ************************ ICON菜单 ************************


    // ************************ 综合隐藏显示类 ************************

    /**
     * 隐藏所选的 菜单项
     *
     * @param context            上下文
     * @param allShortcuts       已有的菜单项
     * @param disableShortcutIds 要显示的菜单项
     */
    public static void disableShortcuts(Context context, List<ShortcutInfo> allShortcuts, List<String> disableShortcutIds) {
        ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
        if (shortcutManager != null) {
            List<String> needDisableShortcutIds = new ArrayList<>();
            for (ShortcutInfo shortcut : allShortcuts) {
                if (disableShortcutIds.contains(shortcut.getId())) {
                    needDisableShortcutIds.add(shortcut.getId());
                }
            }
            shortcutManager.disableShortcuts(needDisableShortcutIds);
            // shortcutManager.disableShortcuts(needDisableShortcutIds, "已停用");
        }
    }

    /**
     * 显示所选的 菜单项
     *
     * @param context           上下文
     * @param allShortcuts      已有的菜单项
     * @param enableShortcutIds 要显示的菜单项
     */
    public static void enableShortcuts(Context context, List<ShortcutInfo> allShortcuts, List<String> enableShortcutIds) {
        ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
        if (shortcutManager != null) {
            List<String> needEnableShortcutIds = new ArrayList<>();
            for (ShortcutInfo shortcut : allShortcuts) {
                if (enableShortcutIds.contains(shortcut.getId())) {
                    needEnableShortcutIds.add(shortcut.getId());
                }
            }
            shortcutManager.enableShortcuts(needEnableShortcutIds);
        }
    }
}

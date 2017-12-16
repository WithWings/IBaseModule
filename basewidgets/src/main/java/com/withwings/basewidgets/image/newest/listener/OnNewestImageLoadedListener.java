package com.withwings.basewidgets.image.newest.listener;

import android.content.Loader;
import android.database.Cursor;

import com.withwings.basewidgets.image.newest.bean.NewestMessage;

/**
 * 加载监听器
 * 创建：WithWings 时间 2017/12/16
 * Email:wangtong1175@sina.com
 */
public interface OnNewestImageLoadedListener {

    /**
     * 图片加载成功
     * @param loader 数据列表
     * @param data 数据对象
     * @param newestMessage 数据对象解析结果
     */
    void onImagesLoaded(Loader<Cursor> loader, Cursor data, NewestMessage newestMessage);

    /**
     * 图片加载重置
     * @param loader 数据指针列表
     */
    void onImagesReset(Loader<Cursor> loader);

}

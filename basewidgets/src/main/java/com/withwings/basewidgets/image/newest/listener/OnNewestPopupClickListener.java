package com.withwings.basewidgets.image.newest.listener;

import android.view.View;

import com.withwings.basewidgets.image.newest.bean.NewestMessage;

/**
 * 自定义 Popup 弹出事件监听
 */
public interface OnNewestPopupClickListener {

    /**
     * 点击
     * @param view 点击的 pop
     * @param newestMessage 被点击的 Popup 所有的信息类
     */
    void onClick(View view, NewestMessage newestMessage);

}

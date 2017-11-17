package com.withwings.basewidgets;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * 弹窗
 * 创建：WithWings 时间：2017/11/17.
 * Email:wangtong1175@sina.com
 */
public abstract class BasePopupWindow extends PopupWindow {

    protected Context mContext;
    protected final View mContentView;

    public BasePopupWindow(Context context,int layout) {
        mContext = context;
        // 设置布局文件
        mContentView = LayoutInflater.from(context).inflate(layout,null);
        setContentView(mContentView);
        // 宽高
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 获得焦点
        setFocusable(true);
        // 进出动画
        setAnimationStyle(R.style.popupWindowAnim);
        // 点击外部不消失
        setBackgroundDrawable(new BitmapDrawable());
        setOutsideTouchable(true);
        // 初始化，可以覆盖某些默认设置
        init();
    }

    /**
     * 初始化
     */
    protected abstract void init();

    //相对某个控件的位置（正左下方），无偏移
    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
    }

    //相对某个控件的位置，有偏移;xoff表示x轴的偏移，正值表示向左，负值表示向右；yoff表示相对y轴的偏移，正值是向下，负值是向上；
    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        super.showAsDropDown(anchor, xoff, yoff, gravity);
    }

    //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
    }
}

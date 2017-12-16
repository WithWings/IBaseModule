package com.withwings.basewidgets.image.newest.view;

import android.app.Activity;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.withwings.basewidgets.R;
import com.withwings.basewidgets.image.newest.bean.NewestMessage;
import com.withwings.basewidgets.image.newest.listener.OnNewestImageLoadedListener;
import com.withwings.basewidgets.image.newest.listener.OnNewestPopupClickListener;
import com.withwings.basewidgets.image.newest.loader.NewestImageLoader;

/**
 * 用来展示最近图片，如用户可能想要发送什么图片
 * 创建：WithWings 时间 2017/12/16
 * Email:wangtong1175@sina.com
 */
public class NewestPopupWindow extends PopupWindow {

    /**
     * 默认宽
     */
    private static final int DEFAULT_WIDTH = 90;
    /**
     * 默认高
     */
    private static final int DEFAULT_HEIGHT = 135;


    private Activity mActivity;

    private int mPopupWidth;
    private int mPopupHeight;

    private View mInflate;
    private ImageView mNewestPicture;

    private NewestImageLoader mNewestImageLoader;
    private NewestMessage mData;
    private boolean mShow;

    private View mView;
    private int mGravity;
    private int mXoff;
    private int mYoff;

    /**
     * 自定义构造
     * @param activity 依附的界面

     */
    public NewestPopupWindow(Activity activity) {
        mActivity = activity;
        mInflate = LayoutInflater.from(mActivity).inflate(R.layout.newest_pop_view, null, false);
        mInflate.setOnClickListener(null);
        mNewestPicture = mInflate.findViewById(R.id.newest_picture);
        setContentView(mInflate);
        size(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        // 设置背景 点击后界面才会消失
        setBackgroundDrawable(new BitmapDrawable(activity.getResources(), Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)));
        setOutsideTouchable(true);
        setFocusable(true);

        mNewestImageLoader = new NewestImageLoader(mActivity).date(20000).listener(new OnNewestImageLoadedListener() {
            @Override
            public void onImagesLoaded(Loader<Cursor> loader, Cursor data, NewestMessage newestMessage) {
                mData = newestMessage;
                if(mShow) {
                    see();
                }
            }

            @Override
            public void onImagesReset(Loader<Cursor> loader) {

            }
        }).init();
    }

    /**
     * 宽高数值处理
     * @param type 数据单位
     * @param width 宽
     * @param height 高
     */
    public NewestPopupWindow size(int type, int width, int height) {
        switch (type) {
            case TypedValue.COMPLEX_UNIT_SP:
                mPopupWidth = sp2px(width);
                mPopupHeight = sp2px(height);
                break;
            case TypedValue.COMPLEX_UNIT_DIP:
                mPopupWidth = dip2px(width);
                mPopupHeight = dip2px(height);
                break;
            case TypedValue.COMPLEX_UNIT_PX:
                mPopupWidth = width;
                mPopupHeight = height;
                break;
        }
        setWidth(mPopupWidth);
        setHeight(mPopupHeight);
        return this;
    }

    /**
     * popup点击监听
     * @param onNewestPopupClickListener 监听器
     * @return Popup
     */
    public NewestPopupWindow listener(final OnNewestPopupClickListener onNewestPopupClickListener) {
        mInflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNewestPopupClickListener.onClick(v, mData);
            }
        });
        return this;
    }

    /**
     * 展示 Popup
     * @param view 依赖View
     */
    public void show(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        show(view, Gravity.NO_GRAVITY, (location[0] + view.getWidth() / 2) - mPopupWidth / 2, location[1] - mPopupHeight);
    }

    /**
     * 自定义参数展示 Popup
     * @param view 依赖View
     * @param gravity 方向
     * @param xoff x轴
     * @param yoff y轴
     */
    public void show(View view, int gravity, int xoff, int yoff) {
        mView = view;
        mGravity = gravity;
        mXoff = xoff;
        mYoff = yoff;
        if(mData != null) {
            see();
        } else {
            mShow = true;
        }
    }

    private void see() {
        if (!TextUtils.isEmpty(mData.getPath())) {
            Drawable drawable = Drawable.createFromPath(mData.getPath());
            mNewestPicture.setImageDrawable(drawable);
            showAtLocation(mView, mGravity, mXoff, mYoff);
        }
        mNewestImageLoader.destory();
    }

    /**
     * dip转换px
     */
    private int dip2px(int dipValue) {
        final float scale = mActivity.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * sp转换px
     */
    private int sp2px(int spValue) {
        final float fontScale = mActivity.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}

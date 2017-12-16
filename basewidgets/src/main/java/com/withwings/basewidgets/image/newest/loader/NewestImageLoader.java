package com.withwings.basewidgets.image.newest.loader;

import android.Manifest;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import com.withwings.basewidgets.image.newest.bean.NewestMessage;
import com.withwings.basewidgets.image.newest.listener.OnNewestImageLoadedListener;

/**
 * 最近保存的图片读取器:注意需要声明读取内存卡权限哦，不然这里什么也拿不到呢
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
 * 创建：WithWings 时间 2017/12/16
 * Email:wangtong1175@sina.com
 */
public class NewestImageLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    // 加载所有图片
    private static final int LOADER_ALL_FOLDER = 0;
    // 加载指定目录图片
    private static final int LOADER_BY_FOLDER = 1;

    private String[] IMAGE_PROJECTION = initProjection();

    // 是否需要回调监听
    private OnNewestImageLoadedListener mOnNewestImageLoadedListener;

    // 是否指定图片文件夹
    private String mPath;

    private long mDelay;

    private Activity mActivity;
    private NewestMessage mNewestMessage;
    private LoaderManager mLoaderManager;

    public NewestImageLoader(Activity activity) {
        mActivity = activity;
        mLoaderManager = mActivity.getLoaderManager();
        mNewestMessage = new NewestMessage();
    }

    /**
     * 初始化查询接口
     *
     * @return 可以查询到的信息
     */
    private String[] initProjection() {
        String[] strings;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            strings = new String[]{MediaStore.Images.Media.DISPLAY_NAME,   //图片的显示名称  aaa.jpg
                    MediaStore.Images.Media.DATA,           //图片的真实路径  /storage/emulated/0/pp/downloader/wallpaper/aaa.jpg
                    MediaStore.Images.Media.SIZE,           //图片的大小，long型  132492
                    MediaStore.Images.Media.MIME_TYPE,      //图片的类型     image/jpeg
                    MediaStore.Images.Media.DATE_ADDED,     //图片被添加的时间，long型  1450518608
                    MediaStore.Images.Media.WIDTH,          //图片的宽度，int型  1920
                    MediaStore.Images.Media.HEIGHT};        //图片的高度，int型  1080
        } else {
            strings = new String[]{MediaStore.Images.Media.DISPLAY_NAME,   //图片的显示名称  aaa.jpg
                    MediaStore.Images.Media.DATA,           //图片的真实路径  /storage/emulated/0/pp/downloader/wallpaper/aaa.jpg
                    MediaStore.Images.Media.SIZE,           //图片的大小，long型  132492
                    MediaStore.Images.Media.MIME_TYPE,      //图片的类型     image/jpeg
                    MediaStore.Images.Media.DATE_ADDED};     //图片被添加的时间，long型  1450518608
        }
        return strings;
    }

    public NewestImageLoader date(long delay) {
        mDelay = delay;
        return this;
    }

    public NewestImageLoader path(String path) {
        mPath = path;
        return this;
    }

    public NewestImageLoader listener(OnNewestImageLoadedListener onNewestImageLoadedListener) {
        mOnNewestImageLoadedListener = onNewestImageLoadedListener;
        return this;
    }

    public NewestImageLoader init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                try {
                    throw new NoPermissionException("如果想要抓取用户最新操作的图片是需要申请读取内存卡的权限的哦：android.permission.READ_EXTERNAL_STORAGE");
                } catch (NoPermissionException e) {
                    e.printStackTrace();
                }
            }
        }
        if (TextUtils.isEmpty(mPath)) {// 加载所有的图片
            mLoaderManager.initLoader(LOADER_ALL_FOLDER, null, this);
        } else { // 加载指定目录的图片
            Bundle bundle = new Bundle();
            bundle.putString("path", mPath);
            mLoaderManager.initLoader(LOADER_BY_FOLDER, bundle, this);
        }
        return this;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = null;
        //扫描所有图片
        if (id == LOADER_ALL_FOLDER) {//时间逆序
            cursorLoader = new CursorLoader(mActivity, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION, null, null, IMAGE_PROJECTION[4] + " DESC");
        }
        //扫描某个图片文件夹
        if (id == LOADER_BY_FOLDER) {
            cursorLoader = new CursorLoader(mActivity, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION, IMAGE_PROJECTION[1] + " like '%" + args.getString("path") + "%'", null, IMAGE_PROJECTION[4] + " DESC");
        }

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            if (data.moveToFirst()) {
                //查询数据
                String imageName = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                String imagePath = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                long imageSize = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));
                String imageMimeType = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[3]));
                long imageAddTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[4]));
                // 在 api 16 以下，宽高可能取不到，这里以 -1 标记
                int widthIndex = data.getColumnIndexOrThrow(IMAGE_PROJECTION[5]);
                int imageWidth = widthIndex == -1 ? -1 : data.getInt(widthIndex);
                int heightIndex = data.getColumnIndexOrThrow(IMAGE_PROJECTION[6]);
                int imageHeight = heightIndex == -1 ? -1 : data.getInt(heightIndex);

                mNewestMessage.setName(imageName);
                mNewestMessage.setPath(imagePath);
                mNewestMessage.setSize(imageSize);
                mNewestMessage.setMimeType(imageMimeType);
                mNewestMessage.setAddTime(imageAddTime);
                mNewestMessage.setWidth(imageWidth);
                mNewestMessage.setHeight(imageHeight);
            }

        }

        //回调接口，通知图片数据准备完成
        if (mOnNewestImageLoadedListener != null) {
            if (mDelay != 0 && mNewestMessage.getAddTime() != null && System.currentTimeMillis() - mDelay < mNewestMessage.getAddTime() * 1000) {
                mOnNewestImageLoadedListener.onImagesLoaded(loader, data, mNewestMessage);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //回调接口，通知图片数据重置
        if (mOnNewestImageLoadedListener != null) {
            mOnNewestImageLoadedListener.onImagesReset(loader);
        }
    }

    /**
     * 使用后关闭 NewestImageLoader 防止占用内存
     */
    public void destory() {
        if (mPath == null) {
            mLoaderManager.destroyLoader(LOADER_ALL_FOLDER);
        } else {
            mLoaderManager.destroyLoader(LOADER_BY_FOLDER);
        }
    }

    private class NoPermissionException extends Exception {

        public NoPermissionException(String message) {
            super(message);
        }

    }

}

package com.withwings.basewidgets.webview.client;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import java.io.File;

/**
 * 处理文件请求
 * 创建：WithWings 时间 2018/5/18
 * Email:wangtong1175@sina.com
 */
@SuppressWarnings("unused")
public class FileWebChromeClient extends WebChromeClient {

    private ValueCallback<Uri> mUploadMessage;

    private ValueCallback<Uri[]> mUploadCallbackAboveL;

    public static final int PHOTO_REQUEST = 100;

    public static final int VIDEO_REQUEST = 120;

    private FileWebViewClient mFileWebViewClient;

    private Activity mActivity;

    private File mFileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/" + SystemClock.currentThreadTimeMillis() + ".jpg");
    private Uri mImageUri;

    public FileWebChromeClient(Activity activity, FileWebViewClient fileWebViewClient) {
        mActivity = activity;
        mFileWebViewClient = fileWebViewClient;
    }

    // For Android 3.0-
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        mUploadMessage = uploadMsg;
        if (mFileWebViewClient.isVideoFlag()) {
            recordVideo();
        } else {
            takePhoto();
        }

    }

    // For Android 3.0+
    public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
        mUploadMessage = uploadMsg;
        if (mFileWebViewClient.isVideoFlag()) {
            recordVideo();
        } else {
            takePhoto();
        }
    }

    //For Android 4.1
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        mUploadMessage = uploadMsg;
        if (mFileWebViewClient.isVideoFlag()) {
            recordVideo();
        } else {
            takePhoto();
        }
    }

    // For Android 5.0+
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        mUploadCallbackAboveL = filePathCallback;
        if (mFileWebViewClient.isVideoFlag()) {
            recordVideo();
        } else {
            takePhoto();
        }
        return true;
    }


    /**
     * 拍照
     */
    private void takePhoto() {
        mImageUri = Uri.fromFile(mFileUri);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mImageUri = FileProvider.getUriForFile(mActivity, mActivity.getPackageName() + ".fileProvider", mFileUri);//通过FileProvider创建一个content类型的Uri
        }
        takePicture();
    }

    /**
     * 录像
     */
    private void recordVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        //限制时长
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
        //开启摄像机
        mActivity.startActivityForResult(intent, VIDEO_REQUEST);
    }

    private void takePicture() {
        //调用系统相机
        Intent intentCamera = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intentCamera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        intentCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        //将拍照结果保存至photo_file的Uri中，不保留在相册中
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        if (mActivity!=null){
            mActivity.startActivityForResult(intentCamera, PHOTO_REQUEST);
        }
    }


    public ValueCallback<Uri[]> getUploadCallbackAboveL() {
        return mUploadCallbackAboveL;
    }

    public void setUploadCallbackAboveL(ValueCallback<Uri[]> uploadCallbackAboveL) {
        mUploadCallbackAboveL = uploadCallbackAboveL;
    }

    public ValueCallback<Uri> getUploadMessage() {
        return mUploadMessage;
    }

    public void setUploadMessage(ValueCallback<Uri> uploadMessage) {
        mUploadMessage = uploadMessage;
    }

    public Uri getImageUri() {
        return mImageUri;
    }






//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == FileWebChromeClient.PHOTO_REQUEST) {
//            if (null == mFileWebChromeClient.getUploadMessage() && null == mFileWebChromeClient.getUploadCallbackAboveL()) return;
//            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
//            if (mFileWebChromeClient.getUploadCallbackAboveL() != null) {
//                onActivityResultAboveL(requestCode, resultCode, data);
//            } else if (mFileWebChromeClient.getUploadMessage() != null) {
//                mFileWebChromeClient.getUploadMessage().onReceiveValue(result);
//                mFileWebChromeClient.setUploadMessage(null);
//            }
//        } else if (requestCode == FileWebChromeClient.VIDEO_REQUEST) {
//            if (null == mFileWebChromeClient.getUploadMessage() && null == mFileWebChromeClient.getUploadCallbackAboveL()) return;
//
//            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
//            if (mFileWebChromeClient.getUploadCallbackAboveL() != null) {
//                if (resultCode == RESULT_OK) {
//                    mFileWebChromeClient.getUploadCallbackAboveL().onReceiveValue(new Uri[]{result});
//                    mFileWebChromeClient.setUploadCallbackAboveL(null);
//                } else {
//                    mFileWebChromeClient.getUploadCallbackAboveL().onReceiveValue(new Uri[]{});
//                    mFileWebChromeClient.setUploadCallbackAboveL(null);
//                }
//
//            } else if (mFileWebChromeClient.getUploadMessage() != null) {
//                if (resultCode == RESULT_OK) {
//                    mFileWebChromeClient.getUploadMessage().onReceiveValue(result);
//                    mFileWebChromeClient.setUploadMessage(null);
//                } else {
//                    mFileWebChromeClient.getUploadMessage().onReceiveValue(Uri.EMPTY);
//                    mFileWebChromeClient.setUploadMessage(null);
//                }
//
//            }
//        }
//    }
//
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
//        if (requestCode != FileWebChromeClient.PHOTO_REQUEST || mFileWebChromeClient.getUploadCallbackAboveL() == null) {
//            return;
//        }
//        Uri[] results = null;
//        if (resultCode == Activity.RESULT_OK) {
//            if (data == null) {
//                results = new Uri[]{mFileWebChromeClient.getImageUri()};
//            } else {
//                String dataString = data.getDataString();
//                ClipData clipData = data.getClipData();
//                if (clipData != null) {
//                    results = new Uri[clipData.getItemCount()];
//                    for (int i = 0; i < clipData.getItemCount(); i++) {
//                        ClipData.Item item = clipData.getItemAt(i);
//                        results[i] = item.getUri();
//                    }
//                }
//
//                if (dataString != null)
//                    results = new Uri[]{Uri.parse(dataString)};
//            }
//        }
//        mFileWebChromeClient.getUploadCallbackAboveL().onReceiveValue(results);
//        mFileWebChromeClient.setUploadCallbackAboveL(null)
//    }
}

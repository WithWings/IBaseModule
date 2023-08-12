package com.withwings.mvc.local.model;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import androidx.annotation.NonNull;
import android.text.TextUtils;

import com.withwings.baseutils.network.NetWorkUtils;
import com.withwings.baseutils.network.listener.OnNetWorkListener;
import com.withwings.mvc.bean.IPInfoBean;
import com.withwings.mvc.local.listener.OnQueryLocalListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * TODO
 * 创建：WithWings 时间：2017/11/28.
 * Email:wangtong1175@sina.com
 */
public class QueryLocalIpLocationModelImpl implements QueryLocalIpLocationModel {

    @Override
    public String loadTrackRecordInfo(Context context) {
        return getIPAddress(context);
    }


    /**
     * 获得ip：即使 wifi 状态下一样获得 ip 地址段
     *
     * @param context 上下文
     * @return 可能为null 比如飞行模式或者关闭无线和数据的状态
     */
    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                assert wifiManager != null;
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                return intIP2StringIP(wifiInfo.getIpAddress());
            } else {
                return null;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
            return null;
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip 参数
     * @return 转换
     */
    private static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

    @Override
    public void requestNetWork(String url, @NonNull final Activity activity, final OnQueryLocalListener onQueryLocalListener) {
        NetWorkUtils.requestByGet(url, new OnNetWorkListener() {
            @Override
            public void onSuccess(final byte[] data) {
                if (onQueryLocalListener != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            IPInfoBean ipInfoBean = readIpInfoFromJson(new String(data));
                            if (ipInfoBean == null) {
                                ipInfoBean = new IPInfoBean();
                            }
                            onQueryLocalListener.onSuccess(ipInfoBean);
                        }
                    });
                }
            }

            @Override
            public void onFailed(int responseCode) {
                if (onQueryLocalListener != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onQueryLocalListener.onError();
                        }
                    });
                }
            }
        });
    }

    private IPInfoBean readIpInfoFromJson(String json) {
        IPInfoBean ipInfoBean = null;
        if (!TextUtils.isEmpty(json)) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                ipInfoBean = new IPInfoBean();
                ipInfoBean.setRet(jsonObject.getInt("ret"));
                ipInfoBean.setStart(jsonObject.getInt("start"));
                ipInfoBean.setEnd(jsonObject.getInt("end"));
                ipInfoBean.setCountry(jsonObject.getString("country"));
                ipInfoBean.setProvince(jsonObject.getString("province"));
                ipInfoBean.setCity(jsonObject.getString("city"));
                ipInfoBean.setDistrict(jsonObject.getString("district"));
                ipInfoBean.setIsp(jsonObject.getString("isp"));
                ipInfoBean.setType(jsonObject.getString("type"));
                ipInfoBean.setDesc(jsonObject.getString("desc"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return ipInfoBean;
    }
}

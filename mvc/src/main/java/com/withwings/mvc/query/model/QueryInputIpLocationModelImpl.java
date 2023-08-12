package com.withwings.mvc.query.model;

import android.app.Activity;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;

import com.withwings.baseutils.network.NetWorkUtils;
import com.withwings.baseutils.network.listener.OnNetWorkListener;
import com.withwings.mvc.bean.IPInfoBean;
import com.withwings.mvc.local.listener.OnQueryLocalListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * TODO
 * 创建：WithWings 时间：2017/11/29.
 * Email:wangtong1175@sina.com
 */
public class QueryInputIpLocationModelImpl implements QueryInputIpLocationModel {
    @Override
    public void requestNetWork(String url, final String ip, @NonNull final Activity activity, final OnQueryLocalListener onQueryLocalListener) {
        if (TextUtils.isEmpty(ip)) {
            Toast.makeText(activity, "IP地址不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        url = url + "&ip=" + ip;
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
                            ipInfoBean.setIp(ip);
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

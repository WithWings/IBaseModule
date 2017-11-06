package com.withwings.ibasemodule;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.withwings.baselibs.nohttp.HttpConfig;
import com.withwings.baselibs.nohttp.NetWorkRequestListener;
import com.withwings.baselibs.nohttp.RequestQueueNetWork;
import com.withwings.baselibs.nohttp.RestRequestUtils;
import com.withwings.baseutils.utils.ThreadUtils;
import com.yanzhenjie.nohttp.rest.Request;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ThreadUtils.run(new Runnable() {
            @Override
            public void run() {
                Request<String> requestString = RestRequestUtils.getRequestString(HttpConfig.LOGIN_URL);
                RequestQueueNetWork.getInstance().doRequest(requestString, 0, new NetWorkRequestListener<String>() {
                    @Override
                    public void onSucceed(int what, String result) {

                    }

                    @Override
                    public void onFailed(int what, String result) {

                    }
                });
            }
        });
    }
}

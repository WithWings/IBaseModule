package com.withwings.mvc.local;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.withwings.baseutils.base.BaseActivity;
import com.withwings.mvc.R;
import com.withwings.mvc.bean.IPInfoBean;
import com.withwings.mvc.local.listener.OnQueryLocalListener;
import com.withwings.mvc.local.model.QueryLocalIpLocationModel;
import com.withwings.mvc.local.model.QueryLocalIpLocationModelImpl;

/**
 * 查询本机 IP 所在城市
 * 创建：WithWings 时间：2017/11/28.
 * Email:wangtong1175@sina.com
 */
public class QueryLocalIpLocationActivity extends BaseActivity implements OnQueryLocalListener {

    private static final String LOCAL_URL = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json";

    private TextView mTvShowIp;
    private TextView mTvShowCountry;
    private TextView mTvShowProvince;
    private TextView mTvShowCity;
    private Button mBtnCheck;

    private QueryLocalIpLocationModel mQueryLocalIpLocationModel;

    @Override
    protected int initLayout() {
        return R.layout.activity_query_local_ip_location;
    }

    @Override
    protected void initData() {
        mQueryLocalIpLocationModel = new QueryLocalIpLocationModelImpl();
    }

    @Override
    protected void initView() {
        mTvShowIp = findViewById(R.id.tv_show_ip);
        mTvShowCountry = findViewById(R.id.tv_show_country);
        mTvShowProvince = findViewById(R.id.tv_show_province);
        mTvShowCity = findViewById(R.id.tv_show_city);
        mBtnCheck = findViewById(R.id.btn_check);
    }

    @Override
    protected void syncPage() {
        String ip = mQueryLocalIpLocationModel.loadTrackRecordInfo(mActivity);
        mTvShowIp.setText(getString(R.string.show_ip, ip));
    }

    @Override
    protected void initListener() {
        mBtnCheck.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_check:
                mQueryLocalIpLocationModel.requestNetWork(LOCAL_URL, mActivity, this);
                break;
        }
    }

    @Override
    protected void onLeftClick() {

    }

    @Override
    protected void onRightClick() {

    }

    @Override
    public void onSuccess(IPInfoBean ipInfoBean) {
        mTvShowCountry.setText(getString(R.string.show_country, ipInfoBean.getCountry()));
        mTvShowProvince.setText(getString(R.string.show_province, ipInfoBean.getProvince()));
        mTvShowCity.setText(getString(R.string.show_city, ipInfoBean.getCity()));
    }

    @Override
    public void onError() {

    }
}

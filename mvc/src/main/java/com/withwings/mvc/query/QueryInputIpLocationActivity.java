package com.withwings.mvc.query;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.withwings.baseutils.base.BaseActivity;
import com.withwings.mvc.R;
import com.withwings.mvc.bean.IPInfoBean;
import com.withwings.mvc.local.listener.OnQueryLocalListener;
import com.withwings.mvc.query.model.QueryInputIpLocationModel;
import com.withwings.mvc.query.model.QueryInputIpLocationModelImpl;

/**
 * 查询用户输入的ip信息
 * 创建：WithWings 时间：2017/11/29.
 * Email:wangtong1175@sina.com
 */
public class QueryInputIpLocationActivity extends BaseActivity implements OnQueryLocalListener {

    private static final String LOCAL_URL = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json";

    private EditText mEtInputIp;
    private TextView mTvShowIp;
    private TextView mTvShowCountry;
    private TextView mTvShowProvince;
    private TextView mTvShowCity;
    private Button mBtnCheck;

    private QueryInputIpLocationModel mQueryInputIpLocationModel;

    @Override
    protected int initLayout() {
        return R.layout.activity_query_input_ip_location;
    }

    @Override
    protected void initData() {
        mQueryInputIpLocationModel = new QueryInputIpLocationModelImpl();
    }

    @Override
    protected void initView() {
        mEtInputIp = findViewById(R.id.et_input_ip);
        mTvShowIp = findViewById(R.id.tv_show_ip);
        mTvShowCountry = findViewById(R.id.tv_show_country);
        mTvShowProvince = findViewById(R.id.tv_show_province);
        mTvShowCity = findViewById(R.id.tv_show_city);
        mBtnCheck = findViewById(R.id.btn_check);
    }

    @Override
    protected void syncPage() {

    }

    @Override
    protected void initListener() {
        mBtnCheck.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_check:
                mQueryInputIpLocationModel.requestNetWork(LOCAL_URL, mEtInputIp.getText().toString(), mActivity, this);
                break;
        }
    }

    @Override
    public void onSuccess(IPInfoBean ipInfoBean) {
        mTvShowIp.setText(getString(R.string.show_ip, ipInfoBean.getIp()));
        mTvShowCountry.setText(getString(R.string.show_country, ipInfoBean.getCountry()));
        mTvShowProvince.setText(getString(R.string.show_province, ipInfoBean.getProvince()));
        mTvShowCity.setText(getString(R.string.show_city, ipInfoBean.getCity()));
    }

    @Override
    public void onError() {

    }
}

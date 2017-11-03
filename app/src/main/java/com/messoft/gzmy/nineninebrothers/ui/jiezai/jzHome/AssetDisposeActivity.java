package com.messoft.gzmy.nineninebrothers.ui.jiezai.jzHome;

import android.os.Bundle;
import android.view.View;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityAssetDisposeBinding;
import com.messoft.gzmy.nineninebrothers.utils.BusinessUtils;
import com.messoft.gzmy.nineninebrothers.utils.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.utils.SysUtils;

/**
 * @作者 Administrator
 * 资产处置
 * @创建日期 2017/11/2 0002 17:33
 */

public class AssetDisposeActivity extends BaseActivity<ActivityAssetDisposeBinding> {

    private String mRoleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_dispose);

        initSetting();
        initListener();
    }

    private void initSetting() {
        setTitle("资产处置");
        showContentView();

        if (BusinessUtils.getLoginPersonInfo() != null) {
            mRoleId = BusinessUtils.getLoginPersonInfo().getRoleId();
            if (mRoleId.equals("0") || mRoleId.equals("1")) {
                //普通会员 跳转跟高级合伙人一样
                bindingView.rlAssesBeian.setVisibility(View.VISIBLE);
            }
        }
    }

    private void initListener() {
        //资产备案
        bindingView.rlAssesBeian.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {

            }
        });
        //资产库
        bindingView.rlAssetKu.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                SysUtils.startActivity(AssetDisposeActivity.this,AssetKuActivity.class);
            }
        });

    }
}

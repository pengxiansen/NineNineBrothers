package com.messoft.gzmy.nineninebrothers.ui.jiezai.JzPropertyManage;

import android.os.Bundle;
import android.view.View;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityAssetManageSelectTypeBinding;
import com.messoft.gzmy.nineninebrothers.ui.jiezai.jzHome.ZwAssetInfoByIdActivity;
import com.messoft.gzmy.nineninebrothers.utils.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.utils.SysUtils;

/**
 * @作者 Administrator
 * 资产管理列表点进去选择
 * @创建日期 2017/11/8 0008 18:01
 */

public class AssetManageSelectTypeActivity extends BaseActivity<ActivityAssetManageSelectTypeBinding> {

    private String mType;
    private String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_manage_select_type);

        showContentView();
        setTitle("资产详情");

        if (getIntent() != null && getIntent().getBundleExtra("b") != null) {
            mType = getIntent().getBundleExtra("b").getString("type");
            mId = getIntent().getBundleExtra("b").getString("id");
        }

        bindingView.rlInfo.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("type", mType);
                bundle.putString("id", mId);//资产id
                SysUtils.startActivity(AssetManageSelectTypeActivity.this, ZwAssetInfoByIdActivity.class, bundle);
            }
        });

        bindingView.rlProgress.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("type", mType);
                bundle.putString("id", mId);//资产id
                SysUtils.startActivity(AssetManageSelectTypeActivity.this, AssetProgressActivity.class, bundle);
            }
        });
    }
}

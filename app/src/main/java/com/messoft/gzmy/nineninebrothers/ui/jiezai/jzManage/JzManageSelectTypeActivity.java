package com.messoft.gzmy.nineninebrothers.ui.jiezai.jzManage;

import android.os.Bundle;
import android.view.View;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityJzManageSelectTypeBinding;
import com.messoft.gzmy.nineninebrothers.ui.jiezai.jzHome.ZsInfoActivity;
import com.messoft.gzmy.nineninebrothers.utils.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.utils.SysUtils;

/**
 * @作者 Administrator
 * 解债管理列表点进去选择
 * @创建日期 2017/11/6 0006 15:16
 */

public class JzManageSelectTypeActivity extends BaseActivity<ActivityJzManageSelectTypeBinding> {

    private String mType;
    private String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jz_manage_select_type);

        showContentView();
        setTitle("解债详情");

        if (getIntent() != null && getIntent().getBundleExtra("b") != null) {
            mType = getIntent().getBundleExtra("b").getString("type");
            mId = getIntent().getBundleExtra("b").getString("id");
        }

        bindingView.rlInfo.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("type", mType);
                bundle.putString("id", mId);//债事id
                SysUtils.startActivity(JzManageSelectTypeActivity.this, ZsInfoActivity.class, bundle);
            }
        });

        bindingView.rlProgress.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("type", mType);
                bundle.putString("id", mId);//债事id
                SysUtils.startActivity(JzManageSelectTypeActivity.this, JzProgressActivity.class, bundle);
            }
        });
    }
}

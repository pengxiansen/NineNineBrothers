package com.messoft.gzmy.nineninebrothers.ui.home;

import android.os.Bundle;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityApplyPartnerBinding;
/**
 * @作者 Administrator
 * 申请合伙人
 * @创建日期 2017/11/6 0006 14:43
 */

public class ApplyPartnerActivity extends BaseActivity<ActivityApplyPartnerBinding>{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_partner);

        initSetting();
    }

    private void initSetting() {
        showContentView();
        setTitle("申请合伙人");
    }
}

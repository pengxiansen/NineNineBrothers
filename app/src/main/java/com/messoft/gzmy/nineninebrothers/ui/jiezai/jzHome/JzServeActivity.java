package com.messoft.gzmy.nineninebrothers.ui.jiezai.jzHome;

import android.os.Bundle;
import android.view.View;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityJieZhaiServeBinding;
import com.messoft.gzmy.nineninebrothers.utils.BusinessUtils;
import com.messoft.gzmy.nineninebrothers.utils.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.utils.SysUtils;

/**
 * @作者 Administrator
 * 借债服务
 * @创建日期 2017/10/27 0027 11:16
 */

public class JzServeActivity extends BaseActivity<ActivityJieZhaiServeBinding> {

    private String mRoleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jie_zhai_serve);

        initSetting();
        initListener();
    }

    private void initSetting() {
        setTitle("解债服务");
        showContentView();

        if (BusinessUtils.getLoginPersonInfo() != null) {
            mRoleId = BusinessUtils.getLoginPersonInfo().getRoleId();
            if (mRoleId.equals("0") || mRoleId.equals("1")) {
                //普通会员 跳转跟高级合伙人一样
                bindingView.rlZhaishiBeian.setVisibility(View.VISIBLE);
                bindingView.rlJiezhaiBeian.setVisibility(View.VISIBLE);
            } else if (mRoleId.equals("2")) {
                //解债师
                bindingView.rlJiezhaiKu.setVisibility(View.VISIBLE);
            }
        }
    }

    private void initListener() {
        //债事人备案
        bindingView.rlZhaishiBeian.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                SysUtils.startActivity(JzServeActivity.this, JzPersonBeiAnActivity.class);
            }
        });
        //解债备案
        bindingView.rlJiezhaiBeian.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                SysUtils.startActivity(JzServeActivity.this, JzBeianActivity.class);
            }
        });
        //解债库
        bindingView.rlJiezhaiKu.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
              SysUtils.startActivity(JzServeActivity.this,JzKuActivity.class);
            }
        });
    }


}

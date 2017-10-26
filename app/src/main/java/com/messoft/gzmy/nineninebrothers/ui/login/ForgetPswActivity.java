package com.messoft.gzmy.nineninebrothers.ui.login;

import android.os.Bundle;
import android.view.View;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityForgetPswBinding;
import com.messoft.gzmy.nineninebrothers.listener.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.utils.SysUtils;

/**
 * @作者 Administrator
 * 忘记密码
 * @创建日期 2017/10/24 0024 17:10
 */

public class ForgetPswActivity extends BaseActivity<ActivityForgetPswBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_psw);

        initSetting();
        initListener();
    }

    private void initSetting() {
        showContentView();
        setTitle("忘记密码");
    }

    private void initListener() {
        bindingView.tvNext.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                SysUtils.startActivity(ForgetPswActivity.this,ResetPswActivity.class);
            }
        });
    }
}

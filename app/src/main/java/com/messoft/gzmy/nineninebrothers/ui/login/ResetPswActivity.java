package com.messoft.gzmy.nineninebrothers.ui.login;

import android.os.Bundle;
import android.view.View;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityResetPswBinding;
import com.messoft.gzmy.nineninebrothers.listener.PerfectClickListener;

public class ResetPswActivity extends BaseActivity<ActivityResetPswBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_psw);

        initSetting();
        initListener();
    }

    private void initSetting() {
        showContentView();
        setTitle("重置密码");
    }

    private void initListener() {
        bindingView.tvNext.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {

            }
        });
    }
}

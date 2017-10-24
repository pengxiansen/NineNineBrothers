package com.messoft.gzmy.nineninebrothers.ui.login;

import android.os.Bundle;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityRegisterBinding;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initSetting();
        initListener();
    }

    private void initSetting() {
        showContentView();
        setTitle("注册");
    }

    private void initListener() {

    }
}

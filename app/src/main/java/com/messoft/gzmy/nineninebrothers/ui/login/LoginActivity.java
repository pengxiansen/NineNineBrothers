package com.messoft.gzmy.nineninebrothers.ui.login;

import android.os.Bundle;
import android.view.View;

import com.messoft.gzmy.nineninebrothers.MainActivity;
import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityLoginBinding;
import com.messoft.gzmy.nineninebrothers.listener.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.utils.StatusBarUtil;
import com.messoft.gzmy.nineninebrothers.utils.SysUtils;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        showContentView();
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary),255);

        initListener();
    }

    private void initListener() {
        //忘记密码
        bindingView.tvForgetPsw.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                SysUtils.startActivity(LoginActivity.this,ForgetPswActivity.class);
            }
        });

        //注册
        bindingView.tvRegister.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                SysUtils.startActivity(LoginActivity.this,RegisterActivity.class);
            }
        });

        //登录
        bindingView.tvLogin.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                SysUtils.startActivity(LoginActivity.this, MainActivity.class);
            }
        });
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    protected boolean hasToolBar() {
        return false;
    }
}

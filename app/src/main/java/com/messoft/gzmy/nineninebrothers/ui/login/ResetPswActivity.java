package com.messoft.gzmy.nineninebrothers.ui.login;

import android.os.Bundle;
import android.view.View;

import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityResetPswBinding;
import com.messoft.gzmy.nineninebrothers.http.RequestImpl;
import com.messoft.gzmy.nineninebrothers.listener.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.model.LoginModel;
import com.messoft.gzmy.nineninebrothers.utils.RegexUtil;
import com.messoft.gzmy.nineninebrothers.utils.StringUtils;
import com.messoft.gzmy.nineninebrothers.utils.ToastUtil;

public class ResetPswActivity extends BaseActivity<ActivityResetPswBinding> {

    private String mMobile;
    private String mCode;
    private LoginModel mLoginModel;

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

        mLoginModel = new LoginModel();

        if (getIntent() != null && null != getIntent().getBundleExtra("b")) {
            mMobile = getIntent().getBundleExtra("b").getString("mobile");
            mCode = getIntent().getBundleExtra("b").getString("code");
        }
    }

    private void initListener() {
        bindingView.tvNext.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                reSetPsw();
            }
        });
    }

    private void reSetPsw() {
        String etPsw = bindingView.etPhone.getText().toString().trim();
        String etPswSure = bindingView.etCode.getText().toString().trim();
        if (!StringUtils.isNoEmpty(etPsw)) {
            ToastUtil.showToast("请输入新密码");
            return;
        }
        if (!StringUtils.isNoEmpty(etPswSure)) {
            ToastUtil.showToast("请确认新密码");
            return;
        }
        if (!RegexUtil.isPassword(etPsw) || !RegexUtil.isPassword(etPswSure)) {
            ToastUtil.showToast("请设置规范的密码（6-18位包含字母和数字）");
            return;
        }
        if (!etPsw.equals(etPswSure)) {
            ToastUtil.showToast("两次输入密码不一致");
            return;
        }
        if (!StringUtils.isNoEmpty(mMobile) || !StringUtils.isNoEmpty(mCode)) {
            ToastUtil.showToast("操作失败，请重试");
            finish();
            return;
        }

        mLoginModel.changePsw(ResetPswActivity.this, mMobile, mCode, etPsw, etPswSure, new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                ToastUtil.showToast("修改密码成功");
                finish();
            }

            @Override
            public void loadFailed(int errorCode, String errorMessage) {
                ToastUtil.showToast(errorMessage);
            }
        });
    }
}

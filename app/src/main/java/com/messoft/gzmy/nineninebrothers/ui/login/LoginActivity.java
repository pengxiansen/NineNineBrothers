package com.messoft.gzmy.nineninebrothers.ui.login;

import android.os.Bundle;
import android.view.View;

import com.messoft.gzmy.nineninebrothers.MainActivity;
import com.messoft.gzmy.nineninebrothers.R;
import com.messoft.gzmy.nineninebrothers.base.BaseActivity;
import com.messoft.gzmy.nineninebrothers.bean.Login;
import com.messoft.gzmy.nineninebrothers.databinding.ActivityLoginBinding;
import com.messoft.gzmy.nineninebrothers.http.RequestImpl;
import com.messoft.gzmy.nineninebrothers.listener.PerfectClickListener;
import com.messoft.gzmy.nineninebrothers.model.LoginModel;
import com.messoft.gzmy.nineninebrothers.utils.SPUtils;
import com.messoft.gzmy.nineninebrothers.utils.StatusBarUtil;
import com.messoft.gzmy.nineninebrothers.utils.SysUtils;
import com.messoft.gzmy.nineninebrothers.utils.ToastUtil;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    private LoginModel mLoginModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        showContentView();
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 255);

        initSetting();
        initListener();
    }

    private void initSetting() {
        mLoginModel = new LoginModel();
    }

    private void initListener() {
        //忘记密码
        bindingView.tvForgetPsw.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                SysUtils.startActivity(LoginActivity.this, ForgetPswActivity.class);
            }
        });

        //注册
        bindingView.tvRegister.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                SysUtils.startActivity(LoginActivity.this, RegisterActivity.class);
            }
        });

        //登录
        bindingView.tvLogin.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                String account = bindingView.etAccount.getText().toString().trim();
                String password = bindingView.etPsw.getText().toString().trim();
                clickLogin("18926568749", "123456");
            }
        });
    }

    private void clickLogin(String account, String password) {
//        if (!StringUtils.isNoEmpty(account)) {
//            ToastUtil.showToast("请输入账号");
//            return;
//        }
//        if (!StringUtils.isNoEmpty(password)) {
//            ToastUtil.showToast("请输入密码");
//            return;
//        }
        mLoginModel.login(LoginActivity.this, account, password, new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                ToastUtil.showToast("登录成功");
                if (object != null) {
                    Login login = (Login) object;
                    saveUserData(login);
                    SysUtils.startActivity(LoginActivity.this, MainActivity.class);
                }
            }

            @Override
            public void loadFailed(int errorCode, String errorMessage) {
                ToastUtil.showToast(errorMessage);
                SysUtils.startActivity(LoginActivity.this, MainActivity.class);
            }
        });
    }

    /**
     * 保存用户信息
     *
     * @param login
     */
    private void saveUserData(Login login) {
        SPUtils.putObject("loginObject", login);
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
